package com.ez08.trade.ui.trade;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.BizRequest;
import com.ez08.trade.net.Client;
import com.ez08.trade.net.ClientHelper;
import com.ez08.trade.net.Response;
import com.ez08.trade.net.ResponseCallback;
import com.ez08.trade.ui.BaseFragment;
import com.ez08.trade.ui.Interval;
import com.ez08.trade.ui.trade.adpater.TradeEntrustAdapter;
import com.ez08.trade.ui.trade.adpater.TradeHandAdapter;
import com.ez08.trade.ui.trade.adpater.TradeMainAdapter;
import com.ez08.trade.ui.trade.entity.TradeEntrustEntity;
import com.ez08.trade.ui.trade.entity.TradeFundsEntity;
import com.ez08.trade.ui.trade.entity.TradeHandEntity;
import com.ez08.trade.ui.trade.entity.TradeTitleEntrustEntity;
import com.ez08.trade.ui.trade.entity.TradeTitleHandEntity;
import com.ez08.trade.ui.view.FundsGridItemDecoration;
import com.ez08.trade.ui.view.LinearItemDecoration;
import com.ez08.trade.user.UserHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TradeHandFragment extends BaseFragment implements Interval {

    RecyclerView recyclerView;
    GridLayoutManager manager;
    List<Object> mList;

    FundsGridItemDecoration itemDecoration;
    TradeHandAdapter adapter;

    public static TradeHandFragment newInstance() {
        Bundle args = new Bundle();
        TradeHandFragment fragment = new TradeHandFragment();
        fragment.setArguments(args);
        return fragment;
    }

    float yu_e;
    float ke_yong;
    float ke_qu;

    @Override
    protected int getLayoutResource() {
        return R.layout.trade_fragment_option;
    }

    @Override
    protected void onCreateView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        manager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(manager);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                return adapter.getSpan(i);
            }
        });

        adapter = new TradeHandAdapter(mContext);
        recyclerView.setAdapter(adapter);

        itemDecoration = new FundsGridItemDecoration(mContext);
        recyclerView.addItemDecoration(itemDecoration);
//        orgid,custid,fundid,moneytype,fundbal,fundavl,marketvalue,fund,fundseq,stkvalue,fundbuy,fundsale,fundfrz,fundlastbal,fundloan,fundassetadjamt,stkassetadjamt

//        1009,109000512,109000512,0,15261051.82,1907761.49,68809465995.52,15261051.82,0,68794204943.70,13084078.12,0.00,269212.11,15261051.82,0.00,0.00,0.00
//        1009,109000512,109000512,1,805189.90,805189.90,809770.90,805189.90,0,4581.00,0.00,0.00,0.00,805189.90,0.00,0.00,0.00
//        1009,109000512,109000512,2,10000000182.37,10000000182.37,10000007333.81,10000000182.37,0,7151.44,0.00,0.00,0.00,10000000182.37,0.00,0.00,0.00

        String body = "FUN=410502&TBL_IN=fundid,moneytype,remark;,,;";
//                +
//                UserHelper.getUser().fundid + ";";
        BizRequest request = new BizRequest();
        request.setBody(body);
        request.setCallback(new ResponseCallback() {
            @Override
            public void callback(Client client, Response data) {
                if (data.isSucceed()) {
                    Log.e("TradeHandFragment", data.getData());
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
//                                for (int i = 1; i < split.length; i++) {
//                                    String[] var = split[i].split(",");
//                                    Toast.makeText(mContext, var[0], Toast.LENGTH_SHORT).show();
//                                }
                                String[] var = split[1].split(",");
                                yu_e = Float.parseFloat(var[4]);
                                ke_yong = Float.parseFloat(var[5]);
                                ke_qu = yu_e - ke_yong;

                                mList = new ArrayList<>();
                                mList.add(new TradeFundsEntity("币种", 0));
                                mList.add(new TradeFundsEntity("余额", yu_e));
                                mList.add(new TradeFundsEntity("可用", ke_yong));
                                mList.add(new TradeFundsEntity("可取", ke_qu));
                                mList.add(new TradeTitleHandEntity());
                                mList.add(new TradeHandEntity());
                                adapter.addAll(mList);
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
    public void OnPost() {

    }
}
