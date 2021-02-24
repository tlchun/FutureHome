package com.goldze.user.ui.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;
import android.view.View;

import com.goldze.user.ui.activity.DisturbActivity;
import com.goldze.user.ui.activity.FaceActivity;
import com.goldze.user.ui.activity.FeedBackActivity;
import com.goldze.user.ui.activity.PersonActivity;
import com.goldze.user.ui.activity.SettingActivity;
import com.goldze.user.ui.activity.ShareActivity;

import me.goldze.mvvmhabit.base.BaseViewModel;

public class SettinglViewModel extends BaseViewModel {


    public SettinglViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void getAppVersion() {

    }

    private void getCacheSize() {

    }

    public View.OnClickListener faceOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(FaceActivity.class);
        }
    };
    public View.OnClickListener personOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(PersonActivity.class);
        }
    };
    public View.OnClickListener shareOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(ShareActivity.class);
        }
    };
    public View.OnClickListener disturbOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(DisturbActivity.class);
        }
    };
    public View.OnClickListener feedbackOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(FeedBackActivity.class);
        }
    };
    public View.OnClickListener settingOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(SettingActivity.class);
        }
    };

}
