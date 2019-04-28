package com.ez08.trade.ui.trade;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.ez08.trade.R;
import com.ez08.trade.tools.ScreenUtil;
import com.ez08.trade.ui.BasePopupWindows;

public class TradeSearchWindows extends BasePopupWindows {

    public TradeSearchWindows(Context context,int width,int height) {
        super(context,width,height);
    }


    @Override
    protected void onCreateView(View view) {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.trade_view_search;
    }

    @Override
    protected void setData(Object data) {

    }

    @Override
    public void showAsDropDown(View anchor) {
        if (!this.isShowing()) {
            showAsDropDown(anchor, 0, 0);
        } else {
            this.dismiss();
        }
    }
}
