package com.ez08.trade;


import android.content.Context;
import android.os.AsyncTask;

import com.ez08.trade.net.ClientHelper;
import com.ez08.trade.tools.SharedPreferencesHelper;
import com.ez08.trade.ui.view.refresh.SmartRefreshLayout;
import com.ez08.trade.ui.view.refresh.api.DefaultRefreshFooterCreator;
import com.ez08.trade.ui.view.refresh.api.DefaultRefreshHeaderCreator;
import com.ez08.trade.ui.view.refresh.api.RefreshFooter;
import com.ez08.trade.ui.view.refresh.api.RefreshHeader;
import com.ez08.trade.ui.view.refresh.api.RefreshLayout;
import com.ez08.trade.ui.view.refresh.footer.ClassicsFooter;
import com.ez08.trade.ui.view.refresh.header.ClassicsHeader;

public class TradeInitializer {
    private static TradeInitializer sInstance;
    private static Context appContext;

    static {
        System.loadLibrary("native-lib");

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.trade_white, R.color.trade_pull_text);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    public static TradeInitializer getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TradeInitializer(context);
        }

        return sInstance;
    }

    public TradeInitializer(Context context) {
        appContext = context;
        init();
    }

    private void init(){
        ClientHelper.init();
    }


}
