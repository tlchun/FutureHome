package com.goldze.sign.ui;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.goldze.base.MQTTService;
import com.goldze.base.contract._Login;
import com.goldze.base.global.SPKeyGlobal;
import com.goldze.base.lib.sdk.HService;
import com.goldze.base.router.RouterActivityPath;
import com.goldze.base.sdk.rtc.IRtcSDK;
import com.goldze.sign.model.LoginModel;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.bus.RxBus;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by goldze on 2018/6/21 0021.
 */

public class LoginViewModel extends BaseViewModel {
    //用户名的绑定
    public ObservableField<String> userName = new ObservableField<>("");
    //密码的绑定
    public ObservableField<String> password = new ObservableField<>("");
    //用户名清除按钮的显示隐藏绑定
    public ObservableInt clearBtnVisibility = new ObservableInt();
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<Boolean> pSwitchEvent = new SingleLiveEvent<>();
    }

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    //清除用户名的点击事件, 逻辑从View层转换到ViewModel层
    public BindingCommand clearUserNameOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            userName.set("");
        }
    });
    //密码显示开关  (你可以尝试着狂按这个按钮,会发现它有防多次点击的功能)
    public BindingCommand passwordShowSwitchOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //让观察者的数据改变,逻辑从ViewModel层转到View层，在View层的监听则会被调用
            uc.pSwitchEvent.setValue(uc.pSwitchEvent.getValue() == null || !uc.pSwitchEvent.getValue());
        }
    });
    //用户名输入框焦点改变的回调事件
    public BindingCommand<Boolean> onFocusChangeCommand = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean hasFocus) {
            if (hasFocus) {
                clearBtnVisibility.set(View.VISIBLE);
            } else {
                clearBtnVisibility.set(View.INVISIBLE);
            }
        }
    });
    //登录按钮的点击事件
    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            login();
        }
    });

    /**
     * 网络模拟一个登陆操作
     **/
    private void login() {
        if (TextUtils.isEmpty(userName.get())) {
            ToastUtils.showShort("请输入账号！");
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort("请输入密码！");
            return;
        }
        EasyHttp.get("/app/account/login")
                .readTimeOut(30 * 1000)//局部定义读超时
                .writeTimeOut(30 * 1000)
                .connectTimeout(30 * 1000)
                .params("username", userName.get())
                .params("password", password.get())
                .timeStamp(true)
                .execute(new SimpleCallBack<LoginModel>() {
                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showShort("登录失败");
                    }

                    @Override
                    public void onSuccess(LoginModel response) {
                        ToastUtils.showShort("登录成功");
                        //保存用户信息
                        SPUtils.getInstance().put(SPKeyGlobal.USER_INFO, userName.get());
                        SPUtils.getInstance().put(SPKeyGlobal.USER_ID, response.getAccount().getUserId());
                        SPUtils.getInstance().put(SPKeyGlobal.USER_PIC, response.getAccount().getHeadImgUrl());
                        SPUtils.getInstance().put(SPKeyGlobal.USER_TOKEN, response.getToken());
                        SPUtils.getInstance().put(SPKeyGlobal.USER_MQTT, String.valueOf(response.getMqtt().getId()));
                        SPUtils.getInstance().put(SPKeyGlobal.USER_MQTT_USERNAME, String.valueOf(response.getMqtt().getUsername()));
                        SPUtils.getInstance().put(SPKeyGlobal.USER_MQTT_PWD, String.valueOf(response.getMqtt().getShowPassword()));

                        MQTTService.setId(response.getMqtt().getId() + "");
                        MQTTService.setUserName(response.getMqtt().getUsername());
                        MQTTService.setPassWord(response.getMqtt().getPassword());

                        ARouter.getInstance().build(RouterActivityPath.Main.PAGER_MAIN).navigation();
                        _Login _login = new _Login();
                        //采用ARouter+RxBus实现组件间通信
                        RxBus.getDefault().post(_login);
                        //视频通话
                        IRtcSDK iRtcSDK = HService.getService(IRtcSDK.class);
                        if (iRtcSDK != null && response.getMqtt() != null) {
                            iRtcSDK.login(String.valueOf(response.getMqtt().getId()));
                            KLog.e("腾讯视频通话 登录成功! userId：", response.getMqtt().getId());
                        }
                        //关闭页面
                        finish();
                    }
                });
    }
}
