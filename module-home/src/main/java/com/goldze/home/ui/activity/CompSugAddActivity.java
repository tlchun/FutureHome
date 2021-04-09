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
 * 投诉建议页面
 */
@Route(path = RouterActivityPath.Home.SUGGEST_ADD)
public class CompSugAddActivity extends BaseActivity {

    private RecyclerView rv_device_list;
    private MyDeviceListAdapter mAdapter;//适配器
    private LinearLayoutManager mLinearLayoutManager;//布局管理器
    private List mList = new ArrayList();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_device_list;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {

        TitleView titleView = findViewById(R.id.title);
        titleView.setTitleText("投诉建议");

        rv_device_list = binding.getRoot().findViewById(R.id.rv_device_list);

        EasyHttp.get("/app/suggestion/list")
                .headers("token", SPUtils.getInstance().getString(SPKeyGlobal.USER_TOKEN))
                .timeStamp(true)
                .execute(new SimpleCallBack<List<DeviceModel>>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort("失败");
                    }

                    @Override
                    public void onSuccess(List<DeviceModel> response) {
                        mList.addAll(response);
                        mAdapter.notifyDataSetChanged();
                    }
                });

        //创建布局管理器，垂直设置LinearLayoutManager.VERTICAL，水平设置LinearLayoutManager.HORIZONTAL
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //创建适配器，将数据传递给适配器
        mAdapter = new MyDeviceListAdapter(mList);
        //设置布局管理器
        rv_device_list.setLayoutManager(mLinearLayoutManager);
        //设置适配器adapter
        rv_device_list.setAdapter(mAdapter);
    }
}
