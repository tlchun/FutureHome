package com.goldze.user.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.goldze.base.global.SPKeyGlobal;
import com.goldze.base.router.RouterActivityPath;
import com.goldze.base.widget.TitleView;
import com.goldze.user.AvatarManager;
import com.goldze.user.BR;
import com.goldze.user.R;
import com.goldze.user.databinding.ActivityUserDetailBinding;
import com.goldze.user.ui.viewmodel.UserDetailViewModel;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yalantis.ucrop.UCrop;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.ApiResult;

import java.io.File;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;


@Route(path = RouterActivityPath.User.PAGER_USERDETAIL)
public class FaceActivity extends BaseActivity<ActivityUserDetailBinding, UserDetailViewModel> {
    private String deviceMac;

    SimpleDraweeView iv_face;
    EditText etUseId;

    @Override
    public void initParam() {
        //注入路由框架，拿到Autowired值，必须在initParam方法中注入，不然传到ViewModel里面的name为空
        ARouter.getInstance().inject(this);
        deviceMac = getIntent().getStringExtra("deviceMac");
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_face_input;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        TitleView titleView = findViewById(R.id.title);
        titleView.setTitleText("录入人脸");
        iv_face = findViewById(R.id.iv_face);
        iv_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        etUseId = findViewById(R.id.et_userid);

        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceInput();
            }
        });
    }

    public void takePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions
                    .request(Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean granted) throws Exception {
                            if (granted) { // Always true pre-M
                                AvatarManager.getInstance().takePhoto(FaceActivity.this);
                            } else {
                                ToastUtils.showShort("请先同意权限");
                            }
                        }
                    });
        } else {
            AvatarManager.getInstance().takePhoto(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == AvatarManager.REQUEST_CODE_PHOTO_CAMERA) {
            // 系统相机拍照完保存图片后进行缩放
            AvatarManager.getInstance().startCropPhotoForUCrop(AvatarManager.mAvatarUri, this);
        } else if (resultCode == RESULT_OK && requestCode == AvatarManager.REQUEST_CODE_PHOTO_ALBUM) {
            // 系统相册中选择图片后进行缩放
            if (data != null) {
                AvatarManager.getInstance().startCropPhotoForUCrop(data.getData(), this);
            }
        } else if (resultCode == RESULT_OK && requestCode == AvatarManager.REQUEST_CODE_PHOTO_COMPRESS) {
            uploadZoom();
        } else if (resultCode == UCrop.RESULT_ERROR) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadZoom() {
        String path = AvatarManager.getInstance().getZoomImageSaveName();
        File file = new File(path);
        iv_face.setImageURI(Uri.parse("file://" + file));
    }

    private void faceInput() {
        if (!TextUtils.isEmpty(etUseId.getText().toString().trim())) {
            String url = "http://skintest.hetyj.com/10120/95665446f4554ce48c613ee791e465ba.png";
            EasyHttp.get("/app/device/acs/faceInput")
                    .headers("token", SPUtils.getInstance().getString(SPKeyGlobal.USER_TOKEN))
                    .params("userId", etUseId.getText().toString().trim())
                    .params("deviceMac", deviceMac)
                    .params("faceImg", url)
                    .timeStamp(true)
                    .execute(new SimpleCallBack<String>() {
                        @Override
                        public void onError(ApiException e) {
                            ToastUtils.showShort(e.getMessage() != null ? e.getMessage() : "保存失败");
                        }

                        @Override
                        public void onSuccess(String response) {
                            Gson gson = new Gson();
                            ApiResult apiResult = gson.fromJson(response, ApiResult.class);
                            if (apiResult.getCode() == 0) {
                                ToastUtils.showShort("保存成功");
                                finish();
                            } else {
                                ToastUtils.showShort(apiResult.getMsg());
                            }
                        }
                    });
        } else {
            ToastUtils.showShort("请输入用户ID");
        }
    }
}

