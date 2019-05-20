//package com.ez08.trade.ui.bank;
//
//import android.content.DialogInterface;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.ez08.trade.Constant;
//import com.ez08.trade.R;
//import com.ez08.trade.net.BizRequest;
//import com.ez08.trade.net.Client;
//import com.ez08.trade.net.ClientHelper;
//import com.ez08.trade.net.Response;
//import com.ez08.trade.net.ResponseCallback;
//import com.ez08.trade.tools.DialogUtils;
//import com.ez08.trade.tools.YiChuangUtils;
//import com.ez08.trade.ui.BaseActivity;
//import com.ez08.trade.user.UserHelper;
//
//import org.json.JSONObject;
//
//import java.util.Iterator;
//import java.util.Set;
//
//public class TradeFundLeftActivity extends BaseActivity implements View.OnClickListener {
//
//    ImageView img_back;
//    TextView titleView;
//
//    TextView bankName;
//    TextView RMB;
//    LinearLayout bankLayout;
//    LinearLayout rmbLayout;
//    TextView funds;
//    TextView account;
//    Button button;
//
//    LinearLayout result;
//    String moneyTypeValue = "0";
//    String bankCodeValue;
//    String yue = "";
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.trade_activity_funds_left);
//        img_back = findViewById(R.id.img_back);
//        img_back.setOnClickListener(this);
//        titleView = findViewById(R.id.title);
//        titleView.setText("资金余额");
//        funds = findViewById(R.id.funds);
//        account = findViewById(R.id.funds2);
//        account.setText(UserHelper.getUser().fundid);
//        result = findViewById(R.id.result);
//        result.setVisibility(View.GONE);
//        button = findViewById(R.id.submit);
//        button.setOnClickListener(this);
//
//        bankName = findViewById(R.id.bank_name);
//        RMB = findViewById(R.id.rmb);
//        bankLayout = findViewById(R.id.bank_layout);
//        rmbLayout = findViewById(R.id.rmb_layout);
//        bankLayout.setOnClickListener(this);
//        rmbLayout.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        if(v == img_back){
//            finish();
//        }else if(v == button){
//            post();
//        }else if(v == bankLayout){
//            DialogUtils.showSelectDialog(context, bankNames.toArray(new String[0]), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    bankNameValue = bankNames.get(which);
//                    bankCodeValue = getBankCodeByName(bankNameValue);
//                    moneyTypes = getBankMoneyTypes(bankNameValue);
//                    moneyTypeValue = getBankMoneyTypes(bankNameValue).get(0);
//
//                    bankName.setText(bankNameValue);
//                    RMB.setText(YiChuangUtils.getMoneyType(moneyTypeValue));
//
//                    getInfo();
//                }
//            });
//        }else if(v == rmbLayout){
//            String[] temp = new String[moneyTypes.size()];
//            for (int i = 0; i < moneyTypes.size(); i++) {
//                temp[i] = YiChuangUtils.getMoneyType(moneyTypes.get(i));
//            }
//
//            DialogUtils.showSelectDialog(context, temp, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    moneyTypeValue = moneyTypes.get(which);
//                    RMB.setText(YiChuangUtils.getMoneyType(moneyTypeValue));
//
//                    getInfo();
//                }
//            });
//        }
//
//    }
//
//
//
//    public void post() {
//        String body = "FUN=410606&TBL_IN=fundid,moneytype,fundpwd,bankcode,bankpwd;" +
//                UserHelper.getUser().fundid + "," +
//                moneyTypeValue + "," +
//                "" + "," +
//                bankCodeValue + "," +
//                "" +
//                ";";
//
//        BizRequest request = new BizRequest();
//        request.setBody(body);
//        request.setCallback(new ResponseCallback() {
//            @Override
//            public void callback(Client client, Response data) {
//                dismissBusyDialog();
//                if (data.isSucceed()) {
//                    Log.e(TAG, data.getData());
//                    try {
//                        JSONObject jsonObject = new JSONObject(data.getData());
//                        String content = jsonObject.getString("content");
//                        Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + content);
//                        Set<String> pn = uri.getQueryParameterNames();
//                        for (Iterator it = pn.iterator(); it.hasNext(); ) {
//                            String key = it.next().toString();
//                            if ("TBL_OUT".equals(key)) {
//                                String out = uri.getQueryParameter(key);
//                                String[] split = out.split(";");
//                                String[] var = split[1].split(",");
//                                yue = var[3];
//                                funds.setText(yue + "元");
//                            }
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//
//                    }
//                }
//
//            }
//        });
//
//        showBusyDialog();
//        ClientHelper.get().send(request);
//    }
//}
