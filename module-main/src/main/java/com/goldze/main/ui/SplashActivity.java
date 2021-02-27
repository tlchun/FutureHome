package com.goldze.main.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.base.global.SPKeyGlobal;
import com.goldze.base.router.RouterActivityPath;
import com.goldze.main.R;

import androidx.annotation.Nullable;
import me.goldze.mvvmhabit.utils.SPUtils;


public class SplashActivity extends Activity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                inMain();
            }
        }, 3 * 1000);
    }

    /**
     * 进入主页面
     */
    private void inMain() {
        initData();
    }

    public void initData() {
        String userInfo = SPUtils.getInstance().getString(SPKeyGlobal.USER_INFO);
        if (!TextUtils.isEmpty(userInfo)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            ARouter.getInstance().build(RouterActivityPath.Sign.PAGER_LOGIN).navigation();
            finish();
        }
    }
}
