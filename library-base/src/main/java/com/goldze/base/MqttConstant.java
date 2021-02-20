package com.goldze.base;

public interface MqttConstant {

    int MQTT_ERROR_CONNECT_FAILED= 10001;  //mqtt无法连接
    int MQTT_ERROR_GET_CONFIG= 10002;  //mqtt获取配置失败
    int MQTT_ERROR_RECEIVE_MSG= 10003;  //mqtt接收消息失败
    int MQTT_ERROR_MQTT_MSG= 10004;  //mqtt接收消息失败

}
