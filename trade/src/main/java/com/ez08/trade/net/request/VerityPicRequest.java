package com.ez08.trade.net.request;

import com.ez08.trade.net.NativeTools;
import com.ez08.trade.net.SnFactory;
import com.ez08.trade.net.YCRequest;

public class VerityPicRequest extends YCRequest {

    //验证码只传pid
    public VerityPicRequest() {
        //验证码需要传
        super(100);
    }

    public void setBody(int width,int height){
        this.mData = NativeTools.getVerifyCodePackFromJNI(width, height);
    }

    @Override
    public String parse(byte[] head,byte[] body) {
       return NativeTools.parseVerifyCodeBodyAFromJNI(body);
    }
}
