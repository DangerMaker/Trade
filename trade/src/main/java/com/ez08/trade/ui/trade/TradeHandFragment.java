package com.ez08.trade.ui.trade;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ez08.trade.R;
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

import java.util.ArrayList;
import java.util.List;

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

        mList = new ArrayList<>();
        mList.add(new TradeFundsEntity("总资产",12000));
        mList.add(new TradeFundsEntity("总市值",11000));
        mList.add(new TradeFundsEntity("总盈利",1000));
        mList.add(new TradeFundsEntity("可用资产",3000));
        mList.add(new TradeFundsEntity("可取资产",3000));
        mList.add(new TradeTitleHandEntity());
        mList.add(new TradeHandEntity());
        adapter.addAll(mList);
    }

    @Override
    public void OnPost() {

    }
}
