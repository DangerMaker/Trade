package com.ez08.trade.ui.other;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.request.BizRequest;
import com.ez08.trade.net.Client;
import com.ez08.trade.net.ClientHelper;
import com.ez08.trade.net.request.QueryRequest;
import com.ez08.trade.net.Response;
import com.ez08.trade.net.Callback;
import com.ez08.trade.tools.CommonUtils;
import com.ez08.trade.tools.DialogUtils;
import com.ez08.trade.tools.MathUtils;
import com.ez08.trade.tools.YiChuangUtils;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.trade.entity.TradeStockEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TradeNewOrderActivity extends BaseActivity implements View.OnClickListener {
    ImageView backBtn;
    TextView titleView;

    TextView newestPrice;
    TextView lastPrice;
    TextView upPrice;
    TextView downPrice;
    EditText code;
    LinearLayout typeLayout;
    LinearLayout quoteLayout;
    TextView buyDict;
    TextView quoteWay;
    EditText price;
    EditText number;
    Button submit;

    TradeStockEntity stockEntity;
    String[] bsSelect = new String[]{"买入", "卖出"};

    OrderEntity orderEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_new_order);

        titleView = findViewById(R.id.title);
        titleView.setText("新建预埋单");
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        newestPrice = findViewById(R.id.newest_price);
        lastPrice = findViewById(R.id.last_price);
        upPrice = findViewById(R.id.limit_up_price);
        downPrice = findViewById(R.id.limit_down_price);
        code = findViewById(R.id.stock_code);
        typeLayout = findViewById(R.id.type_layout);
        quoteLayout = findViewById(R.id.quote_layout);
        buyDict = findViewById(R.id.buy_dict);
        quoteWay = findViewById(R.id.quote_way);
        price = findViewById(R.id.price);
        number = findViewById(R.id.num);
        submit = findViewById(R.id.submit);
        typeLayout.setOnClickListener(this);
        quoteLayout.setOnClickListener(this);
        submit.setOnClickListener(this);

        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (code.getText().length() == 6) {
                    search(code.getText().toString());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == backBtn) {
            finish();
        } else if (v == typeLayout) {
            DialogUtils.showSelectDialog(context, bsSelect, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    buyDict.setText(bsSelect[which]);
                }
            });
        } else if (v == quoteLayout) {
            if (stockEntity == null || stockEntity.market == null) {
                CommonUtils.show(context, "请输入股票代码");
                return;
            }
            final String[] select;
            if (stockEntity.market.equals("0")) {
                select = YiChuangUtils.szQuoteType;
            } else if (stockEntity.market.equals("1")) {
                select = YiChuangUtils.shQuoteType;
            } else {
                select = new String[]{};
            }
            DialogUtils.showSelectDialog(context, select, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    quoteWay.setText(select[which]);
                }
            });
        } else if (v == submit) {
            orderEntity = new OrderEntity();
            orderEntity.market = stockEntity.market;
            orderEntity.name = stockEntity.stkname;
            orderEntity.code = stockEntity.stkcode;
            String temp = "B";
            if(buyDict.getText().toString().equals("卖出")){
                temp = "S";
            }
            orderEntity.bsflag = YiChuangUtils.getTagByQuoteName(temp,quoteWay.getText().toString());
            orderEntity.dict = buyDict.getText().toString();
            orderEntity.price = price.getText().toString();
            orderEntity.qty = number.getText().toString();

            Intent intent = new Intent();
            intent.putExtra("entity", orderEntity);
            setResult(2, intent);
            finish();
        }
    }

    private void setIndex(TradeStockEntity entity) {
        newestPrice.setText(MathUtils.format2Num(entity.fNewest));
        newestPrice.setTextColor(entity.fNewest > entity.fOpen ? setTextColor(R.color.trade_red) : setTextColor(R.color.trade_green));
        lastPrice.setText(MathUtils.format2Num(entity.fLastClose));
        upPrice.setText(MathUtils.format2Num(entity.fLastClose * 1.1f));
        upPrice.setTextColor(setTextColor(R.color.trade_red));
        downPrice.setText(MathUtils.format2Num(entity.fLastClose * 0.9f));
        downPrice.setTextColor(setTextColor(R.color.trade_green));
    }

    public void search(String code) {
        String body = "FUN=410203&TBL_IN=market,stklevel,stkcode,poststr,rowcount,stktype;" +
                "" + "," +
                "" + "," +
                code + "," +
                "" + "," +
                "" + "," +
                ";";

        BizRequest request = new BizRequest();
        request.setBody(body);
        request.setCallback(new Callback() {
            @Override
            public void callback(Client client, Response data) {
                dismissBusyDialog();
                try {
                    if (data.isSucceed()) {
                        Log.e(TAG, data.getData());
                        JSONObject jsonObject = new JSONObject(data.getData());
                        String content = jsonObject.getString("content");
                        Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + content);
                        Set<String> pn = uri.getQueryParameterNames();
                        for (Iterator it = pn.iterator(); it.hasNext(); ) {
                            String key = it.next().toString();
                            if ("TBL_OUT".equals(key)) {
                                stockEntity = new TradeStockEntity();
                                String out = uri.getQueryParameter(key);
                                String[] split = out.split(";");
                                String[] var = split[1].split(",");
                                if (var == null) {
                                    DialogUtils.showSimpleDialog(context, "股票输入有误");
                                    return;
                                }
                                stockEntity.market = var[0];
                                stockEntity.stkname = var[2];
                                stockEntity.stkcode = var[3];
                                stockEntity.stopflag = var[8];
                                stockEntity.maxqty = var[11];
                                stockEntity.minqty = var[12];
                                getHQQuery(stockEntity);
                            }
                        }

                    } else {
                        JSONObject jsonObject = new JSONObject(data.getData());
                        String msg = jsonObject.getString("szError");
                        DialogUtils.showSimpleDialog(context, msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

        showBusyDialog();
        ClientHelper.get().send(request);

    }

    private void getHQQuery(final TradeStockEntity entity) {
        QueryRequest request = new QueryRequest();
        request.setBody(entity.market, entity.stkcode);
        request.setCallback(new Callback() {
            @Override
            public void callback(Client client, Response data) {
                dismissBusyDialog();
                try {
                    if (data.isSucceed()) {
                        Log.e(TAG, data.getData());
                        JSONObject jsonObject = new JSONObject(data.getData());
                        entity.fOpen = jsonObject.getDouble("fOpen");
                        entity.fLastClose = jsonObject.getDouble("fLastClose");
                        entity.fHigh = jsonObject.getDouble("fHigh");
                        entity.fLow = jsonObject.getDouble("fLow");
                        entity.fNewest = jsonObject.getDouble("fNewest");

                        JSONArray jsonArray = jsonObject.getJSONArray("ask");
                        List<TradeStockEntity.Dang> list1 = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            TradeStockEntity.Dang dang = new TradeStockEntity.Dang();
                            dang.fOrder = jsonArray.getJSONObject(i).getInt("fOrder");
                            dang.fPrice = jsonArray.getJSONObject(i).getDouble("fPrice");
                            list1.add(dang);
                        }
                        entity.ask = list1;

                        JSONArray jsonArray1 = jsonObject.getJSONArray("bid");
                        List<TradeStockEntity.Dang> list2 = new ArrayList<>();
                        for (int i = 0; i < jsonArray1.length(); i++) {
                            TradeStockEntity.Dang dang = new TradeStockEntity.Dang();
                            dang.fOrder = jsonArray1.getJSONObject(i).getInt("fOrder");
                            dang.fPrice = jsonArray1.getJSONObject(i).getDouble("fPrice");
                            list2.add(dang);
                        }
                        entity.bid = list2;

                        code.setText(stockEntity.stkcode + " " + stockEntity.stkname);
                        setIndex(stockEntity);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        showBusyDialog();
        ClientHelper.get().send(request);
    }
}
