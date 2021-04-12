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

    private ImageView ivCode;

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;

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

        ivCode = findViewById(R.id.iv_code);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);

        getKey();
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
                        if (response != null && response.getCipherValue() != null && response.getCipherValue().length() == 4) {
                            Bitmap bitmap = CodeCreator.createQRCode(response.getCipherValue(), 400, 400, null);
                            ivCode.setImageBitmap(bitmap);
                            tv1.setText(response.getCipherValue().charAt(0) + "");
                            tv2.setText(response.getCipherValue().charAt(1) + "");
                            tv3.setText(response.getCipherValue().charAt(2) + "");
                            tv4.setText(response.getCipherValue().charAt(3) + "");
                        }
                    }
                });
    }
}
