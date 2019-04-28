package com.ez08.trade.ui.user;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.net.Client;
import com.ez08.trade.tools.ActivityCallback;
import com.ez08.trade.ui.BaseFragment;
import com.ez08.trade.ui.view.BlueUnderLineClickableSpan;

public class TradeLoginFragment extends BaseFragment implements View.OnClickListener {

    TextView registerBtn;
    Button loginBtn;

    public static TradeLoginFragment newInstance() {
        Bundle args = new Bundle();
        TradeLoginFragment fragment = new TradeLoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.trade_fragment_login;
    }

    @Override
    protected void onCreateView(View rootView) {
        loginBtn = rootView.findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
        registerBtn = rootView.findViewById(R.id.register_btn);

        SpannableStringBuilder builder = new SpannableStringBuilder(getString(R.string.trade_register_tips));
        builder.setSpan(new BlueUnderLineClickableSpan() {
            @Override
            public void onClick(View widget) {
                Log.e("SpannableStringBuilder","onClick");

            }
        },builder.length() - 4,builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        registerBtn.setText(builder);
        registerBtn.setMovementMethod(LinkMovementMethod.getInstance());

        Client.connect();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_btn) {
            ((ActivityCallback) getActivity()).replace();
        }
    }
}
