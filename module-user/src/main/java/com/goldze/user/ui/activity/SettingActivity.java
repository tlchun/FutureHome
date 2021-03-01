package com.goldze.user.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.base.router.RouterActivityPath;
import com.goldze.user.BR;
import com.goldze.user.R;
import com.goldze.user.databinding.ActivitySettingBinding;
import com.goldze.user.ui.viewmodel.SettinglViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

@Route(path = RouterActivityPath.User.PAGER_USERDISTURB)
public class SettingActivity extends BaseActivity<ActivitySettingBinding, SettinglViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_setting;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        findViewById(R.id.tv_loginout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.getInstance().clear();
                ToastUtils.showShort("退出成功");
                ARouter.getInstance().build(RouterActivityPath.Sign.PAGER_LOGIN).navigation();
                finish();
            }
        });
    }
}

