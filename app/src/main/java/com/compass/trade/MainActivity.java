package com.compass.trade;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ez08.trade.tools.ActivityCallback;
import com.ez08.trade.ui.TradeMenuFragment;
import com.ez08.trade.ui.user.TradeLoginFragment;

public class MainActivity extends AppCompatActivity implements ActivityCallback {


    FragmentManager fragmentManager;
    RelativeLayout container;
    TextView pageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);
        pageName = findViewById(R.id.page_name);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TradeLoginFragment fragment = TradeLoginFragment.newInstance();
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();
        }

    @Override
    public void replace() {
        pageName.setText("交易");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TradeMenuFragment fragment = TradeMenuFragment.newInstance();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

}
