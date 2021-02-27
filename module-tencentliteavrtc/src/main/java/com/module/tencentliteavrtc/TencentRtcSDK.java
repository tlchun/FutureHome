package com.module.tencentliteavrtc;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.goldze.base.global.SPKeyGlobal;
import com.goldze.base.router.RouterActivityPath;
import com.goldze.base.sdk.rtc.IRtcSDK;
import com.goldze.base.sdk.rtc.TRTCCallingDelegate;
import com.module.tencentliteavrtc.model.TRTCCalling;
import com.module.tencentliteavrtc.model.impl.TRTCCallingImpl;
import com.module.tencentliteavrtc.ui.TRTCCallingEntranceActivity;
import com.module.tencentliteavrtc.ui.audiocall.TRTCAudioCallActivity;
import com.module.tencentliteavrtc.ui.videocall.TRTCVideoCallActivity;
import com.module.tencentliteavrtc.usr.GenerateTestUserSig;
import com.module.tencentliteavrtc.usr.ProfileManager;
import com.module.tencentliteavrtc.usr.UserModel;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.SPUtils;

public class TencentRtcSDK implements IRtcSDK {

    private TRTCCalling mTRTCCalling;
    private TRTCCallingDelegate mRTCCallingDelegate;


    @Override
    public void init() {
        //检查登录状态
        String mqttId = SPUtils.getInstance().getString(SPKeyGlobal.USER_MQTT);
        if (!TextUtils.isEmpty(mqttId)) {
            login(mqttId);
            KLog.e("腾讯视频通话SKD 登录成功! userId：", mqttId);
        }
    }

    @Override
    public boolean login(String account) {
        ProfileManager.getInstance().login(account, "", new ProfileManager.ActionCallback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailed(int code, String msg) {
            }
        });
        return true;
    }

    @Override
    public void prepare(final Context context, TRTCCallingDelegate tRTCCallingDelegate) {
        mRTCCallingDelegate = tRTCCallingDelegate;
        //登录成功
        mTRTCCalling = TRTCCallingImpl.sharedInstance(context);
        mTRTCCalling.addDelegate(tRTCCallingDelegate);
        int appid = GenerateTestUserSig.SDKAPPID;
        String userId = ProfileManager.getInstance().getUserModel().userId;
        String userSig = ProfileManager.getInstance().getUserModel().userSig;
        mTRTCCalling.login(appid, userId, userSig, new TRTCCalling.ActionCallBack() {
            @Override
            public void onError(int code, String msg) {
                ToastUtils.showShort("腾讯IM登录失败");
            }

            @Override
            public void onSuccess() {
                ToastUtils.showShort("腾讯IM登录成功");
            }
        });
    }

    @Override
    public void startCallSomeone(Context context,String account) {
        final UserModel mSelfModel = ProfileManager.getInstance().getUserModel();
        if (mSelfModel.userId.equals(account)) {
            ToastUtils.showShort("不能呼叫自己");
            return;
        }
        TRTCVideoCallActivity.UserInfo selfInfo = new TRTCVideoCallActivity.UserInfo();
        selfInfo.userId = mSelfModel.userId;
        selfInfo.userAvatar = mSelfModel.userAvatar;
        selfInfo.userName = "";
        List<TRTCVideoCallActivity.UserInfo> callUserInfoList = new ArrayList<>();
        TRTCVideoCallActivity.UserInfo callUserInfo = new TRTCVideoCallActivity.UserInfo();
        callUserInfo.userId = account;
        callUserInfo.userAvatar = "";
        callUserInfo.userName = "";
        callUserInfoList.add(callUserInfo);
        ToastUtils.showShort("视频呼叫:" + callUserInfo.userName);
        TRTCVideoCallActivity.startCallSomeone(context, selfInfo, callUserInfoList);
    }

    @Override
    public void startSample(Context context) {
        ARouter.getInstance().build(RouterActivityPath.Rtc.PAGER_RTC).navigation();
    }

    @Override
    public void someoneCall(final Context context, String sponsor) {

        final TRTCVideoCallActivity.UserInfo selfInfo = new TRTCVideoCallActivity.UserInfo();
        selfInfo.userId = ProfileManager.getInstance().getUserModel().userId;
        selfInfo.userAvatar = ProfileManager.getInstance().getUserModel().userAvatar;
        selfInfo.userName = ProfileManager.getInstance().getUserModel().userName;
        final TRTCVideoCallActivity.UserInfo callUserInfo = new TRTCVideoCallActivity.UserInfo();
        callUserInfo.userId = sponsor;
        callUserInfo.userName = sponsor;
        ProfileManager.getInstance().getUserInfoByUserId(sponsor, new ProfileManager.GetUserInfoCallback() {
            @Override
            public void onSuccess(UserModel model) {
                callUserInfo.userAvatar = model.userAvatar;
                TRTCVideoCallActivity.startBeingCall(context, selfInfo, callUserInfo, null);
            }

            @Override
            public void onFailed(int code, String msg) {
            }
        });


    }

    @Override
    public void addDelegate(TRTCCallingDelegate delegate) {
        mTRTCCalling.addDelegate(delegate);
    }

    @Override
    public void removeDelegate(TRTCCallingDelegate delegate) {
        mTRTCCalling.removeDelegate(delegate);
    }

    @Override
    public void release() {
        if (mTRTCCalling != null) {
            mTRTCCalling.removeDelegate(mRTCCallingDelegate);
        }
    }

    @Override
    public void logout(Context context) {
        //退出登录
        mTRTCCalling = TRTCCallingImpl.sharedInstance(context);
        mTRTCCalling.logout(new TRTCCalling.ActionCallBack() {
            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onSuccess() {

            }
        });
    }
}
