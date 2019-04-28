package com.ez08.trade.ui.fresh_stock.holder;

import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseViewHolder;

public class TradeFreshStockHolder extends BaseViewHolder<Object> {

    ImageView left_reduce_num;
    ImageView right_plus_num;
    public TradeFreshStockHolder(ViewGroup itemView) {
        super(itemView, R.layout.trade_holder_fresh_item);
        left_reduce_num = $(R.id.left_reduce_num);
        right_plus_num = $(R.id.right_plus_num);
        left_reduce_num.setColorFilter(Color.RED);
        right_plus_num.setColorFilter(Color.RED);
    }
}
