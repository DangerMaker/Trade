package com.ez08.trade.net.request;

import com.ez08.trade.net.SnFactory;
import com.ez08.trade.net.YCRequest;
import com.ez08.trade.net.NativeTools;

public class VerityPicRequest extends YCRequest {

    public VerityPicRequest() {
        super(SnFactory.getSnClient());
    }

    public void setBody(int width,int height){
        this.mData = NativeTools.getVerifyCodePackFromJNI(width, height);
    }

    @Override
    public String parse(byte[] head,byte[] body) {
       return NativeTools.parseVerifyCodeBodyAFromJNI(body);
    }
}
