package com.module.tencentliteavrtc.debug;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.goldze.base.lib.sdk.HService;
import com.goldze.base.router.RouterActivityPath;
import com.goldze.base.sdk.rtc.IRtcSDK;
import com.goldze.base.sdk.rtc.TRTCCallingDelegate;
import com.module.tencentliteavrtc.R;
import com.module.tencentliteavrtc.model.TRTCCalling;
import com.module.tencentliteavrtc.model.impl.TRTCCallingImpl;
import com.module.tencentliteavrtc.ui.audiocall.TRTCAudioCallActivity;
import com.module.tencentliteavrtc.ui.videocall.TRTCVideoCallActivity;
import com.module.tencentliteavrtc.usr.GenerateTestUserSig;
import com.module.tencentliteavrtc.usr.ProfileManager;
import com.module.tencentliteavrtc.usr.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * 联系人选择Activity，可以通过此界面搜索已注册用户，并发起通话；
 */
public class TRTCCallingEntranceActivity extends AppCompatActivity {
    private static final String TAG = "SelectContactActivity";

    private Toolbar mToolbar;      //导航栏，主要负责监听导航栏返回按钮
    private TextView mTvTitle; //导航栏标题
    private EditText mEtSearchUser; //输入手机号码的编辑文本框
    private ImageView mIvClearSearch;//清除搜索框文本按钮
    private TextView mTvSearch;     //开始搜索用户的按钮
    private TextView mTvSelfPhone;  //自己的手机号
    private LinearLayout mLlContract;   //用来展示对方信息的layout
    private ImageView mIvAvatar;    //用来展示对方头像
    private TextView mTvUserName;   //用来展示对方昵称
    private Button mBtnStartCall; //开始呼叫按钮
    private ConstraintLayout mClTips;       //显示搜索提示信息

    private UserModel mSelfModel;    //表示当前用户的UserModel
    private UserModel mSearchModel;  //表示当前搜索的usermodel
    private int mType = TRTCCalling.TYPE_VIDEO_CALL; //表示当前是 videocall/audiocall

    private TRTCCalling mTRTCCalling;

    private TRTCCallingDelegate mTRTCCallingDelegate = new TRTCCallingDelegate() {
        // <editor-fold  desc="视频监听代码">
        @Override
        public void onError(int code, String msg) {
        }

        @Override
        public void onInvited(String sponsor, final List<String> userIdList, boolean isFromGroup, final int callType) {
            //1. 收到邀请，先到服务器查询
            ProfileManager.getInstance().getUserInfoByUserId(sponsor, new ProfileManager.GetUserInfoCallback() {
                @Override
                public void onSuccess(final UserModel model) {
                    if (callType == TRTCCalling.TYPE_VIDEO_CALL) {
                        TRTCVideoCallActivity.UserInfo selfInfo = new TRTCVideoCallActivity.UserInfo();
                        selfInfo.userId = ProfileManager.getInstance().getUserModel().userId;
                        selfInfo.userAvatar = ProfileManager.getInstance().getUserModel().userAvatar;
                        selfInfo.userName = ProfileManager.getInstance().getUserModel().userName;
                        TRTCVideoCallActivity.UserInfo callUserInfo = new TRTCVideoCallActivity.UserInfo();
                        callUserInfo.userId = model.userId;
                        callUserInfo.userAvatar = model.userAvatar;
                        callUserInfo.userName = model.userName;
                        TRTCVideoCallActivity.startBeingCall(TRTCCallingEntranceActivity.this, selfInfo, callUserInfo, null);
                    } else if (callType == TRTCCalling.TYPE_AUDIO_CALL) {
                        TRTCAudioCallActivity.UserInfo selfInfo = new TRTCAudioCallActivity.UserInfo();
                        selfInfo.userId = ProfileManager.getInstance().getUserModel().userId;
                        selfInfo.userAvatar = ProfileManager.getInstance().getUserModel().userAvatar;
                        selfInfo.userName = ProfileManager.getInstance().getUserModel().userName;
                        TRTCAudioCallActivity.UserInfo callUserInfo = new TRTCAudioCallActivity.UserInfo();
                        callUserInfo.userId = model.userId;
                        callUserInfo.userAvatar = model.userAvatar;
                        callUserInfo.userName = model.userName;
                        TRTCAudioCallActivity.startBeingCall(TRTCCallingEntranceActivity.this, selfInfo, callUserInfo, null);
                    }
                }

                @Override
                public void onFailed(int code, String msg) {

                }
            });
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
        // </editor-fold  desc="视频监听代码">
    };

    /**
     * 开始呼叫某人
     */
    private void startCallSomeone() {
        if (mType == TRTCCalling.TYPE_VIDEO_CALL) {
            TRTCVideoCallActivity.UserInfo selfInfo = new TRTCVideoCallActivity.UserInfo();
            selfInfo.userId = mSelfModel.userId;
            selfInfo.userAvatar = mSelfModel.userAvatar;
            selfInfo.userName = mSearchModel.userName;
            List<TRTCVideoCallActivity.UserInfo> callUserInfoList = new ArrayList<>();
            TRTCVideoCallActivity.UserInfo callUserInfo = new TRTCVideoCallActivity.UserInfo();
            callUserInfo.userId = mSearchModel.userId;
            callUserInfo.userAvatar = mSearchModel.userAvatar;
            callUserInfo.userName = mSearchModel.userName;
            callUserInfoList.add(callUserInfo);
            ToastUtils.showShort("视频呼叫:" + callUserInfo.userName);
            TRTCVideoCallActivity.startCallSomeone(TRTCCallingEntranceActivity.this, selfInfo, callUserInfoList);
        } else {
            TRTCAudioCallActivity.UserInfo selfInfo = new TRTCAudioCallActivity.UserInfo();
            selfInfo.userId = ProfileManager.getInstance().getUserModel().userId;
            selfInfo.userAvatar = ProfileManager.getInstance().getUserModel().userAvatar;
            selfInfo.userName = ProfileManager.getInstance().getUserModel().userName;
            List<TRTCAudioCallActivity.UserInfo> callUserInfoList = new ArrayList<>();
            TRTCAudioCallActivity.UserInfo callUserInfo = new TRTCAudioCallActivity.UserInfo();
            callUserInfo.userId = mSearchModel.userId;
            callUserInfo.userAvatar = mSearchModel.userAvatar;
            callUserInfo.userName = mSearchModel.userName;
            callUserInfoList.add(callUserInfo);
            ToastUtils.showShort("语音呼叫:" + callUserInfo.userName);
            TRTCAudioCallActivity.startCallSomeone(TRTCCallingEntranceActivity.this, selfInfo, callUserInfoList);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trtccalling_activity_search_user_entrance);

        ProfileManager.getInstance().login("111", "", new ProfileManager.ActionCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailed(int code, String msg) {
            }
        });

        mSelfModel = ProfileManager.getInstance().getUserModel();
        mType = getIntent().getIntExtra("TYPE", TRTCCalling.TYPE_VIDEO_CALL);
        initView();
        initStatusBar();
        initPermission();

        initData();


    }

