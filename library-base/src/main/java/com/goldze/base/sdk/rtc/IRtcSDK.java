package com.goldze.base.sdk.rtc;

import android.content.Context;

public interface IRtcSDK {

    void init();

    boolean login(String account);

    /**
     * 开始视频通话
     *
     * @param account 账号
     */
    void startCallSomeone(Context context, String account, TRTCCallingDelegate tRTCCallingDelegate);

    void someoneCall(Context context, String sponsor);

    void addDelegate(TRTCCallingDelegate delegate);

    void removeDelegate(TRTCCallingDelegate delegate);

    void release();

    void logout(Context context);
}
