package com.goldze.work.ui;

public class CardRecordModel {
    private int cardRelationId;
    private long cardNo;
    private String userId;
    private long deviceId;
    private long createTime;
    private long updateTime;

    public int getCardRelationId() {
        return cardRelationId;
    }

    public void setCardRelationId(int cardRelationId) {
        this.cardRelationId = cardRelationId;
    }

    public long getCardNo() {
        return cardNo;
    }

    public void setCardNo(long cardNo) {
        this.cardNo = cardNo;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
