package com.ez08.trade.ui.query;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.ActivityCallback;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TradeQueryEntrustActivity extends BaseActivity implements View.OnClickListener, DatePickerCallback {

    TextView titleView;
    int type = 0;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_query_entrust);
        if (getIntent() != null) {
            type = getIntent().getIntExtra("type", 0);
        }
        titleView = findViewById(R.id.title);
        date_layout = findViewById(R.id.date_layout);
        if (type == 0) {
            titleView.setText("当日委托");
            date_layout.setVisibility(View.GONE);
        } else if (type == 1) {
            titleView.setText("历史委托");
        } else if (type == 2) {
            titleView.setText("当日成交");
            date_layout.setVisibility(View.GONE);
        } else if (type == 3) {
            titleView.setText("历史成交");
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        if(type == 0 || type == 1) {
            adapter = new TradeEntrustAdapter(this);
            recyclerView.setAdapter(adapter);
            mList = new ArrayList<>();
            mList.add(new TradeTitleEntrustEntity());
            mList.add(new TradeEntrustEntity());
            mList.add(new TradeEntrustEntity());
            adapter.addAll(mList);
        }else{
            adapter = new TradeDealAdapter(this);
            recyclerView.setAdapter(adapter);
            mList = new ArrayList<>();
            mList.add(new TradeTitleDealEntity());
            mList.add(new TradeDealEntity());
            mList.add(new TradeDealEntity());
            adapter.addAll(mList);
        }

        LinearItemDecoration divider = new LinearItemDecoration(this);
        recyclerView.addItemDecoration(divider);

        beginTV = findViewById(R.id.date_begin);
        closeTV = findViewById(R.id.date_close);
        beginTV.setOnClickListener(this);
        closeTV.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);

        beginYear = calendar.get(Calendar.YEAR);
        closeYear = beginYear;
        beginMonth = calendar.get(Calendar.MONTH);
        closeMonth = beginMonth;
        beginDay = calendar.get(Calendar.DAY_OF_MONTH);
        closeDay = beginDay;

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        beginTV.setText(dateFormat.format(date));
        closeTV.setText(dateFormat.format(date));
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
        }
    }

    @Override
    public void callback(int type, int year, int month, int day) {
        if (type == 0) {
            beginYear = year;
            beginMonth = month;
            beginDay = day;
            Calendar calendar = Calendar.getInstance();
            calendar.set(beginYear, beginMonth, beginDay);
            beginTV.setText(dateFormat.format(calendar.getTime()));
        } else {
            closeYear = year;
            closeMonth = month;
            closeDay = day;
            Calendar calendar = Calendar.getInstance();
            calendar.set(closeYear, closeMonth, closeDay);
            closeTV.setText(dateFormat.format(calendar.getTime()));
        }
    }
}
