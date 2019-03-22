package com.ez08.trade.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseActivity;

public class TradeChangeMsgActivity extends BaseActivity implements View.OnClickListener {

    ImageView backBtn;
    TextView titleView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_change_msg);

        titleView = findViewById(R.id.title);
        titleView.setText("修改资料");
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
