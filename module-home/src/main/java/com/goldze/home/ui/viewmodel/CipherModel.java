package com.goldze.home.ui.viewmodel;

public class CipherModel {
    private long cipherId;
    private String cipherValue;
    private String userId;
    private long deviceId;
    private int status;
    private long startTime;
    private long endTime;

    public long getCipherId() {
        return cipherId;
    }

    public void setCipherId(long cipherId) {
        this.cipherId = cipherId;
    }

    public String getCipherValue() {
        return cipherValue;
    }

    public void setCipherValue(String cipherValue) {
        this.cipherValue = cipherValue;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
