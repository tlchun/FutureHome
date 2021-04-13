package com.goldze.home.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.base.global.SPKeyGlobal;
import com.goldze.base.router.RouterFragmentPath;
import com.goldze.home.BR;
import com.goldze.home.R;
import com.goldze.home.ui.adapter.MyDeviceListAdapter;
import com.goldze.home.ui.model.DeviceModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;


@Route(path = RouterFragmentPath.Home.SUG)
public class ComplainFragment extends BaseFragment {

    private RecyclerView list;
    private MyDeviceListAdapter mAdapter;//适配器
    private LinearLayoutManager mLinearLayoutManager;//布局管理器
    private List mList = new ArrayList();

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_sug;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        list = binding.getRoot().findViewById(R.id.list);

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
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        //创建适配器，将数据传递给适配器
        mAdapter = new MyDeviceListAdapter(mList);
        //设置布局管理器
        list.setLayoutManager(mLinearLayoutManager);
        //设置适配器adapter
        list.setAdapter(mAdapter);
    }

    @Override
    public void initViewObservable() {

    }
}
