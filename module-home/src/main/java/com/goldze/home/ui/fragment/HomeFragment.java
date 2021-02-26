package com.goldze.home.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.base.router.RouterFragmentPath;
import com.goldze.home.BR;
import com.goldze.home.R;
import com.goldze.home.databinding.FragmentHomeBinding;
import com.goldze.home.ui.viewmodel.HomeViewModel;

import androidx.annotation.Nullable;
import me.goldze.mvvmhabit.base.BaseFragment;


@Route(path = RouterFragmentPath.Home.PAGER_HOME)
public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }
}
