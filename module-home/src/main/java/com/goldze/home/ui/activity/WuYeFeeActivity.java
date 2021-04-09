package com.goldze.home.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.goldze.base.router.RouterActivityPath;
import com.goldze.base.widget.TitleView;
import com.goldze.home.BR;
import com.goldze.home.R;
import com.goldze.home.databinding.ActivityShequBinding;
import com.goldze.home.databinding.ActivityWuyeFeeBinding;
import com.goldze.home.ui.viewmodel.SheQuViewModel;
import com.goldze.home.ui.viewmodel.WuYeFeeViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.shaohui.bottomdialog.BottomDialog;

@Route(path = RouterActivityPath.Home.PAGER_WUYE_PAY)
public class WuYeFeeActivity extends BaseActivity<ActivityWuyeFeeBinding, WuYeFeeViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_wuye_fee;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        TitleView titleView = findViewById(R.id.title);
        titleView.setTitleText("物业费账单");

        Button btnGoToPay = findViewById(R.id.btn_goToPay);
        btnGoToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomDialog.create(getSupportFragmentManager())
                        .setViewListener(new BottomDialog.ViewListener() {
                            @Override
                            public void bindView(View v) {
                                Button btnPay = v.findViewById(R.id.btn_pay);
                                btnPay.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        System.out.println("hhhh");
                                    }
                                });
                            }
                        })
                        .setLayoutRes(R.layout.pay_dialog_layout)
                        .setDimAmount(0.1f)            // Dialog window dim amount(can change window background color）, range：0 to 1，default is : 0.2f
                        .setCancelOutside(true)     // click the external area whether is closed, default is : true
                        .setTag("BottomDialog")     // setting the DialogFragment tag
                        .show();
            }
        });
    }
}
