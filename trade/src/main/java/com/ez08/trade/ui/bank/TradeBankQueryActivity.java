package com.ez08.trade.ui.bank;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.request.BizRequest;
import com.ez08.trade.net.Client;
import com.ez08.trade.net.ClientHelper;
import com.ez08.trade.net.Response;
import com.ez08.trade.net.Callback;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.bank.adpater.TradeTransAdapter;
import com.ez08.trade.ui.bank.entity.TransferEntity;
import com.ez08.trade.ui.bank.entity.TransferTitleEntity;
import com.ez08.trade.ui.view.LinearItemDecoration;
import com.ez08.trade.user.UserHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TradeBankQueryActivity extends BaseActivity implements View.OnClickListener {

    ImageView img_back;
    TextView titleView;

    RecyclerView recyclerView;
    LinearLayoutManager manager;
    List<Object> mList;
    BaseAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_query_bank_record);
        titleView = findViewById(R.id.title);
        titleView.setText("转账查询");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new TradeTransAdapter(context);
        recyclerView.setAdapter(adapter);
        LinearItemDecoration divider = new LinearItemDecoration(this);
        recyclerView.addItemDecoration(divider);

        getInfo();
    }


    public void getInfo() {
        String body = "FUN=410608&TBL_IN=fundid,moneytype,sno,extsno,qryoperway;" +
                UserHelper.getUser().fundid + "," +
                "" + "," +
                ""+ "," +
                "" + "," +
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
                                mList = new ArrayList<>();
                                mList.add(new TransferTitleEntity());
                                String out = uri.getQueryParameter(key);
                                String[] split = out.split(";");
                                for (int i = 1; i < split.length; i++) {
                                    String[] var = split[i].split(",");
                                    TransferEntity entity = new TransferEntity();
                                    entity.operdate = var[0];
                                    entity.opertime = var[1];
                                    entity.status = var[11];
                                    entity.fundeffect = var[8];
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
    public void onClick(View v) {

    }
}
