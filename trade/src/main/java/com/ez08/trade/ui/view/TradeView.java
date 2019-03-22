package com.ez08.trade.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ez08.trade.R;

public class TradeView extends LinearLayout {

    Context context;
    ImageView plusPriceIv;
    ImageView reducePriceIv;
    ImageView plusNumIv;
    ImageView reduceNumIv;
    public TradeView(Context context,AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.trade_view,this);
        plusPriceIv = findViewById(R.id.right_plus_price);
        plusNumIv = findViewById(R.id.right_plus_num);
        reducePriceIv = findViewById(R.id.left_reduce_price);
        reduceNumIv = findViewById(R.id.left_reduce_num);

        plusPriceIv.setColorFilter(Color.RED);
        plusNumIv.setColorFilter(Color.RED);
        reducePriceIv.setColorFilter(Color.RED);
        reduceNumIv.setColorFilter(Color.RED);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }
}
