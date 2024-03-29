package com.ez08.trade.ui.fresh_stock;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.request.BizRequest;
import com.ez08.trade.net.Client;
import com.ez08.trade.net.ClientHelper;
import com.ez08.trade.net.Response;
import com.ez08.trade.net.Callback;
import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.TradeHisQueryActivity;
import com.ez08.trade.ui.fresh_stock.adpater.TradePhAdapter;
import com.ez08.trade.ui.fresh_stock.entity.TradeZqEntity;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Set;

public class TradeZhongqianActivity extends TradeHisQueryActivity {
    @Override
    public String getTitleString() {
        return "历史中签";
    }

    @Override
    public int getListTitleLayout() {
        return R.layout.trade_holder_zq_title;
    }

    @Override
    public BaseAdapter getAdapter() {
        return new TradePhAdapter(this);
    }

    @Override
    public void post(String beginValue, String endValue) {
        mList.clear();

        String body = "FUN=411560&TBL_IN=secuid,market,stkcode,issuetype,begindate,enddate,count,poststr;" +
                "" + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                beginValue + "," +
                endValue + "," +
                "100" + "," +
                ";";

        BizRequest request = new BizRequest();
        request.setBody(body);
        request.setCallback(new Callback() {
            @Override
            public void callback(Client client, Response data) {
                if (data.isSucceed()) {
                    Log.e(TAG, data.getData());
                    try {
                        JSONObject jsonObject = new JSONObject(data.getData());
                        String content = jsonObject.getString("content");
                        Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + content);
                        Set<String> pn = uri.getQueryParameterNames();
                        for (Iterator it = pn.iterator(); it.hasNext(); ) {
                            String key = it.next().toString();
                            if ("TBL_OUT".equals(key)) {
                                String out = uri.getQueryParameter(key);
                                String[] split = out.split(";");
                                for (int i = 1; i < split.length; i++) {
                                    String[] var = split[i].split(",");
                                    TradeZqEntity entity = new TradeZqEntity();
                                    entity.stkname = var[4];
                                    entity.matchdate = var[6];
                                    entity.hitqty = var[8];
                                    entity.status = var[16];
                                    mList.add(entity);
                                }
                                adapter.clearAndAddAll(mList);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        ClientHelper.get().send(request);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
