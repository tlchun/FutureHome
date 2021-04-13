package com.goldze.home.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.base.global.SPKeyGlobal;
import com.goldze.base.router.RouterActivityPath;
import com.goldze.base.widget.TitleView;
import com.goldze.home.BR;
import com.goldze.home.R;
import com.goldze.home.ui.adapter.MyDeviceListAdapter;
import com.goldze.home.ui.model.DeviceModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 添加投诉建议页面
 */
@Route(path = RouterActivityPath.Home.SUGGEST_ADD)
public class CompSugAddActivity extends BaseActivity {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_add_comp_sug;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {

        TitleView titleView = findViewById(R.id.title);
        titleView.setTitleText("投诉建议");

    }
}
