package com.ez08.trade.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseActivity;

public class TradeRiskLevelActivity extends BaseActivity implements View.OnClickListener {

    ImageView backBtn;
    TextView titleView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_risk_level);

        titleView = findViewById(R.id.title);
        titleView.setText("客户风险等级查询");
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.img_back){
            finish();
        }
    }
}
