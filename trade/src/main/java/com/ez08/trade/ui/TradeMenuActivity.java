package com.ez08.trade.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.ui.user.TradeLoginFragment;

public class TradeMenuActivity extends BaseActivity {
    FragmentManager fragmentManager;
    RelativeLayout container;
    TextView pageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_menu);
        container = findViewById(R.id.container);
        pageName = findViewById(R.id.page_name);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TradeMenuFragment fragment = TradeMenuFragment.newInstance();
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
