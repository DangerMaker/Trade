package com.ez08.trade.net;

public class LoginRequest extends YCRequest {

    public LoginRequest(String userType, String userId, String password, String checkCode, String verifiCodeId) {
        super(SnFactory.getSnClient());
        this.mData = NativeTools.genTradeGateLoginPackFromJNI(userType,userId,password,checkCode,verifiCodeId,this.sn);
    }

    @Override
    public String parse(byte[] head, byte[] body) {
        return NativeTools.parseTradeGateLoginFromJNI(head, body);
    }
}
