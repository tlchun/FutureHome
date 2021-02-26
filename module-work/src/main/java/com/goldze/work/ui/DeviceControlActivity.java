package com.goldze.work.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.base.global.SPKeyGlobal;
import com.goldze.base.router.RouterActivityPath;
import com.goldze.work.R;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class DeviceControlActivity extends AppCompatActivity {

    private String deviceMac;
    private long deviceId;

    private TextView pw_show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_work);

        deviceMac = getIntent().getStringExtra("deviceMac");
        deviceId = getIntent().getLongExtra("deviceId", 0);

        findViewById(R.id.rl_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.tv_face).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouterActivityPath.User.PAGER_USERDETAIL).withString("deviceMac", deviceMac).navigation();
            }
        });
        findViewById(R.id.tv_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeviceControlActivity.this, CardActivity.class);
                intent.putExtra("deviceMac", deviceMac);
                startActivity(intent);
            }
        });
        findViewById(R.id.tv_face_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeviceControlActivity.this, FaceRecordActivity.class);
                intent.putExtra("deviceMac", deviceMac);
                startActivity(intent);
            }
        });
        findViewById(R.id.tv_card_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeviceControlActivity.this, CardRecordActivity.class);
                intent.putExtra("deviceMac", deviceMac);
                startActivity(intent);
            }
        });

        pw_show = findViewById(R.id.pw_show);
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
