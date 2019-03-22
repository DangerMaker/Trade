package com.ez08.trade.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseActivity;

public class TradeChangePwdActivity extends BaseActivity implements View.OnClickListener {

    TradePwdPopupWindows popupWindows;
    LinearLayout selectPwdType;
    ImageView backBtn;
    TextView titleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_change_pwd);

        titleView = findViewById(R.id.title);
        titleView.setText("修改密码");
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        selectPwdType = findViewById(R.id.select_type);
        selectPwdType.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.select_type){
            if(popupWindows == null){
                popupWindows = new TradePwdPopupWindows(this);
            }
            popupWindows.showAsDropDown(selectPwdType);
        }else if(v.getId() == R.id.img_back){
            finish();
        }
    }
}
