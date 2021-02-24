package com.goldze.sign.model;

public class LoginModel {
    private AccountModel account;
    private MqttModel mqtt;
    private String token;

    public AccountModel getAccount() {
        return account;
    }

    public void setAccount(AccountModel account) {
        this.account = account;
    }

    public MqttModel getMqtt() {
        return mqtt;
    }

    public void setMqtt(MqttModel mqtt) {
        this.mqtt = mqtt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
