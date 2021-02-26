package com.goldze.work.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.utils.TextUtils;
import com.goldze.base.global.SPKeyGlobal;
import com.goldze.work.R;
import com.google.gson.Gson;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.ApiResult;

import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class CardActivity extends AppCompatActivity {

    private EditText et_userid;
    private EditText et_card;

    private String deviceMac;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_input);

        et_userid = findViewById(R.id.et_userid);
        et_card = findViewById(R.id.et_card);

        deviceMac = getIntent().getStringExtra("deviceMac");

        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeCard();
            }
        });
    }

    private void activeCard() {
        String userId = et_userid.getText().toString().trim();
        String cardNo = et_card.getText().toString().trim();
        if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(cardNo)) {
            EasyHttp.get("/app/device/acs/activeCard")
                    .headers("token", SPUtils.getInstance().getString(SPKeyGlobal.USER_TOKEN))
                    .params("userId", userId + "")
                    .params("cardNo", cardNo + "")
                    .params("deviceMac", deviceMac)
                    .timeStamp(true)
                    .execute(new SimpleCallBack<String>() {
                        @Override
                        public void onError(ApiException e) {
                            ToastUtils.showShort(e.getMessage() != null ? e.getMessage() : "保存失败");
                        }

                        @Override
                        public void onSuccess(String response) {
                            Gson gson = new Gson();
                            ApiResult apiResult = gson.fromJson(response, ApiResult.class);
                            if (apiResult.getCode() == 0) {
                                ToastUtils.showShort("保存成功");
                                finish();
                            } else {
                                ToastUtils.showShort(apiResult.getMsg());
                            }
                        }
                    });
        }
    }
}
