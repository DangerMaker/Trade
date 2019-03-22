package com.compass.trade;

import android.app.Application;

import com.ez08.trade.TradeInitializer;

public class TradeApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        TradeInitializer.getInstance(this);
    }
}
