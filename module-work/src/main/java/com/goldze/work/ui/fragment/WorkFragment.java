package com.goldze.work.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.base.contract._Login;
import com.goldze.base.global.SPKeyGlobal;
import com.goldze.base.router.RouterActivityPath;
import com.goldze.base.router.RouterFragmentPath;
import com.goldze.work.DeviceModel;
import com.goldze.work.MyRecycleViewAdapter;
import com.goldze.work.R;
import com.goldze.work.BR;
import com.goldze.work.databinding.FragmentWorkBinding;
import com.goldze.work.ui.viewmodel.WorkViewModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by goldze on 2018/6/21
 */
@Route(path = RouterFragmentPath.Work.PAGER_WORK)
public class WorkFragment extends BaseFragment<FragmentWorkBinding, WorkViewModel> {

    private RecyclerView rv_device_list;
    private MyRecycleViewAdapter mAdapter;//适配器
    private LinearLayoutManager mLinearLayoutManager;//布局管理器
    private List mList = new ArrayList();

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_work;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        rv_device_list = binding.getRoot().findViewById(R.id.rv_device_list);

        EasyHttp.get("/app/user/getDeviceList")
                .headers("token", SPUtils.getInstance().getString(SPKeyGlobal.USER_TOKEN))
                .params("pageIndex", 1 + "")
                .params("pageSize", 20 + "")
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
        mAdapter = new MyRecycleViewAdapter(mList);
        //设置布局管理器
        rv_device_list.setLayoutManager(mLinearLayoutManager);
        //设置适配器adapter
        rv_device_list.setAdapter(mAdapter);
    }
}
