//package com.ez08.trade.net;
//
//import android.util.Log;
//
//public class YCClient {
//
//    //        static int port = 60010;
////    static String host = "127.0.0.1";
//    static int port = 35502;
//    static String host = "220.181.152.112";
//
//    public static final void main(String args[]) {
//        final YCSocketClient socketClient = new YCSocketClient(host, port);
//        socketClient.setOnConnectListener(new ConnectListener() {
//            @Override
//            public void connectSuccess(Client client) {
//                success(client);
//            }
//
//            @Override
//            public void connectFail(YCSocketClient client) {
//
//            }
//
//            @Override
//            public void connectLost(YCSocketClient client) {
//
//            }
//        });
//        socketClient.connect();
//    }
//
//    public static void success(YCSocketClient client){
//        YCRequest request = new YCRequest(100);
//        byte[] test = NativeTools.getVerifyCodePackFromJNI(20,10);
//        request.mData = test;
//        request.setCallback(new ResponseCallback() {
//            @Override
//            public void callback(YCSocketClient client, Response data) {
//                if(data.isSucceed()){
//                    Log.e("YCRequest",data.getData());
//                }
//            }
//        });
//
//        client.send(request);
//    }
//}
