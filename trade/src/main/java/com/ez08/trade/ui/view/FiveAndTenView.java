package com.ez08.trade.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.MathUtils;

public class FiveAndTenView extends RelativeLayout implements View.OnClickListener {

    LinearLayout container;
    String[] title = {"卖5", "卖4", "卖3", "卖2", "卖1",
            "买1", "买2", "买3", "买4", "买5"};

    float price = 3.21f;

    public FiveAndTenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.trade_view_five, this);
        setOnClickListener(this);
        container = findViewById(R.id.container);

        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        ll.weight = 1;

        LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);

        for (int i = 0; i < title.length; i++) {
            if (i == 5) {
                View line = new View(context);
                line.setBackgroundColor(ContextCompat.getColor(context, R.color.trade_gray));
                container.addView(line, lineParams);
            }

            View item = inflate(context, R.layout.trade_item_five, null);
            TextView nameView = (TextView) item.findViewById(R.id.name);
            price = price - 0.01f;
            item.setTag(MathUtils.formatNum(price,4));
            item.setOnClickListener(this);
            nameView.setText(title[i]);
            SingleLineAutoResizeTextView priceView = (SingleLineAutoResizeTextView) item.findViewById(R.id.price);
            priceView.setTextContent(MathUtils.formatNum(price,4));
            SingleLineAutoResizeTextView volumeView = (SingleLineAutoResizeTextView) item.findViewById(R.id.volume);
            volumeView.setTextContent("10.28万");
            container.addView(item, ll);
        }
    }


    @Override
    public void onClick(View v) {
        onFiveListener.OnFive(v.getTag().toString());
    }


    OnFiveListener onFiveListener;
    public void setOnFiveListener(OnFiveListener listener){
        onFiveListener = listener;
    }


    public interface OnFiveListener{
        void OnFive(String value);
    }
}
