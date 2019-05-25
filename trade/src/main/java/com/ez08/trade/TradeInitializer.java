package com.ez08.trade;


import android.content.Context;

public class TradeInitializer {
    private static TradeInitializer sInstance;
    private static Context appContext;

    static {
        System.loadLibrary("native-lib");
    }

    public static TradeInitializer getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TradeInitializer(context);
        }

        return sInstance;
    }

    public TradeInitializer(Context context) {
        appContext = context;
    }
}
