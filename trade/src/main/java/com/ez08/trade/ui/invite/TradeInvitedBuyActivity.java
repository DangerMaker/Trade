package com.ez08.trade.ui.invite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.trade.TradeCancelFragment;
import com.ez08.trade.ui.trade.TradeOptionFragment;
import com.ez08.trade.ui.view.customtab.EasyFragment;
import com.ez08.trade.ui.view.customtab.FragmentAdapter;
import com.ez08.trade.ui.view.customtab.SlidingTabLayout;

import java.util.ArrayList;

public class TradeInvitedBuyActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private FragmentAdapter mAdapter;
    private ArrayList<EasyFragment> mFragmentList = new ArrayList<>();
    private int mIndex = 0;
    SlidingTabLayout sliding_tabs;
    ImageView backBtn;

    TradeOptionFragment fragment1;
    TradeOptionFragment fragment2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_invited);
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        mViewPager = (ViewPager) findViewById(R.id.info_tab_pager);
        sliding_tabs = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        mFragmentList = new ArrayList<>();
        if (savedInstanceState != null) {
            if (getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.info_tab_pager + ":" + 0) != null)
                fragment1 = (TradeOptionFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.info_tab_pager + ":" + 0);
            if (getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.info_tab_pager + ":" + 1) != null)
                fragment2 = (TradeOptionFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.info_tab_pager + ":" + 1);
        } else {
            fragment1 = TradeOptionFragment.newInstance();
            fragment2 = TradeOptionFragment.newInstance();
        }

        mFragmentList.clear();
        mFragmentList.add(new EasyFragment(fragment1, "要约申报"));
        mFragmentList.add(new EasyFragment(fragment2, "要约解除"));

        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.addOnPageChangeListener(new PageChangeListener());
        mViewPager.setAdapter(mAdapter);
        sliding_tabs.setViewPager(mViewPager);
        mViewPager.setCurrentItem(mIndex);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            finish();
        }
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            mIndex = arg0;
        }
    }

}