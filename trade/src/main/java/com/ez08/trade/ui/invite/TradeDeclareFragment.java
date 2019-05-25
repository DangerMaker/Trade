package com.ez08.trade.ui.invite;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.request.BizRequest;
import com.ez08.trade.net.Client;
import com.ez08.trade.net.ClientHelper;
import com.ez08.trade.net.Response;
import com.ez08.trade.net.Callback;
import com.ez08.trade.tools.DialogUtils;
import com.ez08.trade.ui.BaseFragment;
import com.ez08.trade.ui.trade.entity.TradeStockEntity;
import com.ez08.trade.ui.view.AdjustEditText;
import com.ez08.trade.user.TradeUser;
import com.ez08.trade.user.UserHelper;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Set;

public class TradeDeclareFragment extends BaseFragment implements View.OnClickListener {

    TextView name;
    TextView max;
    TextView maxTitle;
    TextView numTitle;

    EditText buyMan;
    EditText code;
    AdjustEditText num;

    Button submit;

    int type = 0;
    TradeStockEntity stockEntity;
    String bsflag = "0Y";
    String titles = "要约申报";


    public static TradeDeclareFragment newInstance(int type) {
        Bundle args = new Bundle();
        TradeDeclareFragment fragment = new TradeDeclareFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.trade_fragment_invited_declare;
    }

    @Override
    protected void onCreateView(View rootView) {
        type = getArguments().getInt("type");
        name = rootView.findViewById(R.id.stock_name);
        code = rootView.findViewById(R.id.stock_code);
        max = rootView.findViewById(R.id.max);
        maxTitle = rootView.findViewById(R.id.max_title);
        buyMan = rootView.findViewById(R.id.buy_man);
        numTitle = rootView.findViewById(R.id.num_title);
        num = rootView.findViewById(R.id.num);
        submit = rootView.findViewById(R.id.submit);
        submit.setOnClickListener(this);

        if (type == 0) {
            maxTitle.setText("可用股数");
            numTitle.setText("预售数量");
        } else {
            maxTitle.setText("解除上限");
            numTitle.setText("解除数量");
        }

        bsflag = type == 0 ? "0Y" : "0E";
        titles = type == 0 ? "要约申报" : "要约解除";

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
                                stockEntity.market = var[0];
                                stockEntity.stkname = var[2];
                                stockEntity.stkcode = var[3];
                                stockEntity.stopflag = var[8];
                                stockEntity.maxqty = var[11];
                                stockEntity.minqty = var[12];
                                stockEntity.fixprice = var[21];

                                name.setText(stockEntity.stkname);
                                getMax(stockEntity.stkcode, stockEntity.fixprice);
                            }
                        }

                    } else {
                        JSONObject jsonObject = new JSONObject(data.getData());
                        String msg = jsonObject.getString("szError");
                        DialogUtils.showSimpleDialog(mContext, msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

        showBusyDialog();
        ClientHelper.get().send(request);

    }

    public void getMax(String code, String price) {
        TradeUser user = UserHelper.getUserByMarket(stockEntity.market);
        if (user == null) {
            return;
        }

        String body = "FUN=410410&TBL_IN=market,secuid,fundid,stkcode,bsflag,price,bankcode,hiqtyflag,creditid,creditflag,linkmarket,linksecuid,sorttype,dzsaletype,prodcode;" +
                stockEntity.market + "," +
                user.secuid + "," +
                user.fundid + "," +
                code + "," +
                bsflag + "," +
                price + "," + "," + "," + "," + "," + "," + "," + "," + "," +
                ";";

        BizRequest request = new BizRequest();
        request.setBody(body);
        request.setCallback(new Callback() {
            @Override
            public void callback(Client client, Response data) {
                dismissBusyDialog();
                Log.e(TAG, data.getData());
                try {
                    if (data.isSucceed()) {
                        JSONObject jsonObject = new JSONObject(data.getData());
                        String content = jsonObject.getString("content");
                        Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + content);
                        Set<String> pn = uri.getQueryParameterNames();
                        for (Iterator it = pn.iterator(); it.hasNext(); ) {
                            String key = it.next().toString();
                            if ("TBL_OUT".equals(key)) {
                                String out = uri.getQueryParameter(key);
                                String[] split = out.split(";");
                                String[] var = split[1].split(",");
                                String maxValue = var[0];
                                max.setText(maxValue);
                                num.setText(maxValue);
                            }
                        }

                    } else {
                        JSONObject jsonObject = new JSONObject(data.getData());
                        String msg = jsonObject.getString("szError");
                        DialogUtils.showSimpleDialog(mContext, msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

        showBusyDialog();
        ClientHelper.get().send(request);
    }

    public void submit() {
        TradeUser user = UserHelper.getUserByMarket(stockEntity.market);
        if (user == null) {
            return;
        }

        post(stockEntity.stkcode, stockEntity.fixprice, num.getText());
    }

    private void post(String code, String price, String qty) {
        TradeUser user = UserHelper.getUserByMarket(stockEntity.market);
        if (user == null) {
            return;
        }

        String body = "FUN=410411&TBL_IN=fundid,market,secuid,stkcode,qty,price,remark,bsflag,ordergroup,bankcode;" +
                user.fundid + "," +
                stockEntity.market + "," +
                user.secuid + "," +
                code + "," +
                qty + "," +
                price + "," +
                buyMan.getText().toString() + "," +
                bsflag + "," +
                "0" + "," +
                ";";

        BizRequest request = new BizRequest();
        request.setBody(body);
        request.setCallback(new Callback() {
            @Override
            public void callback(Client client, Response data) {
                dismissBusyDialog();
                Log.e(TAG, data.getData());
                try {
                    if (data.isSucceed()) {
                        JSONObject jsonObject = new JSONObject(data.getData());
                        String content = jsonObject.getString("content");
                        Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + content);
                        Set<String> pn = uri.getQueryParameterNames();
                        for (Iterator it = pn.iterator(); it.hasNext(); ) {
                            String key = it.next().toString();
                            if ("TBL_OUT".equals(key)) {
                                String out = uri.getQueryParameter(key);
                                String[] split = out.split(";");
                                String[] var = split[1].split(",");
                                DialogUtils.showSimpleDialog(mContext, titles + "已提交，合同号是：" + var[1]);
                            }
                        }

                    } else {
                        JSONObject jsonObject = new JSONObject(data.getData());
                        String msg = jsonObject.getString("szError");
                        DialogUtils.showSimpleDialog(mContext, msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

        showBusyDialog();
        ClientHelper.get().send(request);
    }

    @Override
    public void onClick(View v) {
        if (v == submit) {
            submit();
        }
    }
}
