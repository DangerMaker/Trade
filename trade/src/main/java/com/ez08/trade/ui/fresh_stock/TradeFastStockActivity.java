package com.ez08.trade.ui.fresh_stock;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.BizRequest;
import com.ez08.trade.net.Client;
import com.ez08.trade.net.ClientHelper;
import com.ez08.trade.net.Response;
import com.ez08.trade.net.ResponseCallback;
import com.ez08.trade.tools.JumpActivity;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.fresh_stock.adpater.TradeFreshBuyAdapter;
import com.ez08.trade.ui.fresh_stock.adpater.TradeFreshListAdapter;
import com.ez08.trade.ui.fresh_stock.entity.TradeDrawLeftEntity;
import com.ez08.trade.ui.fresh_stock.entity.TradeFreshBuyEntity;
import com.ez08.trade.ui.fresh_stock.entity.TradeLineEntity;
import com.ez08.trade.ui.fresh_stock.entity.TradeNewStockLinesEntity;
import com.ez08.trade.ui.fresh_stock.entity.TradeZqEntity;
import com.ez08.trade.ui.trade.entity.TradeOtherEntity;
import com.ez08.trade.ui.view.LinearItemDecoration;
import com.ez08.trade.user.UserHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TradeFastStockActivity extends BaseActivity implements View.OnClickListener {

    Button submit;
    ImageView backBtn;
    TextView titleView;
    TextView subTitleView;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    List<TradeFreshBuyEntity> mList;
    List<TradeNewStockLinesEntity> linesEntityList;
    TextView tips;

    TradeNewStockAdapter adapter;
    String ALines = "0";
    String Slines = "0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_fast_buy);
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        titleView = findViewById(R.id.title);
        titleView.setText("新股申购");
        subTitleView = findViewById(R.id.sub_title);
        subTitleView.setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new TradeNewStockAdapter();
        recyclerView.setAdapter(adapter);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        tips = findViewById(R.id.tips);

        LinearItemDecoration divider = new LinearItemDecoration(this);
        recyclerView.addItemDecoration(divider);

        mList = new ArrayList<>();
        linesEntityList = new ArrayList<>();
        getTips();
        post();
    }

    public void getTips(){
        String body = "FUN=410610&TBL_IN=market,secuid,orgid,count,posstr;" +
                "" + "," +
                UserHelper.getUser().secuid + "," +
                "" + "," +
                "100" + "," +
                ";";

        BizRequest request = new BizRequest();
        request.setBody(body);
        request.setCallback(new ResponseCallback() {
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
                                    TradeNewStockLinesEntity entity = new TradeNewStockLinesEntity();
                                    entity.market = var[4];
                                    entity.custquota = var[6];
                                    entity.receivedate = var[7];
                                    linesEntityList.add(entity);
                                }

                                for (int i = 0; i < linesEntityList.size(); i++) {
                                    if(linesEntityList.get(i).market.equals("0")){
                                        Slines = linesEntityList.get(i).custquota;
                                    }else if(linesEntityList.get(i).market.equals("1")){
                                        ALines = linesEntityList.get(i).custquota;
                                    }
                                }

                                tips.setText("您今日的申购额度为沪市" + ALines + "股,深市" + Slines + "股");
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


    public void post() {
        mList.clear();

        String body = "FUN=411549&TBL_IN=market,stkcode,issuedate;" +
                "" + "," +
                "" + "," +
                ";";

        BizRequest request = new BizRequest();
        request.setBody(body);
        request.setCallback(new ResponseCallback() {
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
                                    TradeFreshBuyEntity entity = new TradeFreshBuyEntity();
                                    entity.stkcode = var[1];
                                    entity.stkname = var[2];
                                    entity.linkstk = var[3];
                                    entity.maxqty = var[8];
                                    entity.minqty = var[9];
                                    mList.add(entity);
                                }
                                adapter.setData(mList);
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

    class TradeNewStockAdapter extends RecyclerView.Adapter<TradeFreshStockHolder> {

        List<TradeFreshBuyEntity> list;
        List<String> codes;

        public TradeNewStockAdapter() {
            this.list = new ArrayList<>();
            this.codes = new ArrayList<>();
        }

        @Override
        public TradeFreshStockHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.trade_holder_fresh_item, parent,
                    false);
            return new TradeFreshStockHolder(view);
        }

        @Override
        public void onBindViewHolder(final TradeFreshStockHolder holder, int position) {
            final TradeFreshBuyEntity model = list.get(position);

            holder.name.setText(model.stkname);
            holder.code.setText(model.stkcode);
            holder.num.setText(model.maxqty);
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    model.isSelect = b;
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        public void setData(List<TradeFreshBuyEntity> array) {
            this.list = array;
            notifyDataSetChanged();
        }
    }

    class TradeFreshStockHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView code;
        public TextView num;
        public ImageView left_reduce_num;
        public ImageView right_plus_num;
        public AppCompatCheckBox checkBox;

        public TradeFreshStockHolder(View itemView) {
            super(itemView);
            left_reduce_num = (ImageView) itemView.findViewById(R.id.left_reduce_num);
            right_plus_num = (ImageView) itemView.findViewById(R.id.right_plus_num);
            left_reduce_num.setColorFilter(Color.RED);
            right_plus_num.setColorFilter(Color.RED);
            checkBox = (AppCompatCheckBox) itemView.findViewById(R.id.checkbox);
            name = (TextView) itemView.findViewById(R.id.txt_name);
            code = (TextView) itemView.findViewById(R.id.txt_code);
            num = (TextView) itemView.findViewById(R.id.txt_num);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            finish();
        } else if (v.getId() == R.id.sub_title) {
            JumpActivity.start(this, "新股申购查询");
        }else if(v.getId() == R.id.submit){
            for (int i = 0; i < mList.size(); i++) {
                Log.e(TAG, mList.get(i).stkname + ":" +mList.get(i).isSelect );

            }
        }
    }
}
