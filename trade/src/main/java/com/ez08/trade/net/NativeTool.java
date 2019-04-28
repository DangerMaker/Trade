package com.ez08.trade.net;

public class NativeTool {

    static {
        System.loadLibrary("native-lib");
    }

    public static native String stringFromJNI();

    public static native byte[] getVerifyCodePackFromJNI(int width,int height);
}
