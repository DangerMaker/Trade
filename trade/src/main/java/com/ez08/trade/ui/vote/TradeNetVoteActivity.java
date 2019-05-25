package com.ez08.trade.ui.vote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseActivity;

public class TradeNetVoteActivity extends BaseActivity implements View.OnClickListener {

    ImageView backBtn;
    TextView titleView;

    RelativeLayout title1;
    RelativeLayout title2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_vote_menu);
        titleView = findViewById(R.id.title);
        titleView.setText("网络投票");
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        title1 = findViewById(R.id.title1);
        title2 = findViewById(R.id.title2);
        title1.setOnClickListener(this);
        title2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == backBtn){
            finish();
        }else if(v == title1){
            startActivity(new Intent(this,TradeVoteListActivity.class));
        }else if(v == title2){

        }
    }
}
