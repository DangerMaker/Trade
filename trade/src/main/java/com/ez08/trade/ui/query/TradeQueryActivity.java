package com.ez08.trade.ui.query;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.trade.adpater.TradeMainAdapter;
import com.ez08.trade.ui.trade.entity.TradeOtherEntity;
import com.ez08.trade.ui.view.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TradeQueryActivity extends BaseActivity implements View.OnClickListener {

    ImageView backBtn;
    TextView titleView;
    RecyclerView recyclerView;
    GridLayoutManager manager;
    List<Object> mList;

    TradeMainAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_custom);
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        titleView = findViewById(R.id.title);
        titleView.setText("查询");

        recyclerView = findViewById(R.id.recycler_view);
        manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                return 4;
            }
        });

        adapter = new TradeMainAdapter(this);
        recyclerView.setAdapter(adapter);

        LinearItemDecoration divider = new LinearItemDecoration(this);
        recyclerView.addItemDecoration(divider);

        mList = new ArrayList<>();
        mList.add(new TradeOtherEntity("当日成交"));
        mList.add(new TradeOtherEntity("历史成交"));
        mList.add(new TradeOtherEntity("当日委托"));
        mList.add(new TradeOtherEntity("历史委托"));
        adapter.addAll(mList);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.img_back){
            finish();
        }
    }
}
