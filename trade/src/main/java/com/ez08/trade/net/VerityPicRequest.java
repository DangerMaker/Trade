package com.ez08.trade.net;

public class VerityPicRequest extends YCRequest {

    public VerityPicRequest() {
        super(100);
    }

    @Override
    public String parse(byte[] buffer) {
       return NativeTools.parseVerifyCodeBodyAFromJNI(buffer);
    }
}
