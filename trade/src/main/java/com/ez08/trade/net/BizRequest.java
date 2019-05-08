package com.ez08.trade.net;

import com.ez08.trade.Constant;

import java.io.UnsupportedEncodingException;

public class BizRequest extends YCRequest {
    public BizRequest() {
        super(SnFactory.getSnClient());
    }

    @Override
    public String parse(byte[] head, byte[] body) {
        return NativeTools.parseTradeGateBizFunFromJNI(head, body);
    }

    public void setBody(String body) {
        try {
            byte[] a = body.getBytes(Constant.SERVER_CHARSET);
            this.mData = NativeTools.genTradeGateBizFunFromJNI(a, sn);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String bytesToHexString(byte[] src){

        StringBuilder stringBuilder = new StringBuilder();

        if (src == null || src.length <= 0) {

            return null;

        }

        for (int i = 0; i < src.length; i++) {

            int v = src[i] & 0xFF;

            String hv = Integer.toHexString(v);

            if (hv.length() < 2) {

                stringBuilder.append(0);

            }

            stringBuilder.append(hv);

        }

        return stringBuilder.toString();

    }

}
