package com.goldze.user.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.base.router.RouterActivityPath;
import com.goldze.user.BR;
import com.goldze.user.R;
import com.goldze.user.databinding.ActivityUserDetailBinding;
import com.goldze.user.ui.viewmodel.UserDetailViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;


@Route(path = RouterActivityPath.User.PAGER_USERDETAIL)
public class UserDetailActivity extends BaseActivity<ActivityUserDetailBinding, UserDetailViewModel> {

    @Override
    public void initParam() {

        ARouter.getInstance().inject(this);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_user_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {

    }
}

