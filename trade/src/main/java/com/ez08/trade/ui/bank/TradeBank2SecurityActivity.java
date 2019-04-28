package com.ez08.trade.ui.bank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseActivity;

public class TradeBank2SecurityActivity extends BaseActivity implements View.OnClickListener {

    ImageView img_back;
    TextView titleView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_bank2_security);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        titleView = findViewById(R.id.title);
        titleView.setText("银行转证券");
    }

    @Override
    public void onClick(View v) {
        if(v == img_back){
            finish();
        }

    }
}
