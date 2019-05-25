package com.ez08.trade.ui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.Client;
import com.ez08.trade.net.ClientHelper;
import com.ez08.trade.net.ConnectListener;
import com.ez08.trade.net.KeyExchangeRequest;
import com.ez08.trade.net.LoginRequest;
import com.ez08.trade.net.NativeTools;
import com.ez08.trade.net.Response;
import com.ez08.trade.net.ResponseCallback;
import com.ez08.trade.net.VerityPicRequest;
import com.ez08.trade.net.YCBizClient;
import com.ez08.trade.net.YCRequest;
import com.ez08.trade.net.YCSocketClient;
import com.ez08.trade.tools.ActivityCallback;
import com.ez08.trade.tools.DialogUtils;
import com.ez08.trade.tools.SharedPreferencesHelper;
import com.ez08.trade.ui.BaseFragment;
import com.ez08.trade.ui.TradeMenuActivity;
import com.ez08.trade.ui.view.BlueUnderLineClickableSpan;
import com.ez08.trade.user.TradeUser;
import com.ez08.trade.user.UserHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static android.util.Base64.DEFAULT;

public class TradeLoginFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = "TradeLoginFragment";
    TextView registerBtn;
    Button loginBtn;
    String host = Constant.SERVER_IP;
    ImageView verityImageView;
    ImageView pickAccount;
    TextView account;
    AppCompatCheckBox checkBox;
    LinearLayout accountLayout;

    EditText usernameEdit;
    EditText passwordEdit;
    EditText checkEdit;

    boolean isCanSaved;
    String accountValue;
    final String[] items = new String[]{"资金账户", "深A", "沪Ａ", "深Ｂ", "沪Ｂ", "沪港通", "股转Ａ", "股转Ｂ", "开放式基金", "深港通"};
    final String[] itemsCode = new String[]{"Z", "0", "1", "2", "3", "5", "6", "7", "J", "S"};

    String defaultItem = "Z";

    SharedPreferencesHelper sharedPreferencesHelper;

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
        verityImageView = rootView.findViewById(R.id.safe_code_iv);
        pickAccount = rootView.findViewById(R.id.account_type_nav);
        pickAccount.setOnClickListener(this);
        account = rootView.findViewById(R.id.account_type);
        checkBox = rootView.findViewById(R.id.login_auto_check);
        usernameEdit = rootView.findViewById(R.id.username_edit);
        passwordEdit = rootView.findViewById(R.id.password_edit);
        checkEdit = rootView.findViewById(R.id.check_code);
        verityImageView.setOnClickListener(this);
        accountLayout = rootView.findViewById(R.id.account_layout);
        accountLayout.setOnClickListener(this);

        SpannableStringBuilder builder = new SpannableStringBuilder(getString(R.string.trade_register_tips));
        builder.setSpan(new BlueUnderLineClickableSpan() {
            @Override
            public void onClick(View widget) {
                Log.e("SpannableStringBuilder", "onClick");

            }
        }, builder.length() - 4, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        registerBtn.setText(builder);
        registerBtn.setMovementMethod(LinkMovementMethod.getInstance());

        sharedPreferencesHelper = new SharedPreferencesHelper(mContext, "login_var");
        isCanSaved = (boolean) sharedPreferencesHelper.getSharedPreference("isCanSaved", true);
        accountValue = (String) sharedPreferencesHelper.getSharedPreference("accountValue", "");

        checkBox.setChecked(isCanSaved);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCanSaved = isChecked;
                sharedPreferencesHelper.put("isCanSaved", isCanSaved);
            }
        });

        if (isCanSaved) {
            usernameEdit.setText(accountValue);
        }

        createVerityClientAndSend();
    }

    private void createBizClient() {
        YCBizClient bizClient = ClientHelper.get();
        bizClient.setOnConnectListener(new ConnectListener() {
            @Override
            public void connectSuccess(Client client) {
                sendHandShakePackage(client);
            }

            @Override
            public void connectFail(Client client) {
                dismissBusyDialog();
            }

            @Override
            public void connectLost(Client client) {
                dismissBusyDialog();
            }
        });
        bizClient.connect();
    }

    private void sendHandShakePackage(Client client) {
        KeyExchangeRequest request = new KeyExchangeRequest();
        request.setCallback(new ResponseCallback() {
            @Override
            public void callback(Client client, Response data) {
                if (data.isSucceed()) {
                    Log.e(TAG, data.getData());
                    setLoginPackage(client);
                } else {
                    dismissBusyDialog();
                }
            }
        });
        client.send(request);
    }

    private void setLoginPackage(Client client) {
        LoginRequest request = new LoginRequest(defaultItem, "109000512", "123123", "1011", reserve);
        request.setCallback(new ResponseCallback() {
            @Override
            public void callback(Client client, Response data) {
                try {
                    if (data.isSucceed()) {
                        Log.e(TAG, data.getData());
                        JSONObject jsonObject = new JSONObject(data.getData());
                        int succ = jsonObject.getInt("bLoginSucc");
                        if (succ == 0) {
                            String msg = jsonObject.getString("szErrMsg");
                            Log.e(TAG, msg);
                        } else {
                            List<TradeUser> list = new ArrayList<>();
                            String array = jsonObject.getString("items");
                            JSONArray jsonArray = new JSONArray(array);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject index = jsonArray.getJSONObject(i);
                                TradeUser user = new TradeUser();
                                user.market = index.getString("sz_market");
                                user.name = index.getString("sz_name");
                                user.fundid = index.getString("n64_fundid");
                                user.custcert = index.getString("sz_custcert");
                                user.custid = index.getString("n64_custid");
                                user.secuid = index.getString("sz_secuid");
                                list.add(user);
                            }
                            UserHelper.setUserList(list);
                            startActivity(new Intent(mContext, TradeMenuActivity.class));
                            ((Activity) mContext).finish();
                        }

                    } else {
                        JSONObject jsonObject = new JSONObject(data.getData());
                        String msg = jsonObject.getString("szError");
                        DialogUtils.showSimpleDialog(mContext, msg);
                    }
                    dismissBusyDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
        client.send(request);
    }

    private void createVerityClientAndSend() { //获取验证码图片 type = 0  验证验证码 type = 1
        final YCSocketClient verityClient = new YCSocketClient(host, Constant.VERITY_SERVER_PORT);
        verityClient.setOnConnectListener(new ConnectListener() {
            @Override
            public void connectSuccess(Client client) {
                sendVerityPicRequest(client);
            }

            @Override
            public void connectFail(Client client) {

            }

            @Override
            public void connectLost(Client client) {

            }
        });
        verityClient.connect();
    }

    byte[] szId;
    byte[] code;
    String reserve = "0";

    private void sendVerityPicRequest(Client socketClient) {
        VerityPicRequest request = new VerityPicRequest();
        byte[] test = NativeTools.getVerifyCodePackFromJNI(20, 10);
        request.mData = test;
        request.setCallback(new ResponseCallback() {
            @Override
            public void callback(Client client, final Response data) {
                if (data.isSucceed()) {
                    Log.e("VerityPicRequest", data.getData());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(data.getData());
                                String pic = jsonObject.getString("bufPic");
                                byte[] picReal = Base64.decode(pic, DEFAULT);
                                szId = jsonObject.getString("szId").getBytes("GB2312");
                                reserve = jsonObject.getString("reserve");
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(picReal, 0, picReal.length);
                                verityImageView.setImageBitmap(decodedByte);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

        socketClient.send(request);
    }

    public void showPickDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        account.setText(items[which]);
                        defaultItem = itemsCode[which];
                    }
                });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_btn) {
            showBusyDialog();
            accountValue = usernameEdit.getText().toString();
            sharedPreferencesHelper.put("accountValue", accountValue);
            createBizClient();
        } else if (v.getId() == R.id.account_type_nav) {
            showPickDialog();
        } else if (v.getId() == R.id.safe_code_iv) {
            createVerityClientAndSend();
        }else if(v.getId() == R.id.account_layout){
            showPickDialog();
        }
    }

}
