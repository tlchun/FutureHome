package com.goldze.main.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.goldze.base.mqtt.IGetMessageCallBack;
import com.goldze.base.mqtt.MQTTService;
import com.goldze.base.mqtt.MyServiceConnection;
import com.goldze.base.contract._Login;
import com.goldze.base.contract._LoginOut;
import com.goldze.base.global.SPKeyGlobal;
import com.goldze.base.lib.sdk.HService;
import com.goldze.base.router.RouterActivityPath;
import com.goldze.base.router.RouterFragmentPath;
import com.goldze.base.sdk.rtc.IRtcSDK;
import com.goldze.base.sdk.rtc.TRTCCallingDelegate;
import com.goldze.main.R;
import com.goldze.main.BR;
import com.goldze.main.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.RxSubscriptions;
import me.goldze.mvvmhabit.utils.SPUtils;


@Route(path = RouterActivityPath.Main.PAGER_MAIN)
public class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> implements IGetMessageCallBack, TRTCCallingDelegate {
    private List<Fragment> mFragments;
    private Fragment currentFragment;
    private IRtcSDK iRtcSDK;

    private MyServiceConnection serviceConnection;

    private Disposable subscribe;
    private Disposable subscribe2;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        fullScreen(this);
        //初始化Fragment
        initFragment();
        //初始化底部Button
        initBottomTab();

        subscribe = RxBus.getDefault().toObservable(_Login.class)
                .subscribe(new Consumer<_Login>() {
                    @Override
                    public void accept(_Login l) throws Exception {
                        //登录成功后绑定mqtt
                        bindMqtt();
                        //解除注册
                        RxSubscriptions.remove(subscribe);
                    }
                });
        subscribe2 = RxBus.getDefault().toObservable(_LoginOut.class)
                .subscribe(new Consumer<_LoginOut>() {
                    @Override
                    public void accept(_LoginOut l) throws Exception {
                        //退出登录成功后解除绑定mqtt
                        unbindMqtt();
                        //解除注册
                        RxSubscriptions.remove(subscribe2);
                    }
                });
        RxSubscriptions.add(subscribe);
        RxSubscriptions.add(subscribe2);

        String userInfo = SPUtils.getInstance().getString(SPKeyGlobal.USER_INFO);
        if (!TextUtils.isEmpty(userInfo)) {
            bindMqtt();
        }

        initRtc();
    }

    private void bindMqtt() {
        serviceConnection = new MyServiceConnection();
        serviceConnection.setIGetMessageCallBack(MainActivity.this);
        Intent intent = new Intent(this, MQTTService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbindMqtt() {
        if (serviceConnection != null) {
            unbindService(serviceConnection);
        }
    }

    /**
     * 初始化RTC
     */
    private void initRtc() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.MICROPHONE, PermissionConstants.CAMERA)
                    .request();
        }
        iRtcSDK = HService.getService(IRtcSDK.class);
        if (iRtcSDK != null) {
            iRtcSDK.prepare(this, this);
        }
    }

    private void initFragment() {
        //ARouter拿到多Fragment(这里需要通过ARouter获取，不能直接new,因为在组件独立运行时，宿主app是没有依赖其他组件，所以new不到其他组件的Fragment)
        Fragment homeFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Home.PAGER_HOME).navigation();
        Fragment workFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Work.PAGER_WORK).navigation();
        Fragment meFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.User.PAGER_ME).navigation();

        mFragments = new ArrayList<>();
        mFragments.add(homeFragment);
        mFragments.add(workFragment);
        mFragments.add(meFragment);

        if (homeFragment != null) {
            //默认选中第一个
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            currentFragment = homeFragment;
            transaction.add(R.id.frameLayout, homeFragment).show(homeFragment).commit();
        }
    }

    private void initBottomTab() {
        binding.llHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ivHost.setImageResource(R.mipmap.act_host2);
                binding.tvHost.setTextColor(R.color.blue_text_color);
                binding.ivMy.setImageResource(R.mipmap.act_my1);
                binding.tvMy.setTextColor(R.color.textColorVice);
                showFragment(mFragments.get(0));
            }
        });
        binding.llOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ivHost.setImageResource(R.mipmap.act_host1);
                binding.tvHost.setTextColor(R.color.textColorVice);
                binding.ivMy.setImageResource(R.mipmap.act_my1);
                binding.tvMy.setTextColor(R.color.textColorVice);
                showFragment(mFragments.get(1));
            }
        });
        binding.llMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ivHost.setImageResource(R.mipmap.act_host1);
                binding.tvHost.setTextColor(R.color.textColorVice);
                binding.ivMy.setImageResource(R.mipmap.act_my2);
                binding.tvMy.setTextColor(R.color.blue_text_color);
                showFragment(mFragments.get(2));
            }
        });
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

    private void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    @Override
    public void setMessage(String message) {
        Log.d("MQTT msg:", message);
    }

    @Override
    protected void onDestroy() {
        unbindMqtt();
        if (iRtcSDK != null) {
            iRtcSDK.release();
        }
        super.onDestroy();
    }

    @Override
    public void onError(int code, String msg) {

    }

    @Override
    public void onInvited(String sponsor, List<String> userIdList, boolean isFromGroup, int callType) {
        if (iRtcSDK != null) {
            iRtcSDK.someoneCall(this, sponsor);
        }
    }

    @Override
    public void onGroupCallInviteeListUpdate(List<String> userIdList) {

    }

    @Override
    public void onUserEnter(String userId) {

    }

    @Override
    public void onUserLeave(String userId) {

    }

    @Override
    public void onReject(String userId) {

    }

    @Override
    public void onNoResp(String userId) {

    }

    @Override
    public void onLineBusy(String userId) {

    }

    @Override
    public void onCallingCancel() {

    }

    @Override
    public void onCallingTimeout() {

    }

    @Override
    public void onCallEnd() {

    }

    @Override
    public void onUserVideoAvailable(String userId, boolean isVideoAvailable) {

    }

    @Override
    public void onUserAudioAvailable(String userId, boolean isVideoAvailable) {

    }

    @Override
    public void onUserVoiceVolume(Map<String, Integer> volumeMap) {

    }
}
