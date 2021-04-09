package com.goldze.home.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.base.global.SPKeyGlobal;
import com.goldze.base.router.RouterActivityPath;
import com.goldze.base.widget.TitleView;
import com.goldze.home.R;
import com.goldze.home.ui.viewmodel.CipherModel;
import com.yzq.zxinglibrary.encode.CodeCreator;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

@Route(path = RouterActivityPath.Home.PAGER_KEY_SHARE)
public class KeyShareActivity extends BaseActivity {

    private String deviceMac;
    private long deviceId;

    private TextView pw_show;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_key_share;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initData() {
        super.initData();
        TitleView titleView = findViewById(R.id.title);
        titleView.setTitleText("钥匙分享");

        deviceMac = getIntent().getStringExtra("deviceMac");
        deviceId = getIntent().getLongExtra("deviceId", 0);

        ImageView ivCode = findViewById(R.id.iv_code);
        Bitmap bitmap = CodeCreator.createQRCode("123456", 400, 400, null);
        ivCode.setImageBitmap(bitmap);
    }

    private void getKey() {
        EasyHttp.get("/app/device/acs/getKey")
                .headers("token", SPUtils.getInstance().getString(SPKeyGlobal.USER_TOKEN))
                .params("deviceId", deviceId + "")
                .timeStamp(true)
                .execute(new SimpleCallBack<CipherModel>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort("失败");
                    }

                    @Override
                    public void onSuccess(CipherModel response) {
                        pw_show.setVisibility(View.VISIBLE);
                        pw_show.setText("有效密码：" + response.getCipherValue());
                    }
                });
    }
}
