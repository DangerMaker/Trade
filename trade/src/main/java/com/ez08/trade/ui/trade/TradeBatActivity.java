package com.ez08.trade.ui.trade;

import com.ez08.trade.ui.SegmentsTemplateActivity;
import com.ez08.trade.ui.view.customtab.EasyFragment;

import java.util.ArrayList;

public class TradeBatActivity extends SegmentsTemplateActivity {

    @Override
    protected void setTitles() {
        titles = new String[]{"单账户批量买入", "单账户批量卖出"};
    }

    @Override
    protected void initFragmentList(ArrayList<EasyFragment> mFragmentList) {
        mFragmentList.add(new EasyFragment(TradeOptionFragment.newInstance(0), titles[0]));
        mFragmentList.add(new EasyFragment(TradeOptionFragment.newInstance(1), titles[1]));
    }

}
