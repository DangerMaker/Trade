package com.ez08.trade.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
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
                line.setBackgroundColor(ContextCompat.getColor(context,R.color.trade_gray));
                container.addView(line, lineParams);
            }

            View item = inflate(context, R.layout.trade_item_five, null);
            TextView nameView = (TextView) item.findViewById(R.id.name);
            nameView.setText(title[i]);
            TextView priceView = (TextView) item.findViewById(R.id.price);
            priceView.setText("430.22");
            TextView volumeView = (TextView) item.findViewById(R.id.volume);
            volumeView.setText("1293");
            container.addView(item, ll);
        }

    }

//    public void setFiveData(StockDetailEntity entity) {
//        int[] sell5Price = entity.getS5prices();
//        int[] buy5Price = entity.getB5prices();
//        long[] sell5Volume = entity.getS5volumes();
//        long[] buy5Volume = entity.getB5volumes();
//
//        int[] price = {sell5Price[4], sell5Price[3], sell5Price[2], sell5Price[1], sell5Price[0],
//                buy5Price[0], buy5Price[1], buy5Price[2], buy5Price[3], buy5Price[4]};
//
//        long[] volume = {sell5Volume[4], sell5Volume[3], sell5Volume[2], sell5Volume[1], sell5Volume[0],
//                buy5Volume[0], buy5Volume[1], buy5Volume[2], buy5Volume[3], buy5Volume[4]};
//
//        int j = 0;
//        for (int i = 0; i < container.getChildCount(); i++) {
//            if(container.getChildAt(i) instanceof LinearLayout){
//                LinearLayout child = (LinearLayout) container.getChildAt(i);
//                TextView priceView = (TextView)child.getChildAt(1);
//                priceView.setText(MathUtils.getFormatPrice(price[j],entity.getDecm()));
//                ((TextView)child.getChildAt(2)).setText(MathUtils.getMost4Character(volume[j]));
//
//                if (price[j] > entity.getLastclose()) {
//                    priceView.setTextColor(CompassApp.GLOBAL.RED);
//                } else if (price[j] < entity.getLastclose()) {
//                    priceView.setTextColor(CompassApp.GLOBAL.GREEN);
//                } else {
//                    priceView.setTextColor(CompassApp.GLOBAL.LIGHT_GRAY);
//                }
//
//                if(price[j] == 0){
//                    priceView.setText(" - - ");
//                    priceView.setTextColor(CompassApp.GLOBAL.LIGHT_GRAY);
//                }
//                j ++ ;
//            }
//        }
//    }

    @Override
    public void onClick(View v) {

    }
}
