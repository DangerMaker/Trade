package com.ez08.trade.ui.user;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.ez08.trade.ui.BaseFragment;
import com.ez08.trade.ui.view.BlueUnderLineClickableSpan;
import com.ez08.trade.user.TradeUser;
import com.ez08.trade.user.UserHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static android.util.Base64.DEFAULT;

public class TradeLoginFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = "TradeLoginFragment";
    TextView registerBtn;
    Button loginBtn;
    String host = Constant.SERVER_IP;
    ImageView verityImageView;

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

        SpannableStringBuilder builder = new SpannableStringBuilder(getString(R.string.trade_register_tips));
        builder.setSpan(new BlueUnderLineClickableSpan() {
            @Override
            public void onClick(View widget) {
                Log.e("SpannableStringBuilder", "onClick");

            }
        }, builder.length() - 4, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        registerBtn.setText(builder);
        registerBtn.setMovementMethod(LinkMovementMethod.getInstance());

        createVerityClientAndSend(0);
        createBizClient();
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

            }

            @Override
            public void connectLost(Client client) {

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
                }
            }
        });
        client.send(request);
    }

    private void setLoginPackage(Client client) {
        LoginRequest request = new LoginRequest("Z", "109000512", "123123", "1011", "123");
        request.setCallback(new ResponseCallback() {
            @Override
            public void callback(Client client, Response data) {
                if (data.isSucceed()) {
                    Log.e(TAG, data.getData());
                    try {
                        JSONObject jsonObject = new JSONObject(data.getData());
                        int succ = jsonObject.getInt("bLoginSucc");
                        if (succ == 0) {
                            String msg = jsonObject.getString("szErrMsg");
                            Log.e(TAG, msg);
                        } else {
                            String array = jsonObject.getString("items");
                            JSONArray jsonArray = new JSONArray(array);
                            JSONObject index1 = jsonArray.getJSONObject(0);
                            UserHelper.init(index1.getString("sz_name"),
                                    index1.getString("sz_market"),
                                    index1.getString("n64_fundid"),
                                    index1.getString("sz_custcert"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        client.send(request);
    }

    private void createVerityClientAndSend(final int type) { //获取验证码图片 type = 0  验证验证码 type = 1
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
    int reserve = 0;
    int checkId = 0;

    private void sendVerityPicRequest(Client socketClient) {
        VerityPicRequest request = new VerityPicRequest();
        byte[] test = NativeTools.getVerifyCodePackFromJNI(20, 10);
        request.mData = test;
        request.setCallback(new ResponseCallback() {
            @Override
            public void callback(Client client, final Response data) {
                if (data.isSucceed()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(data.getData());
                                String pic = jsonObject.getString("bufPic");
                                byte[] picReal = Base64.decode(pic, DEFAULT);
                                szId = jsonObject.getString("szId").getBytes("GB2312");
                                reserve = jsonObject.getInt("reserve");
                                code = "2219".getBytes("GB2312");

                                Log.e("VerityPicRequest", data.getData());
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

    private void sendVerityRequest(Client socketClient) {
//        YCRequest request = new YCRequest(NativeTools.VERIFICATION_CODE_PID_CHECK_CODE);
//        byte[] test = NativeTools.getVerifyCodeCheckFromJNI(szId, code,checkId,reserve);
//        request.mData = test;
//        request.setCallback(new ResponseCallback() {
//            @Override
//            public void callback(Client client, final Response data) {
//                if (data.isSucceed()) {
//                }
//            }
//        });
//
//        socketClient.send(request);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_btn) {
            ((ActivityCallback) getActivity()).replace();
//            createVerityClientAndSend(1);
        }
    }

}
