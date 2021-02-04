package com.goldze.user.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.base.router.RouterFragmentPath;
import com.goldze.user.R;
import com.goldze.user.BR;
import com.goldze.user.databinding.FragmentMeBinding;
import com.goldze.user.ui.viewmodel.SettinglViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;


@Route(path = RouterFragmentPath.User.PAGER_ME)
public class MeFragment extends BaseFragment<FragmentMeBinding, SettinglViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_me;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
