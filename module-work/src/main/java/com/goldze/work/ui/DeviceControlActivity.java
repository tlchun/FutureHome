package com.goldze.work.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.base.global.SPKeyGlobal;
import com.goldze.base.router.RouterActivityPath;
import com.goldze.work.R;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;


import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class DeviceControlActivity extends AppCompatActivity {

    private String deviceMac;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_work);

        deviceMac = getIntent().getStringExtra("deviceMac");

        findViewById(R.id.tv_face).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouterActivityPath.User.PAGER_USERDETAIL).withString("deviceMac", deviceMac).navigation();
            }
        });

        findViewById(R.id.pw_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getKey();
            }
        });
    }

    private void getKey() {
        EasyHttp.get("/app/device/acs/getKey")
                .headers("token", SPUtils.getInstance().getString(SPKeyGlobal.USER_TOKEN))
                .timeStamp(true)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort("失败");
                    }

                    @Override
                    public void onSuccess(String response) {
                        System.out.println("hhhh=" + response);
                    }
                });
    }
}