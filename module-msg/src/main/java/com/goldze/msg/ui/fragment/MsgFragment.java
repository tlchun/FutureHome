package com.goldze.msg.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.goldze.base.lib.sdk.HService;
import com.goldze.base.router.RouterFragmentPath;
import com.goldze.base.sdk.rtc.IRtcSDK;
import com.goldze.base.sdk.rtc.TRTCCallingDelegate;
import com.goldze.msg.R;
import com.goldze.msg.BR;
import com.goldze.msg.databinding.FragmentMsgBinding;
import com.goldze.msg.ui.viewmodel.MsgViewModel;

import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import me.goldze.mvvmhabit.base.BaseFragment;


/**
 * Created by goldze on 2018/6/21
 */
@Route(path = RouterFragmentPath.Msg.PAGER_MSG)
public class MsgFragment extends BaseFragment<FragmentMsgBinding, MsgViewModel> implements TRTCCallingDelegate {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_msg;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    private IRtcSDK iRtcSDK;

    @Override
    public void initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.MICROPHONE, PermissionConstants.CAMERA)
                    .request();
        }

        iRtcSDK = HService.getService(IRtcSDK.class);
        viewModel.uc.pDataEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String account) {
                if (iRtcSDK != null) {
                    iRtcSDK.startCallSomeone(getActivity(), account, MsgFragment.this);
                }
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (iRtcSDK != null) {
            iRtcSDK.release();
        }
    }


    @Override
    public void onError(int code, String msg) {

    }

    @Override
    public void onInvited(String sponsor, List<String> userIdList, boolean isFromGroup, int callType) {
        if (iRtcSDK != null) {
            iRtcSDK.someoneCall(getActivity(),sponsor);
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
