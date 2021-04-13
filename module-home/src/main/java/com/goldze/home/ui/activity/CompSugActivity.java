package com.goldze.home.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.base.global.SPKeyGlobal;
import com.goldze.base.router.RouterActivityPath;
import com.goldze.base.router.RouterFragmentPath;
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
public class CompSugActivity extends BaseActivity {

    private List<Fragment> mFragments;
    private Fragment currentFragment;
    private Fragment suggestFragment;
    private Fragment complainFragment;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_comp_sug;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {

        TitleView titleView = findViewById(R.id.title);
        titleView.setTitleText("投诉建议");
        titleView.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouterActivityPath.Home.SUGGEST_ADD).navigation();
            }
        }, R.mipmap.add);

        RelativeLayout suggest = findViewById(R.id.suggest);
        RelativeLayout complain = findViewById(R.id.complain);
        final View view_suggest = findViewById(R.id.view_suggest);
        final View view_complain = findViewById(R.id.view_complain);

        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_suggest.setVisibility(View.VISIBLE);
                view_complain.setVisibility(View.INVISIBLE);
                showFragment(suggestFragment);
            }
        });
        complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_complain.setVisibility(View.VISIBLE);
                view_suggest.setVisibility(View.INVISIBLE);
                showFragment(complainFragment);
            }
        });

        //初始化Fragment
        initFragment();
    }

    private void initFragment() {
        //ARouter拿到多Fragment(这里需要通过ARouter获取，不能直接new,因为在组件独立运行时，宿主app是没有依赖其他组件，所以new不到其他组件的Fragment)
        suggestFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Home.SUG).navigation();
        complainFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Home.COMPLAIN).navigation();

        mFragments = new ArrayList<>();
        mFragments.add(suggestFragment);
        mFragments.add(complainFragment);

        if (suggestFragment != null) {
            //默认选中第一个
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            currentFragment = suggestFragment;
            transaction.add(R.id.frameLayout, suggestFragment).show(suggestFragment).commit();
        }
    }

    /**
     * 展示Fragment
     */
    private void showFragment(Fragment fragment) {
        if (currentFragment != fragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(currentFragment);
            currentFragment = fragment;
            if (!fragment.isAdded()) {
                transaction.add(R.id.frameLayout, fragment).show(fragment).commit();
            } else {
                transaction.show(fragment).commit();
            }
        }
    }

}