    private void initData() {
        mSelfModel = ProfileManager.getInstance().getUserModel();
        mTvSelfPhone.setText(getString(R.string.trtccalling_call_self_format, mSelfModel != null ? mSelfModel.phone : ""));

        //登录成功
        mTRTCCalling = TRTCCallingImpl.sharedInstance(TRTCCallingEntranceActivity.this);
        mTRTCCalling.addDelegate(mTRTCCallingDelegate);
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

            }
        });
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTvTitle = (TextView) findViewById(R.id.toolbar_title);
        mEtSearchUser = (EditText) findViewById(R.id.et_search_user);
        mIvClearSearch = (ImageView) findViewById(R.id.iv_clear_search);
        mTvSearch = (TextView) findViewById(R.id.tv_search);
        mTvSelfPhone = (TextView) findViewById(R.id.tv_self_phone);
        mLlContract = (LinearLayout) findViewById(R.id.ll_contract);
        mIvAvatar = (ImageView) findViewById(R.id.img_avatar);
        mTvUserName = (TextView) findViewById(R.id.tv_user_name);
        mBtnStartCall = (Button) findViewById(R.id.btn_start_call);
        mClTips = (ConstraintLayout) findViewById(R.id.cl_tips);

        // 导航栏回退/设置
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //导航栏名称设置
        mBtnStartCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startCallSomeone();
            }
        });

        mEtSearchUser.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchContactsByPhone(v.getText().toString());
                    return true;
                }
                return false;
            }
        });

        mEtSearchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.length() == 0) {
                    mIvClearSearch.setVisibility(View.GONE);
                } else {
                    mIvClearSearch.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchContactsByPhone(mEtSearchUser.getText().toString());
            }
        });

        mIvClearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtSearchUser.setText("");
            }
        });
        mTvSelfPhone.setText(getString(R.string.trtccalling_call_self_format, mSelfModel != null ? mSelfModel.phone : ""));

        if (mType == TRTCCalling.TYPE_VIDEO_CALL) {
            mTvTitle.setText(getString(R.string.trtccalling_video_call));
        } else {
            mTvTitle.setText(getString(R.string.trtccalling_audio_call));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTRTCCalling != null) {
            mTRTCCalling.removeDelegate(mTRTCCallingDelegate);
        }
    }

    private void showSearchUserModel(UserModel model) {
        if (model == null) {
            mLlContract.setVisibility(View.GONE);
            mClTips.setVisibility(View.VISIBLE);
            return;
        }
        mClTips.setVisibility(View.GONE);
        mLlContract.setVisibility(View.VISIBLE);
        Picasso.get().load(model.userAvatar).into(mIvAvatar);
        mTvUserName.setText(model.userName);
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.MICROPHONE, PermissionConstants.CAMERA)
                    .request();
        }
    }

    private void searchContactsByPhone(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return;
        }

        ProfileManager.getInstance().getUserInfoByPhone(phoneNumber, new ProfileManager.GetUserInfoCallback() {
            @Override
            public void onSuccess(UserModel model) {
                mSearchModel = model;
                showSearchUserModel(model);
            }

            @Override
            public void onFailed(int code, String msg) {
                showSearchUserModel(null);
                ToastUtils.showLong(getString(R.string.trtccalling_toast_search_fail, msg));
            }
        });
    }

}
