package com.ez08.trade.net;

public class YCRequest {

    ResponseCallback callback;
    public int mSn;
    public long mSendTime;    //发数据的时间
    public int mTimeout;	//超时时间

    public YCRequest() {
    }

    public YCRequest(ResponseCallback callback) {
        this.callback = callback;
    }


    public void setCallback(ResponseCallback callback) {
        this.callback = callback;
    }


    public void failed(YCSocketClient client) {
        if (callback == null)
            return;

        Response response = new Response();
        response.setSucceed(false);
        callback.callback(client, response);
    }

    public void cancel(YCSocketClient client){
//        mResult = RESULT_CANCELED;
//        failed(client);
        System.out.println("Sn=" + mSn + ", status = cancel");
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
