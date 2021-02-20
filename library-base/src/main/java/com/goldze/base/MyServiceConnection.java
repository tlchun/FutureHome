package com.goldze.base;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class MyServiceConnection implements ServiceConnection {
    private MQTTService mqttService;
    private IGetMessageCallBack IGetMessageCallBack;
    private IMqttStateCallback IMqttStateCallback;

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mqttService = ((MQTTService.CustomBinder) iBinder).getService();
        mqttService.setIGetMessageCallBack(IGetMessageCallBack);
        mqttService.setIMqttStateCallback(IMqttStateCallback);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    public MQTTService getMqttService() {
        return mqttService;
    }

    public void setIGetMessageCallBack(IGetMessageCallBack IGetMessageCallBack) {
        this.IGetMessageCallBack = IGetMessageCallBack;
    }

    public void setIMqttStateCallback(IMqttStateCallback IMqttStateCallback) {
        this.IMqttStateCallback = IMqttStateCallback;
    }
}
