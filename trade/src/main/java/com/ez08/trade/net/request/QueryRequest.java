package com.ez08.trade.net.request;

import android.util.Log;

import com.ez08.trade.net.NativeTools;
import com.ez08.trade.net.SnFactory;
import com.ez08.trade.net.YCRequest;
import com.ez08.trade.tools.YiChuangUtils;

public class QueryRequest extends YCRequest {
    public QueryRequest() {
        super(SnFactory.getSnClient());
    }

    @Override
    public String parse(byte[] head, byte[] body) {
        return NativeTools.parseTradeHQQueryFromJNI(head, body);
    }

    public void setBody(String market, String secucode) {
        Log.e("QueryRequest", YiChuangUtils.getMarketByTag(market) + "," + secucode);
        this.mData = NativeTools.genTradeHQQueryFromJNI(YiChuangUtils.getMarketByTag(market), secucode, sn);
    }
}
