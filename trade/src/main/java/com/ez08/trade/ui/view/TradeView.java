package com.ez08.trade.ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.BizRequest;
import com.ez08.trade.net.Client;
import com.ez08.trade.net.ClientHelper;
import com.ez08.trade.net.Response;
import com.ez08.trade.net.ResponseCallback;
import com.ez08.trade.tools.DialogUtils;
import com.ez08.trade.tools.MathUtils;
import com.ez08.trade.tools.ScreenUtil;
import com.ez08.trade.ui.trade.OptionsDelegate;
import com.ez08.trade.ui.trade.TradeSearchWindows;
import com.ez08.trade.ui.trade.entity.TradeStockEntity;
import com.ez08.trade.user.UserHelper;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Set;

public class TradeView extends LinearLayout implements View.OnClickListener {

    Context context;
    ImageView plusPriceIv;
    ImageView reducePriceIv;
    ImageView plusNumIv;
    ImageView reduceNumIv;
    EditText inputCode;
    EditText editText1;
    EditText editText2;

    RelativeLayout layout1;
    RelativeLayout layout2;
    TextView full;
    TextView half;
    TextView one_three;
    TextView one_fourth;
    Button submitBtn;

    TextView maxText;

    TradeSearchWindows searchWindows;

    public TradeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.trade_view, this);
        plusPriceIv = findViewById(R.id.right_plus_price);
        plusNumIv = findViewById(R.id.right_plus_num);
        reducePriceIv = findViewById(R.id.left_reduce_price);
        reduceNumIv = findViewById(R.id.left_reduce_num);
        editText1 = findViewById(R.id.trade_entrust_price);
        editText2 = findViewById(R.id.trade_entrust_num);
        inputCode = findViewById(R.id.input_code);

        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        full = findViewById(R.id.full);
        half = findViewById(R.id.half);
        one_three = findViewById(R.id.one_three);
        one_fourth = findViewById(R.id.one_fourth);

        full.setOnClickListener(this);
        half.setOnClickListener(this);
        one_three.setOnClickListener(this);
        one_fourth.setOnClickListener(this);

        submitBtn = findViewById(R.id.submit);
        submitBtn.setOnClickListener(this);
        maxText = findViewById(R.id.available_num);

        change();

        inputCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (inputCode.getText().length() == 6) {
                    delegate.search(inputCode.getText().toString());
                }
            }
        });

        inputCode.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("onFocusChange", "" + hasFocus);
                if (hasFocus) {
                    inputCode.setText("");
                }
            }
        });

        plusPriceIv.setOnClickListener(this);
        plusNumIv.setOnClickListener(this);
        reducePriceIv.setOnClickListener(this);
        reduceNumIv.setOnClickListener(this);

        plusPriceIv.setColorFilter(Color.RED);
        plusNumIv.setColorFilter(Color.RED);
        reducePriceIv.setColorFilter(Color.RED);
        reduceNumIv.setColorFilter(Color.RED);

