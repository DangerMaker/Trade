package com.ez08.trade.ui.user;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.ScreenUtil;
import com.ez08.trade.ui.BasePopupWindows;

import org.w3c.dom.Text;

public class TradePwdPopupWindows extends BasePopupWindows {

    TextView trade;
    TextView funds;

    public TradePwdPopupWindows(Context context) {
        super(context);
    }

    @Override
    protected void onCreateView(View view) {
        trade = view.findViewById(R.id.trade);
        funds = view.findViewById(R.id.funds);
        trade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.callback(0);
            }
        });

        funds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.callback(1);
            }
        });
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

    Callback callback;

    public void setCallback(Callback callback){
        this.callback = callback;
    }

    public interface Callback{
        void callback(int type);
    }
}
