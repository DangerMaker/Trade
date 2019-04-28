package com.ez08.trade.net;

public class YCClient {

        static int port = 60010;
    static String host = "127.0.0.1";
//    static int port = 35502;
//    static String host = "220.181.152.112";

    public static final void main(String args[]){
        final YCSocketClient socketClient = new YCSocketClient(host,port);
        socketClient.connect();
    }
}
