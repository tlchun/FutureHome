package com.goldze.work.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.goldze.base.global.SPKeyGlobal;
import com.goldze.work.MyCardRecordAdapter;
import com.goldze.work.MyFaceRecordAdapter;
import com.goldze.work.R;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by goldze on 2018/6/21
 */
public class CardRecordActivity extends AppCompatActivity {

    private RecyclerView rv_card_list;
    private MyCardRecordAdapter mAdapter;//适配器
    private LinearLayoutManager mLinearLayoutManager;//布局管理器
    private List mList = new ArrayList();

    private String deviceMac;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_record);
        initData();
    }

    public void initData() {

        findViewById(R.id.rl_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        deviceMac = getIntent().getStringExtra("deviceMac");

        rv_card_list = findViewById(R.id.rv_card_list);

        EasyHttp.get("/app/device/acs/getDeviceCardNoList")
                .headers("token", SPUtils.getInstance().getString(SPKeyGlobal.USER_TOKEN))
                .params("deviceMac", deviceMac)
                .params("pageIndex", 1 + "")
                .params("pageSize", 20 + "")
                .timeStamp(true)
                .execute(new SimpleCallBack<List<CardRecordModel>>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort("失败");
                    }

                    @Override
                    public void onSuccess(List<CardRecordModel> response) {
                        mList.addAll(response);
                        mAdapter.notifyDataSetChanged();
                    }
                });

        //创建布局管理器，垂直设置LinearLayoutManager.VERTICAL，水平设置LinearLayoutManager.HORIZONTAL
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //创建适配器，将数据传递给适配器
        mAdapter = new MyCardRecordAdapter(mList);
        //设置布局管理器
        rv_card_list.setLayoutManager(mLinearLayoutManager);
        //设置适配器adapter
        rv_card_list.setAdapter(mAdapter);
    }
}
