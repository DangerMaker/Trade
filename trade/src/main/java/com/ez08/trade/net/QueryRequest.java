package com.ez08.trade.net;

import android.util.Log;

import com.ez08.trade.Constant;
import com.ez08.trade.tools.YiChuangUtils;

import java.io.UnsupportedEncodingException;

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
