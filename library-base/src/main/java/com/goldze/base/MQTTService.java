package com.goldze.base;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.goldze.base.global.SPKeyGlobal;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import me.goldze.mvvmhabit.utils.SPUtils;

public class MQTTService extends Service {
    public static final String TAG = MQTTService.class.getSimpleName();

    private static MqttAndroidClient client;
    private MqttConnectOptions conOpt;

    private String host = "tcp://8.134.9.85:1883";
    private static String clientId = SPUtils.getInstance().getString(SPKeyGlobal.USER_MQTT_USERNAME, "device_1");
    private static String userName = SPUtils.getInstance().getString(SPKeyGlobal.USER_MQTT_USERNAME, "device_1");
    private static String passWord = SPUtils.getInstance().getString(SPKeyGlobal.USER_MQTT_PWD, "public");
    private static String myTopic = "/device/up/" + clientId;//要订阅的主题
    private IGetMessageCallBack IGetMessageCallBack;

    private IMqttStateCallback iMqttStateCallback;

    //重连次数
    private final static int RECONNECT_TIME = 3;
    private int retryReconnectTime = RECONNECT_TIME;
    //重连时间
    private static final Integer MILLIS_IN_ONE_SECOND = 1000 * 5;
    //是否连接
    public volatile boolean isConnectFlag = false;

    public static void setUserName(String name) {
        userName = name;
    }

    public static void setPassWord(String pwd) {
        passWord = pwd;
    }

    public static void setId(String id) {
        clientId = id;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "mqtt onCreate");
        init();
    }

    public static void publish(String msg) {
        String topic = myTopic;
        Integer qos = 0;
        Boolean retained = false;
        try {
            if (client != null) {
                client.publish(topic, msg.getBytes(), qos.intValue(), retained.booleanValue());
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        Log.e(TAG, "mqtt init");
        isConnectFlag = false;
        retryReconnectTime = RECONNECT_TIME;
        // 服务器地址（协议+地址+端口号）
        String uri = host;
        if (client == null) {
            client = new MqttAndroidClient(this, uri, clientId);
            // 设置MQTT监听并且接受消息
            client.setCallback(mqttCallback);

            conOpt = new MqttConnectOptions();
            // 清除缓存
            conOpt.setCleanSession(true);
            // 设置超时时间，单位：秒
            conOpt.setConnectionTimeout(10);
            // 心跳包发送间隔，单位：秒
            conOpt.setKeepAliveInterval(20);
            // 用户名
            conOpt.setUserName(userName);
            // 密码
            conOpt.setPassword(passWord.toCharArray());     //将字符串转换为字符串数组

            doClientConnection();
        } else {
            //若mqtt服务不为空，并且未连接，则重新连接
            boolean isConn = client.isConnected();
            if (!isConn) {
                doClientConnection();
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "mqtt onDestroy");
        if (isConnectFlag) {
            isConnectFlag = false;
        }
        stopSelf();
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    /**
     * 连接MQTT服务器
     */
    private void doClientConnection() {
        if (!client.isConnected() && isConnectIsNormal()) {
            try {
                Log.e(TAG, "start connect mqtt");
                client.connect(conOpt, null, iMqttActionListener);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    // MQTT是否连接成功
    private IMqttActionListener iMqttActionListener = new IMqttActionListener() {
        @Override
        public void onSuccess(IMqttToken arg0) {
            Log.e(TAG, "mqtt connect success ");
            retryReconnectTime = RECONNECT_TIME;
            if (client != null && client.isConnected()) {
                if (!isConnectFlag) {
                    isConnectFlag = true;
                }
                try {
                    // 订阅myTopic话题
                    client.subscribe(myTopic, 1);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(IMqttToken arg0, Throwable arg1) {
            arg1.printStackTrace();
            if (isConnectFlag) {
                isConnectFlag = false;
            }
            Log.d(TAG, "mqtt 连接失败");
            // 连接失败，重连
            scheduleReconnect();
            if (retryReconnectTime == 0) {
                if (iMqttStateCallback != null) {
                    iMqttStateCallback.onError(MqttConstant.MQTT_ERROR_CONNECT_FAILED, "mqtt connect failed");
                }
            }
        }
    };

    // MQTT监听并且接受消息
    private MqttCallback mqttCallback = new MqttCallback() {
        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            String str1 = new String(message.getPayload());
            if (IGetMessageCallBack != null) {
                IGetMessageCallBack.setMessage(str1);
            }
            String str2 = topic + ";qos:" + message.getQos() + ";retained:" + message.isRetained();
            Log.i(TAG, "messageArrived:" + str1 + "，" + str2);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken arg0) {
        }

        @Override
        public void connectionLost(Throwable arg0) {
            // 失去连接，重连
            scheduleReconnect();
            if (retryReconnectTime == 0) {
                if (iMqttStateCallback != null) {
                    iMqttStateCallback.onError(MqttConstant.MQTT_ERROR_MQTT_MSG, "mqtt connect failed");
                }
            }
        }
    };

    /**
     * 是否已连上mqtt服务器
     *
     * @return
     */
    public static boolean isConnectFlag() {
        if (client != null && client.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    void scheduleReconnect() {
        if (retryReconnectTime > 0) {
            (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "mqtt 重连，retryReconnectTime:" + retryReconnectTime);
                    retryReconnectTime--;
                    doClientConnection();
                }
            }, MILLIS_IN_ONE_SECOND * (RECONNECT_TIME - retryReconnectTime + 1));
        } else {
            Log.d(TAG, "mqtt 重连失败，连接失败");
        }
    }

    /**
     * 判断网络是否连接
     */
    private boolean isConnectIsNormal() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String name = info.getTypeName();
            Log.e(TAG, "MQTT当前网络名称：" + name);
            return true;
        } else {
            Log.i(TAG, "MQTT 没有可用网络");
            return false;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new CustomBinder();
    }

    public void setIGetMessageCallBack(IGetMessageCallBack IGetMessageCallBack) {
        this.IGetMessageCallBack = IGetMessageCallBack;
    }

    public void setIMqttStateCallback(IMqttStateCallback iMqttStateCallback) {
        this.iMqttStateCallback = iMqttStateCallback;
    }

    public class CustomBinder extends Binder {
        public MQTTService getService() {
            return MQTTService.this;
        }
    }
}
