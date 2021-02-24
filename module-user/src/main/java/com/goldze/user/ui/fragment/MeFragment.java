package com.goldze.user.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.facebook.drawee.view.SimpleDraweeView;
import com.goldze.base.global.SPKeyGlobal;
import com.goldze.base.router.RouterFragmentPath;
import com.goldze.user.R;
import com.goldze.user.BR;
import com.goldze.user.databinding.FragmentMeBinding;
import com.goldze.user.ui.viewmodel.SettinglViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.SPUtils;


@Route(path = RouterFragmentPath.User.PAGER_ME)
public class MeFragment extends BaseFragment<FragmentMeBinding, SettinglViewModel> {
    private SimpleDraweeView simpleDraweeView;

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
        simpleDraweeView = binding.getRoot().findViewById(R.id.iv_pic);
        simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        String url = SPUtils.getInstance().getString(SPKeyGlobal.USER_PIC);
        if (!TextUtils.isEmpty(url)) {
            simpleDraweeView.setImageURI(Uri.parse(url));
        }
    }
}
