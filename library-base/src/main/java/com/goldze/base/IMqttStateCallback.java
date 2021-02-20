package com.goldze.base;

public interface IMqttStateCallback {
    /**
     * mqtt异常
     * @param code 异常code
     * @param msg  异常msg
     */
    void onError(int code,String msg);
}
