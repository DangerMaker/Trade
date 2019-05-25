package com.ez08.trade.net;

import com.ez08.trade.Constant;
import com.ez08.trade.net.client.YCBizClient;

public class ClientHelper {

    private static YCBizClient bizClient = null;

    public static YCBizClient get(){
        if (bizClient == null) {
            bizClient = new YCBizClient(Constant.SERVER_IP, Constant.BIZ_SERVER_PORT);
        }
        return bizClient;
    }

}
