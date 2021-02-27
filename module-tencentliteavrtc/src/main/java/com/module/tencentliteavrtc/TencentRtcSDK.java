package com.module.tencentliteavrtc;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
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

public class TencentRtcSDK implements IRtcSDK {

    private TRTCCalling mTRTCCalling;
    private TRTCCallingDelegate mRTCCallingDelegate;


    @Override
    public void startCallSomeone(final Context context, final String account, TRTCCallingDelegate tRTCCallingDelegate) {
        mRTCCallingDelegate = tRTCCallingDelegate;
        final UserModel mSelfModel = ProfileManager.getInstance().getUserModel();
        if (mSelfModel.userId.equals(account)) {
            ToastUtils.showShort("不能呼叫自己");
            return;
        }
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
        });
    }

    @Override
    public void someoneCall(Context context,String sponsor) {

        TRTCVideoCallActivity.UserInfo selfInfo = new TRTCVideoCallActivity.UserInfo();
        selfInfo.userId = ProfileManager.getInstance().getUserModel().userId;
        selfInfo.userAvatar = ProfileManager.getInstance().getUserModel().userAvatar;
        selfInfo.userName = ProfileManager.getInstance().getUserModel().userName;
        TRTCVideoCallActivity.UserInfo callUserInfo = new TRTCVideoCallActivity.UserInfo();
        callUserInfo.userId = sponsor;
        callUserInfo.userAvatar = "";
        callUserInfo.userName = sponsor;
        TRTCVideoCallActivity.startBeingCall(context, selfInfo, callUserInfo, null);
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
}
