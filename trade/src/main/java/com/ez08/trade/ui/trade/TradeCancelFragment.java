package com.ez08.trade.ui.trade;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseFragment;
import com.ez08.trade.ui.Interval;
import com.ez08.trade.ui.trade.adpater.TradeEntrustAdapter;
import com.ez08.trade.ui.trade.entity.TradeEntrustEntity;
import com.ez08.trade.ui.trade.entity.TradeTitleEntrustEntity;
import com.ez08.trade.ui.view.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TradeCancelFragment extends BaseFragment implements Interval {

    RecyclerView recyclerView;
    LinearLayoutManager manager;
    List<Object> mList;

    TradeEntrustAdapter adapter;
    public static TradeCancelFragment newInstance() {
        Bundle args = new Bundle();
        TradeCancelFragment fragment = new TradeCancelFragment();
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

        adapter = new TradeEntrustAdapter(mContext);
        recyclerView.setAdapter(adapter);

        LinearItemDecoration divider = new LinearItemDecoration(mContext);
        recyclerView.addItemDecoration(divider);

        mList = new ArrayList<>();
        mList.add(new TradeTitleEntrustEntity());
        mList.add(new TradeEntrustEntity());
        mList.add(new TradeEntrustEntity());
        adapter.addAll(mList);
    }

    @Override
    public void OnPost() {

    }
}
