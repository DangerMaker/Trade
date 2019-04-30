package com.ez08.trade.net;

public class Response {
    private boolean isSucceed = false;
    private String data;

    public boolean isSucceed() {
        return isSucceed;
    }

    public void setSucceed(boolean succeed) {
        isSucceed = succeed;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
