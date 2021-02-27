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
public class MsgFragment extends BaseFragment<FragmentMsgBinding, MsgViewModel>  {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_msg;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.uc.pDataEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String account) {
                IRtcSDK iRtcSDK = HService.getService(IRtcSDK.class);
                if (iRtcSDK != null) {
                    iRtcSDK.startSample(getActivity());
                }
            }
        });

    }
}
