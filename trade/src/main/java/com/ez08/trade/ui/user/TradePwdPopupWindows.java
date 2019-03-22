package com.ez08.trade.ui.user;

import android.content.Context;
import android.view.View;

import com.ez08.trade.R;
import com.ez08.trade.tools.ScreenUtil;
import com.ez08.trade.ui.BasePopupWindows;

public class TradePwdPopupWindows extends BasePopupWindows {

    public TradePwdPopupWindows(Context context) {
        super(context);
    }

    @Override
    protected void onCreateView(View view) {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.trade_view_select_pwd;
    }

    @Override
    protected void setData(Object data) {

    }

    @Override
    public void showAsDropDown(View anchor) {
        if (!this.isShowing()) {
            showAsDropDown(anchor, (int)ScreenUtil.getScreenWidth(context) - container.getWidth(), 0);
        } else {
            this.dismiss();
        }
    }
}
