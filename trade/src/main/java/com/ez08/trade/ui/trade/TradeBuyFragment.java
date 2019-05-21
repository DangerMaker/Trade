package com.ez08.trade.ui.trade;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.BizRequest;
import com.ez08.trade.net.Client;
import com.ez08.trade.net.ClientHelper;
import com.ez08.trade.net.QueryRequest;
import com.ez08.trade.net.Response;
import com.ez08.trade.net.ResponseCallback;
import com.ez08.trade.tools.DialogUtils;
import com.ez08.trade.tools.MathUtils;
import com.ez08.trade.tools.YiChuangUtils;
import com.ez08.trade.ui.BaseFragment;
import com.ez08.trade.ui.trade.entity.TradeStockEntity;
import com.ez08.trade.ui.view.FiveAndTenView;
import com.ez08.trade.ui.view.TradeView;
import com.ez08.trade.user.TradeUser;
import com.ez08.trade.user.UserHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TradeBuyFragment extends BaseFragment implements OptionsDelegate {

    public static TradeBuyFragment newInstance(int type) {
        Bundle args = new Bundle();
        TradeBuyFragment fragment = new TradeBuyFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    TradeView tradeView;
    FiveAndTenView fiveAndTenView;
    EditText editText;

    TextView newestPrice;
    TextView lastPrice;
    TextView upPrice;
    TextView downPrice;

    int type;
    String bsflag = "B";

    @Override
    protected int getLayoutResource() {
        return R.layout.trade_fragment_buy;
    }

    @Override
    protected void onCreateView(View rootView) {
        fiveAndTenView = rootView.findViewById(R.id.five_ten_view);
        fiveAndTenView.setOnFiveListener(new FiveAndTenView.OnFiveListener() {
            @Override
            public void OnFive(String value) {
                Log.e("TradeBuyFragment", value);
                editText.setText(value);
            }
        });

        editText = rootView.findViewById(R.id.trade_entrust_price);
        tradeView = rootView.findViewById(R.id.trade_view);

        type = getArguments().getInt("type");
        tradeView.setBorderColor(type);
        tradeView.setDelegate(this);

        newestPrice = rootView.findViewById(R.id.newest_price);
        lastPrice = rootView.findViewById(R.id.last_price);
        upPrice = rootView.findViewById(R.id.limit_up_price);
        downPrice = rootView.findViewById(R.id.limit_down_price);

        bsflag = type == 0 ? "B" : "S";
    }

    TradeStockEntity stockEntity;

    @Override
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
        request.setCallback(new ResponseCallback() {
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
                                getHQQuery(stockEntity);
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
        request.setCallback(new ResponseCallback() {
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
                                String max = var[0];
                                tradeView.setMax(max);
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
    public void submit(final String code, final String price, final String num) {
        TradeUser user = UserHelper.getUserByMarket(stockEntity.market);
        if (user == null) {
            return;
        }
        String option = type == 0 ? "买入" : "卖出";
        DialogUtils.showTwoButtonDialog(mContext, option + "交易确认", "确定" + option,
                "操作类型：" + option + "\n" +
                        "股票代码：" + stockEntity.stkcode + "  " + stockEntity.stkname + "\n" +
                        "委托价格：" + price + "\n" +
                        "委托数量：" + num + "\n" +
                        "委托方式：" + "限价委托" + "\n" +
                        "股东代码：" + user.secuid
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        post(code, price, num);
                    }
                });

    }

    private void post(String code, String price, String qty) {
        TradeUser user = UserHelper.getUserByMarket(stockEntity.market);
        if (user == null) {
            return;
        }

        String body = "FUN=410411&TBL_IN=market,secuid,fundid,stkcode,bsflag,price,qty,ordergroup," +
                "bankcode,creditid,creditflag,remark,targetseat,promiseno,risksno,autoflag," +
                "enddate,linkman,linkway,linkmarket,linksecuid,sorttype,mergematchcode,mergematchdate" +
//                "oldorderid,prodcode,pricetype,blackflag,dzsaletype,risksignsno" +
                ";" +
                stockEntity.market + "," +
                user.secuid + "," +
                user.fundid + "," +
                code + "," +
                bsflag + "," +
                price + "," +
                qty + "," +
                "0" +
//                "," + "," + "," + "," + ","
                "," + "," + "," + "," + "," +
                "," + "," + "," + "," + "," + "," + "," + "," + "," + "," + "," +
                ";";

        BizRequest request = new BizRequest();
        request.setBody(body);
        request.setCallback(new ResponseCallback() {
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
                                DialogUtils.showSimpleDialog(mContext, "委托成功" + "\n" +
                                        "委托序号：" + var[0] + "\n" +
                                        "合同序号：" + var[1] + "\n" +
                                        "委托批号：" + var[2]
                                );
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

    private void getHQQuery(final TradeStockEntity entity) {
        QueryRequest request = new QueryRequest();
        request.setBody(entity.market, entity.stkcode);
        request.setCallback(new ResponseCallback() {
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

                        tradeView.setStockEntity(stockEntity);
                        fiveAndTenView.setLevel1(stockEntity);
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


    private void setIndex(TradeStockEntity entity) {
        newestPrice.setText(MathUtils.format2Num(entity.fNewest));
        newestPrice.setTextColor(entity.fNewest > entity.fOpen ? setTextColor(R.color.trade_red) : setTextColor(R.color.trade_green));
        lastPrice.setText(MathUtils.format2Num(entity.fLastClose));
        upPrice.setText(MathUtils.format2Num(entity.fLastClose * 1.1f));
        upPrice.setTextColor(setTextColor(R.color.trade_red));
        downPrice.setText(MathUtils.format2Num(entity.fLastClose * 0.9f));
        downPrice.setTextColor(setTextColor(R.color.trade_green));
    }

}
