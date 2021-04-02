package com.goldze.home.ui.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.base.router.RouterActivityPath;
import com.goldze.base.widget.TitleView;
import com.goldze.home.BR;
import com.goldze.home.R;
import com.goldze.home.databinding.ActivityShequBinding;
import com.goldze.home.ui.adapter.SheQuAdapter;
import com.goldze.home.ui.model.SheQuModel;
import com.goldze.home.ui.viewmodel.SheQuViewModel;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;

@Route(path = RouterActivityPath.Home.PAGER_WUYE_PAY)
public class WuYePayActivity extends BaseActivity<ActivityShequBinding, SheQuViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_wuye_pay;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        TitleView titleView = findViewById(R.id.title);
        titleView.setTitleText("物业缴费");

    }
}
