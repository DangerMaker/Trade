package com.ez08.trade.ui.fresh_stock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.JumpActivity;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.fresh_stock.adpater.TradeFreshBuyAdapter;
import com.ez08.trade.ui.fresh_stock.adpater.TradeFreshListAdapter;
import com.ez08.trade.ui.fresh_stock.entity.TradeDrawLeftEntity;
import com.ez08.trade.ui.fresh_stock.entity.TradeFreshBuyEntity;
import com.ez08.trade.ui.fresh_stock.entity.TradeLineEntity;
import com.ez08.trade.ui.trade.entity.TradeOtherEntity;
import com.ez08.trade.ui.view.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TradeFastStockActivity extends BaseActivity implements View.OnClickListener {

    ImageView backBtn;
    TextView titleView;
    TextView subTitleView;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    List<Object> mList;

    TradeFreshBuyAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_fast_buy);
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        titleView = findViewById(R.id.title);
        titleView.setText("新股申购查询");
        subTitleView = findViewById(R.id.sub_title);
        subTitleView.setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new TradeFreshBuyAdapter(this);
        recyclerView.setAdapter(adapter);

        LinearItemDecoration divider = new LinearItemDecoration(this);
        recyclerView.addItemDecoration(divider);

        mList = new ArrayList<>();
        mList.add(new TradeFreshBuyEntity());
        mList.add(new TradeLineEntity());
        mList.add(new TradeFreshBuyEntity());
        adapter.addAll(mList);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.img_back){
            finish();
        }else if(v.getId() == R.id.sub_title){
            JumpActivity.start(this,"新股申购查询");
        }
    }
}
