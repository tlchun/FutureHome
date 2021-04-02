package com.goldze.home.ui.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import com.goldze.home.ui.activity.SheQuActivity;
import com.goldze.home.ui.activity.WuYeActivity;
import com.goldze.home.ui.activity.WuYePayActivity;

import me.goldze.mvvmhabit.base.BaseViewModel;

public class HomeViewModel extends BaseViewModel {

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public View.OnClickListener sheQuOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(SheQuActivity.class);
        }
    };

    public View.OnClickListener wuYeOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(WuYeActivity.class);
        }
    };

    public View.OnClickListener wuYePayOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(WuYePayActivity.class);
        }
    };

}
