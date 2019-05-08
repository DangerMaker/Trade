package com.ez08.trade.net;


public class NativeTools {

    //验证码
    public static final int VERIFICATION_CODE_PID__REQ_PIC = 100; //获取验证码图片
    public static final int VERIFICATION_CODE_PID_CHECK_CODE = 200; //验证验证码
    //握手
    public static final int PID_TRADE_COMM_BASE = 100; //shark
    public static final int PID_TRADE_KEY_EXCHANGE = PID_TRADE_COMM_BASE + 1; //
    public static final int PID_TRADE_COMM_OK = PID_TRADE_COMM_BASE + 10;
    //股票
    public static final int PID_TRADE_HQ_BASE = 500; //query hq
    public static final int PID_TRADE_CN_LIST = PID_TRADE_HQ_BASE + 1; //查询代码表
    public static final int PID_TRADE_HQ_QUERY = PID_TRADE_HQ_BASE + 2; //查询5档Level1行情，最多同时查询2个股票
    //业务
    public static final int PID_TRADE_GATE_BASE = 2000;
    static {
        System.loadLibrary("native-lib");
    }

    public static native byte[] getVerifyCodePackFromJNI(int width,int height);
    public static native String parseVerifyCodeHeadFromJNI(byte[] buffer);
    public static native String parseVerifyCodeBodyAFromJNI(byte[] buffer);

    public static native String parseTradeHeadFromJNI(byte[] buffer);
    public static native String parseTradeGateErrorFromJNI(byte[] head,byte[] body);
    public static native byte[] genTradeHeartBeatFromJNI();

    public static native byte[] genTradePacketKeyExchangePackFromJNI(int reqId);
    public static native String parseTradePacketKeyExchangeFromJNI(byte[] buffer);

    public static native byte[] genTradeGateLoginPackFromJNI(String userType,String userId,String password,String checkCode,String verifiCodeId,int reqId);
    public static native String parseTradeGateLoginFromJNI(byte[] head,byte[] body);

    public static native byte[] genTradeGateBizFunFromJNI(byte[] content,int reqId);
    public static native String parseTradeGateBizFunFromJNI(byte[] head,byte[] body);
}
