package com.goldze.user.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.goldze.base.router.RouterActivityPath;
import com.goldze.user.AvatarManager;
import com.goldze.user.BR;
import com.goldze.user.R;
import com.goldze.user.databinding.ActivityUserDetailBinding;
import com.goldze.user.ui.viewmodel.UserDetailViewModel;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;


@Route(path = RouterActivityPath.User.PAGER_USERDETAIL)
public class FaceActivity extends BaseActivity<ActivityUserDetailBinding, UserDetailViewModel> {

    SimpleDraweeView iv_face;

    @Override
    public void initParam() {
        //注入路由框架，拿到Autowired值，必须在initParam方法中注入，不然传到ViewModel里面的name为空
        ARouter.getInstance().inject(this);
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
        iv_face = findViewById(R.id.iv_face);
        iv_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
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
}

