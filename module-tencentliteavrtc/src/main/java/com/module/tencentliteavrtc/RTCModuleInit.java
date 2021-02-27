package com.module.tencentliteavrtc;

import android.app.Application;

import com.blankj.utilcode.util.ToastUtils;
import com.goldze.base.base.IModuleInit;
import com.goldze.base.lib.sdk.HService;
import com.goldze.base.sdk.rtc.IRtcSDK;
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

/**
 * Created by goldze on 2018/6/21 0021.
 */

public class RTCModuleInit implements IModuleInit {
    @Override
    public boolean onInitAhead(Application application) {
        KLog.e("视频通话模块初始化 -- onInitAhead");
        HService.registerService(TencentRtcSDK.class);
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        KLog.e("视频通话模块初始化 -- onInitLow");
        IRtcSDK iRtcSDK = HService.getService(IRtcSDK.class);
        if (iRtcSDK != null) {
            iRtcSDK.init();
        }

        return false;
    }
}
