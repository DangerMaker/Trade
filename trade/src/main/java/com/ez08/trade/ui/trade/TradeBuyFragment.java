package com.ez08.trade.ui.trade;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseFragment;
import com.ez08.trade.ui.view.FiveAndTenView;
import com.ez08.trade.ui.view.TradeView;

public class TradeBuyFragment extends BaseFragment {

    public static TradeBuyFragment newInstance(int type) {
        Bundle args = new Bundle();
        TradeBuyFragment fragment = new TradeBuyFragment();
        args.putInt("type",type);
        fragment.setArguments(args);
        return fragment;
    }

    TradeView tradeView;
    FiveAndTenView fiveAndTenView;
    EditText editText;

    @Override
    protected int getLayoutResource() {
        return R.layout.trade_fragment_buy;
    }

    @Override
    protected void onCreateView(View rootView) {
        fiveAndTenView = rootView.findViewById(R.id.five_ten_view);
        fiveAndTenView.setOnFiveListener(new FiveAndTenView.OnFiveListener() {
            @Override
            public void OnFive(String value) {
                Log.e("TradeBuyFragment",value);
                editText.setText(value);
            }
        });

        editText = rootView.findViewById(R.id.trade_entrust_price);
        tradeView = rootView.findViewById(R.id.trade_view);

        int type = getArguments().getInt("type");
        tradeView.setBorderColor(type);
    }
}