//        inputCode.post(new Runnable() {
//            @Override
//            public void run() {
//                Rect rect = new Rect();
//                inputCode.getGlobalVisibleRect(rect);
//
//                searchWindows = new TradeSearchWindows(context,ScreenUtil.getIntScreenWidth(context)
//                        ,ScreenUtil.getIntScreenHeight(context) - rect.bottom);
//            }
//        });


    }

    public void change() {
        if (type == 0) {
            editText1.setBackgroundResource(R.drawable.trade_input_white_bg);
            editText2.setBackgroundResource(R.drawable.trade_input_white_bg);
            plusPriceIv.setColorFilter(getResources().getColor(R.color.trade_red));
            plusNumIv.setColorFilter(getResources().getColor(R.color.trade_red));
            reducePriceIv.setColorFilter(getResources().getColor(R.color.trade_red));
            reduceNumIv.setColorFilter(getResources().getColor(R.color.trade_red));
            inputCode.setBackgroundResource(R.drawable.trade_input_bg);
            layout1.setBackgroundResource(R.drawable.trade_input_bg);
            layout2.setBackgroundResource(R.drawable.trade_input_bg);
            full.setBackgroundResource(R.drawable.trade_input_bg);
            half.setBackgroundResource(R.drawable.trade_input_bg);
            one_three.setBackgroundResource(R.drawable.trade_input_bg);
            one_fourth.setBackgroundResource(R.drawable.trade_input_bg);
            submitBtn.setBackgroundResource(R.drawable.trade_red_corner_full);
            submitBtn.setText("立即买入");
        } else {
            editText1.setBackgroundResource(R.drawable.trade_input_white_bg_blue);
            editText2.setBackgroundResource(R.drawable.trade_input_white_bg_blue);
            plusPriceIv.setColorFilter(getResources().getColor(R.color.trade_blue));
            plusNumIv.setColorFilter(getResources().getColor(R.color.trade_blue));
            reducePriceIv.setColorFilter(getResources().getColor(R.color.trade_blue));
            reduceNumIv.setColorFilter(getResources().getColor(R.color.trade_blue));
            inputCode.setBackgroundResource(R.drawable.trade_input_bg_blue);
            layout1.setBackgroundResource(R.drawable.trade_input_bg_blue);
            layout2.setBackgroundResource(R.drawable.trade_input_bg_blue);
            full.setBackgroundResource(R.drawable.trade_input_bg_blue);
            half.setBackgroundResource(R.drawable.trade_input_bg_blue);
            one_three.setBackgroundResource(R.drawable.trade_input_bg_blue);
            one_fourth.setBackgroundResource(R.drawable.trade_input_bg_blue);
            submitBtn.setBackgroundResource(R.drawable.trade_blue_corner_full);
            submitBtn.setText("立即卖出");
        }
    }

    int type = 0;

    public void setBorderColor(int type) {
        this.type = type;
        if (inputCode != null) {
            change();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    public void onClick(View v) {
        if (v == plusPriceIv) {
            String e1 = editText1.getText().toString();
            if (TextUtils.isEmpty(e1)) {
                return;
            }
            float t1 = Float.parseFloat(e1);
            t1 = t1 + 0.01f;
            editText1.setText(MathUtils.formatNum(t1, 4));
        } else if (v == reducePriceIv) {
            String e1 = editText1.getText().toString();
            if (TextUtils.isEmpty(e1)) {
                return;
            }
            float t1 = Float.parseFloat(e1);
            t1 = t1 - 0.01f;
            editText1.setText(MathUtils.formatNum(t1, 4));
        } else if (v == plusNumIv) {
            String e1 = editText2.getText().toString();
            if (TextUtils.isEmpty(e1)) {
                e1 = "0";
            }
            int t1 = Integer.parseInt(e1);
            t1 = t1 + 100;
            editText2.setText(t1 + "");
        } else if (v == reduceNumIv) {
            String e1 = editText2.getText().toString();
            if (TextUtils.isEmpty(e1)) {
                return;
            }
            int t1 = Integer.parseInt(e1);
            t1 = t1 - 100;
            if (t1 < 0) {
                t1 = 0;
            }
            editText2.setText(t1 + "");
        }else if(v == full){
            editText2.setText(MathUtils.save100(maxValue));
        }else if(v == half){
            editText2.setText(MathUtils.save100(maxValue/2));
        }else if(v == one_three){
            editText2.setText(MathUtils.save100(maxValue/3));
        }else if(v == one_fourth){
            editText2.setText(MathUtils.save100(maxValue/4));
        }else if(v == submitBtn){
            delegate.submit(entity.stkcode,editText1.getText().toString(),editText2.getText().toString());
        }
    }

    TradeStockEntity entity;
    int maxValue = 0;

    public void setStockEntity(TradeStockEntity entity) {
        this.entity = entity;
        inputCode.setText(entity.stkcode + "    " + entity.stkname);
        editText1.setText((MathUtils.format2Num(entity.fNewest)));
        delegate.getMax(entity.stkcode, editText1.getText().toString());

        this.requestFocus();
        this.requestFocusFromTouch();
    }

    public void setMax(String max){
        maxText.setText("最大可买 " + max + " 股");
//        editText2.setText(max);
        maxValue = Integer.parseInt(max);
    }


    OptionsDelegate delegate;

    public void setDelegate(OptionsDelegate delegate) {
        this.delegate = delegate;

    }
}
