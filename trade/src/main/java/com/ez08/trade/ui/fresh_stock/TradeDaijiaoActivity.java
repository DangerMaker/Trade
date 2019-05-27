package com.ez08.trade.ui.fresh_stock;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.net.Callback;
import com.ez08.trade.net.Client;
import com.ez08.trade.net.ClientHelper;
import com.ez08.trade.net.Response;
import com.ez08.trade.net.YCParser;
import com.ez08.trade.net.request.BizRequest;
import com.ez08.trade.tools.YiChuangUtils;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.view.LinearItemDecoration;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TradeDaijiaoActivity extends BaseActivity implements View.OnClickListener {

    ImageView backBtn;
    TextView titleView;
    RecyclerView recyclerView;
    List<Map<String, String>> list;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_new_stock_hit);

        titleView = findViewById(R.id.title);
        titleView.setText("新股申购代缴查询");
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearItemDecoration divider = new LinearItemDecoration(this);
        recyclerView.addItemDecoration(divider);

        list = new ArrayList<>();
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        getList();
    }

    @Override
    public void onClick(View v) {
        if (v == backBtn) {
            finish();
        }
    }

    private void getList() {
        String body = "FUN=411547&TBL_IN=secuid,market,stkcode,issuetype;" +
                "," +
                "," +
                "," +
                ";";
        BizRequest request = new BizRequest();
        request.setBody(body);
        request.setCallback(new Callback() {
            @Override
            public void callback(Client client, Response data) {
                dismissBusyDialog();
//                String a = "{\n" +
//                        "    \t\"dwContentLen\":\t167,\n" +
//                        "    \t\"content\":\t\"FUN=411547&TBL_OUT=custid,secuid,market,stkcode,stkname,orderdate,matchdate,issuetype,hitqty,matchprice,giveupqty,hitamt,payqty,payamt,frzamt,clearsno,status,stktype;001,001,1,600600,青岛啤酒,20120908,20120919,1,10000,12.99,100,100,100,100,100,,,\"\n" +
//                        "    }";
//                data.setData(a);
                if (data.isSucceed()) {
                    Log.e("新股代缴款", data.getData());
                    try {
                        list = YCParser.parseArray(data.getData());
                        myAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        showBusyDialog();
        ClientHelper.get().send(request);
    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyHolder(LayoutInflater.from(context).inflate(R.layout.trade_holder_daijiao, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
            final Map<String,String> entity = list.get(i);
            myHolder.name.setText(entity.get("stkname"));
            myHolder.code.setText(entity.get("stkcode"));
            myHolder.date.setText(entity.get("matchdate"));
            myHolder.marketName.setText(YiChuangUtils.getMarketType(entity.get("market")));
            myHolder.hitNum.setText(entity.get("hitqty"));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView code;
        TextView date;
        TextView marketName;
        TextView hitNum;

        public MyHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            code = view.findViewById(R.id.code);
            date = view.findViewById(R.id.date);
            marketName = view.findViewById(R.id.market);
            hitNum = view.findViewById(R.id.hit);

        }
    }

}
