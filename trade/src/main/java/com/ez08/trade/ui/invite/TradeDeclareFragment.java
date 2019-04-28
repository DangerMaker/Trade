package com.ez08.trade.ui.invite;

import android.os.Bundle;
import android.view.View;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseFragment;

public class TradeDeclareFragment extends BaseFragment {

    public static TradeDeclareFragment newInstance() {
        Bundle args = new Bundle();
        TradeDeclareFragment fragment = new TradeDeclareFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.trade_fragment_invited_declare;
    }

    @Override
    protected void onCreateView(View rootView) {

    }
}
