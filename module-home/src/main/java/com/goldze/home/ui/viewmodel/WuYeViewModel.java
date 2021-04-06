package com.goldze.home.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import com.goldze.home.ui.activity.SheQuActivity;
import com.goldze.home.ui.activity.WuYeActivity;
import com.goldze.home.ui.activity.WuYeFeeActivity;
import com.goldze.home.ui.activity.WuYePayActivity;

import me.goldze.mvvmhabit.base.BaseViewModel;

public class WuYeViewModel extends BaseViewModel {

    public WuYeViewModel(@NonNull Application application) {
        super(application);
    }

    public View.OnClickListener wuYeFeeOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(WuYeFeeActivity.class);
        }
    };
}
