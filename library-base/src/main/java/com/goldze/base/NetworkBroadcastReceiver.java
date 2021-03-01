package com.goldze.base;

import android.app.usage.NetworkStats;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import me.goldze.mvvmhabit.bus.RxBus;

public class NetworkBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo.State state = NetworkInfo.State.DISCONNECTED;
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            ConnectivityManager contectivityMananger = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = contectivityMananger.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
                state = mNetworkInfo.getState();
            }
        }
        if (state == NetworkInfo.State.CONNECTED) {
            _NetworkStatus status = new _NetworkStatus();
            status.setType(0);
            RxBus.getDefault().post(status);
        } else if (state == NetworkInfo.State.DISCONNECTED) {
            _NetworkStatus status = new _NetworkStatus();
            status.setType(1);
            RxBus.getDefault().post(status);
        }
    }
}
