package com.compass.trade;

import android.app.Application;

import com.ez08.trade.TradeInitializer;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TradeApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        TradeInitializer.getInstance(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://www.baidu.com").build();
                try {
                    Response response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
