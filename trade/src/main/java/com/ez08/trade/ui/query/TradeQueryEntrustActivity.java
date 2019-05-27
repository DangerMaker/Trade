package com.ez08.trade.ui.query;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.request.BizRequest;
import com.ez08.trade.net.Client;
import com.ez08.trade.net.ClientHelper;
import com.ez08.trade.net.Response;
import com.ez08.trade.net.Callback;
import com.ez08.trade.tools.DatePickerCallback;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.trade.adpater.TradeDealAdapter;
import com.ez08.trade.ui.trade.adpater.TradeEntrustAdapter;
import com.ez08.trade.ui.trade.entity.TradeDealEntity;
import com.ez08.trade.ui.trade.entity.TradeEntrustEntity;
import com.ez08.trade.ui.trade.entity.TradeTitleDealEntity;
import com.ez08.trade.ui.trade.entity.TradeTitleEntrustEntity;
import com.ez08.trade.ui.view.LinearItemDecoration;
import com.ez08.trade.user.UserHelper;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TradeQueryEntrustActivity extends BaseActivity implements View.OnClickListener, DatePickerCallback {

    TextView titleView;
    ImageView backBtn;
    int UItype = 0;

    RecyclerView recyclerView;
    LinearLayoutManager manager;
    List<Object> mList;
    BaseAdapter adapter;
    TextView beginTV;
    TextView closeTV;
    LinearLayout date_layout;

    int beginYear;
    int beginMonth;
    int beginDay;

    int closeYear;
    int closeMonth;
    int closeDay;
    SimpleDateFormat dateFormat;
    SimpleDateFormat postFormat;

    String beginValue;
    String endValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_query_entrust);
        if (getIntent() != null) {
            UItype = getIntent().getIntExtra("type", 0);
        }
        titleView = findViewById(R.id.title);
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        date_layout = findViewById(R.id.date_layout);
        if (UItype == 0) {
            titleView.setText("当日委托");
            date_layout.setVisibility(View.GONE);
        } else if (UItype == 1) {
            titleView.setText("历史委托");
        } else if (UItype == 2) {
            titleView.setText("当日成交");
            date_layout.setVisibility(View.GONE);
        } else if (UItype == 3) {
            titleView.setText("历史成交");
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        if (UItype == 0 || UItype == 1) {
            adapter = new TradeEntrustAdapter(this);
            recyclerView.setAdapter(adapter);
            mList = new ArrayList<>();
            mList.add(new TradeTitleEntrustEntity());
            adapter.addAll(mList);
        } else {
            adapter = new TradeDealAdapter(this);
            recyclerView.setAdapter(adapter);
            mList = new ArrayList<>();
            mList.add(new TradeTitleDealEntity());
            adapter.addAll(mList);
        }

        LinearItemDecoration divider = new LinearItemDecoration(this);
        recyclerView.addItemDecoration(divider);

        beginTV = findViewById(R.id.date_begin);
        closeTV = findViewById(R.id.date_close);
        beginTV.setOnClickListener(this);
        closeTV.setOnClickListener(this);

        if (UItype == 0) {
            getWTInfo();
        } else if (UItype == 1) {
            Calendar calendar = Calendar.getInstance();
            Date date = new Date();
            calendar.setTime(date);
            closeYear = calendar.get(Calendar.YEAR);
            closeMonth = calendar.get(Calendar.MONTH);
            closeDay = calendar.get(Calendar.DAY_OF_MONTH);
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            closeTV.setText(dateFormat.format(date));
            postFormat = new SimpleDateFormat("yyyyMMdd");
            endValue = postFormat.format(date);

            calendar.add(Calendar.DATE, -7);
            Date begin = calendar.getTime();
            beginYear = calendar.get(Calendar.YEAR);
            beginMonth = calendar.get(Calendar.MONTH);
            beginDay = calendar.get(Calendar.DAY_OF_MONTH);
            beginTV.setText(dateFormat.format(begin));
            beginValue = postFormat.format(begin);
            getWTHisInfo();
        } else if (UItype == 2) {
            getInfo();
        } else if (UItype == 3) {
            Calendar calendar = Calendar.getInstance();
            Date date = new Date();
            calendar.setTime(date);
            closeYear = calendar.get(Calendar.YEAR);
            closeMonth = calendar.get(Calendar.MONTH);
            closeDay = calendar.get(Calendar.DAY_OF_MONTH);
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            closeTV.setText(dateFormat.format(date));
            postFormat = new SimpleDateFormat("yyyyMMdd");
            endValue = postFormat.format(date);

            calendar.add(Calendar.DATE, -7);
            Date begin = calendar.getTime();
            beginYear = calendar.get(Calendar.YEAR);
            beginMonth = calendar.get(Calendar.MONTH);
            beginDay = calendar.get(Calendar.DAY_OF_MONTH);
            beginTV.setText(dateFormat.format(begin));
            beginValue = postFormat.format(begin);

            getHisInfo();
        }

    }

    public void getInfo() {
        String body = "FUN=410512&TBL_IN=fundid,market,secuid,stkcode,ordersno,bankcode,qryflag,count,poststr,qryoperway;" +
                UserHelper.getUser().fundid + "," +
                "" + "," +
                UserHelper.getUser().secuid + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                "1" + "," +
                "100" + "," +
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
                                String out = uri.getQueryParameter(key);
                                String[] split = out.split(";");
                                for (int i = 1; i < split.length; i++) {
                                    String[] var = split[i].split(",");
                                    TradeDealEntity entity = new TradeDealEntity();
                                    entity.stkcode = var[8];
                                    entity.stkname = var[9];
                                    entity.matchtime = var[10];
                                    entity.matchprice = var[11];
                                    entity.matchqty = var[12];
                                    entity.trddate = var[1];
                                    entity.bsFlag = var[4];
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

    public void getHisInfo() {
        String body = "FUN=411513&TBL_IN=strdate,enddate,fundid,market,secuid,stkcode,bankcode,qryflag,count,poststr;" +
                beginValue + "," +
                endValue + "," +
                UserHelper.getUser().fundid + "," +
                "" + "," +
                UserHelper.getUser().secuid + "," +
                "" + "," +
                "" + "," +
                "1" + "," +
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
                                mList.clear();
                                mList.add(new TradeTitleDealEntity());
                                String out = uri.getQueryParameter(key);
                                String[] split = out.split(";");
                                for (int i = 1; i < split.length; i++) {
                                    String[] var = split[i].split(",");
                                    TradeDealEntity entity = new TradeDealEntity();
                                    entity.stkcode = var[8];
                                    entity.stkname = var[9];
                                    entity.matchtime = var[10];
                                    entity.matchprice = var[12];
                                    entity.matchqty = var[13];
                                    entity.trddate = var[2];
                                    entity.bsFlag = var[4];
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

    public void getWTInfo() {
        String body = "FUN=410510&TBL_IN=market,fundid,secuid,stkcode,ordersno,Ordergroup,bankcode,qryflag,count,poststr,extsno,qryoperway;" +
                "" + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                "1" + "," +
                "100" + "," +
                "" + "," +
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
                                String out = uri.getQueryParameter(key);
                                String[] split = out.split(";");
                                for (int i = 1; i < split.length; i++) {
                                    String[] var = split[i].split(",");
                                    TradeEntrustEntity entity = new TradeEntrustEntity();
                                    entity.stkcode = var[14];
                                    entity.stkname = var[15];
                                    entity.orderprice = var[16];
                                    entity.opertime = var[18];
                                    entity.orderdate = var[1];
                                    entity.orderqty = var[17];
                                    entity.matchqty = var[20];
                                    entity.bsflag = var[10];
                                    entity.orderstatus = var[23];
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

    public void getWTHisInfo() {
        String body = "FUN=411511&TBL_IN=strdate,enddate,fundid,market,secuid,stkcode,ordersno,Ordergroup,bankcode,qryflag,count,poststr,extsno,qryoperway;" +
                beginValue + "," +
                endValue + "," +
                UserHelper.getUser().fundid + "," +
                "" + "," +
                UserHelper.getUser().secuid + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                "" + "," +
                "1" + "," +
                "100" + "," +
                "" + "," +
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
                                mList.clear();
                                mList.add(new TradeTitleEntrustEntity());
                                String out = uri.getQueryParameter(key);
                                String[] split = out.split(";");
                                for (int i = 1; i < split.length; i++) {
                                    String[] var = split[i].split(",");
                                    TradeEntrustEntity entity = new TradeEntrustEntity();
                                    entity.stkcode = var[13];
                                    entity.stkname = var[14];
                                    entity.orderprice = var[15];
                                    entity.opertime = var[11];
                                    entity.orderdate = var[1];
                                    entity.orderqty = var[16];
                                    entity.matchqty = var[18];
                                    entity.bsflag = var[8];
                                    entity.orderstatus = var[21];
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
        if (v == beginTV) {
            DatePickerFragment adf = DatePickerFragment.newInstance(0, beginYear, beginMonth, beginDay);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            adf.show(ft, "123");
        } else if (v == closeTV) {
            DatePickerFragment adf = DatePickerFragment.newInstance(1, closeYear, closeMonth, closeDay);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            adf.show(ft, "456");
        }else if(v == backBtn){
            finish();
        }
    }

    @Override
    public void callback(int type, int year, int month, int day) {
        if (type == 0) {
            if (beginYear == year && beginMonth == month && beginDay == day) {
                return;
            }

            beginYear = year;
            beginMonth = month;
            beginDay = day;
            Calendar calendar = Calendar.getInstance();
            calendar.set(beginYear, beginMonth, beginDay);
            beginTV.setText(dateFormat.format(calendar.getTime()));
            beginValue = postFormat.format(calendar.getTime());

            if(UItype == 0 || UItype == 1) {
                getWTHisInfo();
            }else{
                getHisInfo();
            }
        } else {
            if (closeYear == year && closeMonth == month && closeDay == day) {
                return;
            }

            closeYear = year;
            closeMonth = month;
            closeDay = day;
            Calendar calendar = Calendar.getInstance();
            calendar.set(closeYear, closeMonth, closeDay);
            closeTV.setText(dateFormat.format(calendar.getTime()));
            endValue = postFormat.format(calendar.getTime());

            if(UItype == 0 || UItype == 1) {
                getWTHisInfo();
            }else{
                getHisInfo();
            }
        }
    }
}
