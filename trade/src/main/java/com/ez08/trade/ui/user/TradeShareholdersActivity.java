package com.ez08.trade.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.trade.adpater.TradeMainAdapter;
import com.ez08.trade.ui.trade.entity.TradeOtherEntity;
import com.ez08.trade.ui.user.adpater.TradeShareHoldersAdapter;
import com.ez08.trade.ui.user.entity.TradeShareHoldersItem;
import com.ez08.trade.ui.user.entity.TradeShareHoldersTitle;
import com.ez08.trade.ui.view.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TradeShareholdersActivity extends BaseActivity implements View.OnClickListener {

    ImageView backBtn;
    TextView titleView;

    RecyclerView recyclerView;
    LinearLayoutManager manager;
    List<Object> mList;
    TradeShareHoldersAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_shareholders);
        titleView = findViewById(R.id.title);
        titleView.setText("股东资料");
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new TradeShareHoldersAdapter(this);
        recyclerView.setAdapter(adapter);

        LinearItemDecoration divider = new LinearItemDecoration(this);
        recyclerView.addItemDecoration(divider);

        mList = new ArrayList<>();
        mList.add(new TradeShareHoldersTitle());
//        mList.add(new TradeShareHoldersItem("01234567890(主)","刘宇","沪市","153000"));
//        mList.add(new TradeShareHoldersItem("01234567890(主)","刘宇","深市","153001"));
        adapter.addAll(mList);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.img_back){
            finish();
        }
    }
}
