package com.goldze.user.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.base.router.RouterActivityPath;
import com.goldze.user.BR;
import com.goldze.user.R;
import com.goldze.user.databinding.ActivitySettingBinding;
import com.goldze.user.ui.viewmodel.SettinglViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;

@Route(path = RouterActivityPath.User.PAGER_USERDETAIL)
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

    }
}

