package com.goldze.base.sdk.rtc;

import android.content.Context;

public interface IRtcSDK {

    void init();

    boolean login(String account);


    void prepare(Context context, TRTCCallingDelegate tRTCCallingDelegate);

    /**
     * 开始视频通话
     *
     * @param account 账号
     */
    void startCallSomeone(Context context, String account);

    /**
     * 开始视频通话
     */
    void startSample(Context context);

    void someoneCall(Context context, String sponsor);

    void addDelegate(TRTCCallingDelegate delegate);

    void removeDelegate(TRTCCallingDelegate delegate);

    void release();

    void logout(Context context);
}
