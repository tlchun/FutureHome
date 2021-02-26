package com.goldze.msg.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by goldze on 2018/6/21.
 */

public class MsgViewModel extends BaseViewModel {
    public MsgViewModel(@NonNull Application application) {
        super(application);
    }

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();


    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<String> pDataEvent = new SingleLiveEvent<>();
    }

    //登录按钮的点击事件
    public BindingCommand rtcOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            uc.pDataEvent.setValue("111");
        }
    });

}
