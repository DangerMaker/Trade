package com.ez08.trade.ui.trade.holder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.trade.entity.TradeEntrustEntity;
import com.ez08.trade.ui.view.SingleLineAutoResizeTextView;

public class TradeEntrustHolder extends BaseViewHolder<TradeEntrustEntity> {

    TextView name;
    SingleLineAutoResizeTextView time;
    TextView price;
    TextView num1;
    TextView num2;
    TextView direction;
    TextView state;

    public TradeEntrustHolder(ViewGroup itemView) {
        super(itemView, R.layout.trade_holder_entrust);
        name = $(R.id.txt_name);
        time = $(R.id.txt_code);
        price = $(R.id.stock_entrust_price);
        num1 = $(R.id.stock_entrust_num);
        num2 = $(R.id.stock_deal_num);
        direction = $(R.id.stock_entrust_option);
        state = $(R.id.stock_entrust_state);
    }

    @Override
    public void setData(TradeEntrustEntity data) {
        name.setText(data.stkname);
        if(data.opertime.length() == 6 || data.opertime.length() == 8) {
            String hour = data.opertime.substring(0,2);
            String min = data.opertime.substring(2,4);
            String sec = data.opertime.substring(4,6);
            time.setTextContent(data.orderdate + " " +hour + ":" + min + ":" + sec);
        }
        price.setText(data.orderprice);
        num1.setText(data.orderqty);
        num2.setText(data.matchqty);
        if (Boolean.parseBoolean(data.bsflag)) {
            direction.setText("卖出");
        } else {
            direction.setText("买入");
        }

        String status;
        if (data.orderstatus.equals("0")) {
            status = "未报";
        } else if (data.orderstatus.equals("1")) {
            status = "正报";
        } else if (data.orderstatus.equals("2")) {
            status = "已报";
        } else if (data.orderstatus.equals("3")) {
            status = "已报待撤";
        } else if (data.orderstatus.equals("4")) {
            status = "部成待撤";
        } else if (data.orderstatus.equals("5")) {
            status = "部撤";
        } else if (data.orderstatus.equals("6")) {
            status = "已撤";
        } else if (data.orderstatus.equals("7")) {
            status = "部成";
        } else if (data.orderstatus.equals("8")) {
            status = "已成";
        } else if (data.orderstatus.equals("9")) {
            status = "废单";
        } else if (data.orderstatus.equals("A")) {
            status = "待报";
        } else if (data.orderstatus.equals("B")) {
            status = "正报";
        } else {
            status = "未知";
        }
        state.setText(status);
    }
}
