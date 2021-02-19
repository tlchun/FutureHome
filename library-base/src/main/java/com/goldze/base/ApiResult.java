package com.goldze.base;

public class ApiResult<T> {
    private int code;
    private String msg;
    private T data;

    public ApiResult() {
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk() {
        return this.code == 0;
    }

    public String toString() {
        return "ApiResult{code='" + this.code + '\'' + ", msg='" + this.msg + '\'' + ", data=" + this.data + '}';
    }
}