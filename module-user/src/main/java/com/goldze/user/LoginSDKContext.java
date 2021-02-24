package com.goldze.user;

import android.content.Context;

public class LoginSDKContext {

    private Context mContext;

    private static LoginSDKContext mInstance;

    private LoginSDKContext() {
    }


    public static LoginSDKContext getInstance() {
        if (mInstance == null) {
            synchronized ( LoginSDKContext.class) {
                if (mInstance == null) {
                    mInstance = new LoginSDKContext();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }
}
