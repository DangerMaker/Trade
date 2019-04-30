package com.ez08.trade.net;

public class YCRequest {

    ResponseCallback callback;
    public int pid;
    public long mSendTime;    //发数据的时间
    public int mTimeout;	//超时时间
    public byte[] mData;

    public YCRequest(int pid) {
        this.pid = pid;
    }

    public YCRequest(ResponseCallback callback) {
        this.callback = callback;
    }

    public void setCallback(ResponseCallback callback) {
        this.callback = callback;
    }


    public void failed(Client client) {
        if (callback == null)
            return;

        Response response = new Response();
        response.setSucceed(false);
        callback.callback(client, response);
    }

    public void received(Response response,Client client){
        if(callback == null)
            return;

        response.setSucceed(true);
        callback.callback(client, response);
    }

    public void cancel(Client client){
//        mResult = RESULT_CANCELED;
//        failed(client);
        System.out.println("Sn=" + pid + ", status = cancel");
    }


    public String parse(byte[] buffer){
        return "";
    }

    public NetPackage getNetPackage() {
        NetPackage netPackage = new NetPackage();
        return netPackage;
//        NetPakage pakage = new NetPakage(NetPakage.NETPAKAGE_TYPE_DATA);
//        pakage.sn = mSn;
//        pakage.action = mAction;
//        pakage.content = mData;
//        pakage.contentType = mDataType;
//        return pakage;
    }

    public static final int REQUEST_STATE_READY = 0;
    public static final int REQUEST_STATE_SEND = 1;
    public static final int REQUEST_STATE_SEVER_RECEIVED = 2;
    public static final int REQUEST_STATE_RESPONSE_RECEIVED = 3;
    public int mState;    //
}
