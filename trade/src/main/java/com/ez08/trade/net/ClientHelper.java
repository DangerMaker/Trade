package com.ez08.trade.net;

import com.ez08.trade.Constant;

public class ClientHelper {

    private static YCBizClient bizClient = null;

    public static void init() {
        if (bizClient == null) {
            bizClient = new YCBizClient(Constant.SERVER_IP, Constant.BIZ_SERVER_PORT);
        }
    }

    public static YCBizClient get(){
        return bizClient;
    }

}
