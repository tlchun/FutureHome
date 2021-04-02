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

@Route(path = RouterActivityPath.Home.PAGER_SHEQU)
public class SheQuActivity extends BaseActivity<ActivityShequBinding, SheQuViewModel> {

    private RecyclerView rv_face_list;
    private SheQuAdapter mAdapter;//适配器
    private LinearLayoutManager mLinearLayoutManager;//布局管理器
    private List mList = new ArrayList();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_shequ;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        TitleView titleView = findViewById(R.id.title);
        titleView.setTitleText("社区服务");

        rv_face_list = findViewById(R.id.rv_shequ_list);
        //创建布局管理器，垂直设置LinearLayoutManager.VERTICAL，水平设置LinearLayoutManager.HORIZONTAL
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mList.add(new SheQuModel());
        mList.add(new SheQuModel());
        mList.add(new SheQuModel());
        mList.add(new SheQuModel());

        //创建适配器，将数据传递给适配器
        mAdapter = new SheQuAdapter(mList);
        //设置布局管理器
        rv_face_list.setLayoutManager(mLinearLayoutManager);
        //设置适配器adapter
        rv_face_list.setAdapter(mAdapter);
    }
}
