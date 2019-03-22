package com.ez08.trade.ui.trade;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseFragment;
import com.ez08.trade.ui.Interval;
import com.ez08.trade.ui.trade.adpater.TradeActionAdapter;
import com.ez08.trade.ui.trade.entity.TradeHandEntity;
import com.ez08.trade.ui.trade.entity.TradeHeaderEntity;
import com.ez08.trade.ui.trade.entity.TradeTitleHandEntity;
import com.ez08.trade.ui.view.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TradeOptionFragment extends BaseFragment implements Interval {

    RecyclerView recyclerView;
    LinearLayoutManager manager;
    List<Object> mList;

    TradeActionAdapter adapter;
    public static TradeOptionFragment newInstance() {
        Bundle args = new Bundle();
        TradeOptionFragment fragment = new TradeOptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.trade_fragment_option;
    }

    @Override
    protected void onCreateView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(manager);

        adapter = new TradeActionAdapter(mContext);
        recyclerView.setAdapter(adapter);

        LinearItemDecoration divider = new LinearItemDecoration(mContext);
        recyclerView.addItemDecoration(divider);

        mList = new ArrayList<>();
        mList.add(new TradeHeaderEntity());
        mList.add(new TradeTitleHandEntity());
        mList.add(new TradeHandEntity());
        adapter.addAll(mList);
    }

    @Override
    public void OnPost() {

    }
}
