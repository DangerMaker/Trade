package com.ez08.trade.ui.user;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.entity.ShareHoldersEntity;
import com.ez08.trade.net.BizRequest;
import com.ez08.trade.net.Client;
import com.ez08.trade.net.ClientHelper;
import com.ez08.trade.net.Response;
import com.ez08.trade.net.ResponseCallback;
import com.ez08.trade.tools.CommonUtils;
import com.ez08.trade.ui.BaseActivity;
import com.ez08.trade.ui.trade.adpater.TradeMainAdapter;
import com.ez08.trade.ui.trade.entity.TradeOtherEntity;
import com.ez08.trade.ui.user.adpater.TradeShareHoldersAdapter;
import com.ez08.trade.ui.user.entity.TradeShareHoldersItem;
import com.ez08.trade.ui.user.entity.TradeShareHoldersTitle;
import com.ez08.trade.ui.view.LinearItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TradeShareholdersActivity extends BaseActivity implements View.OnClickListener {

    ImageView backBtn;
    TextView titleView;

    RecyclerView recyclerView;
    LinearLayoutManager manager;
    List<Object> mList;
    TradeShareHoldersAdapter adapter;

    List<ShareHoldersEntity> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_shareholders);
        titleView = findViewById(R.id.title);
        titleView.setText("股东资料");
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new TradeShareHoldersAdapter(this);
        recyclerView.setAdapter(adapter);

        LinearItemDecoration divider = new LinearItemDecoration(this);
        recyclerView.addItemDecoration(divider);

        list = new ArrayList<>();
        mList = new ArrayList<>();
        mList.add(new TradeShareHoldersTitle());
//        mList.add(new TradeShareHoldersItem("01234567890(主)","刘宇","沪市","153000"));
//        mList.add(new TradeShareHoldersItem("01234567890(主)","刘宇","深市","153001"));
        adapter.addAll(mList);

//        String body = "FUN=" + "410501" + "&TBL_IN=" + "qryflag,count,poststr;" + "0,10,;";
        String body = "FUN=410501&TBL_IN=fundid,market,secuid,qryflag,count,poststr;,,,1,10,;";

        BizRequest request = new BizRequest();
        request.setBody(body);
        request.setCallback(new ResponseCallback() {
            @Override
            public void callback(Client client, Response data) {
                if (data.isSucceed()) {
                    Log.e("ShareHolders", data.getData());

                    try {
                        JSONObject jsonObject = new JSONObject(data.getData());
                        String content = jsonObject.getString("content");
                        Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + content);
                        Set<String> pn = uri.getQueryParameterNames();
                        for (Iterator it = pn.iterator(); it.hasNext(); ) {
                            String key = it.next().toString();
                            if ("TBL_OUT".equals(key)) {
                                String out = uri.getQueryParameter(key);
                                String[] split = out.split(";");
                                for (int i = 1; i < split.length; i++) {
                                    String[] var = split[i].split(",");
                                    ShareHoldersEntity entity = new ShareHoldersEntity();
                                    entity.custid = var[0];
                                    entity.regflag = var[1];
                                    entity.bondreg = var[2];
                                    entity.opendate = var[3];
                                    entity.market = var[4];
                                    entity.secuid = var[5];
                                    entity.name = var[6];
                                    entity.fundid = var[7];
                                    entity.secuseq = var[8];
                                    entity.status = var[9];
                                    list.add(entity);
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    mList.clear();
                    mList.add(new TradeShareHoldersTitle());
                    for (int i = 0; i < list.size(); i++) {
                        mList.add(list.get(i).getItem());
                    }
                    adapter.clearAndAddAll(mList);
                }

            }
        });
        ClientHelper.get().send(request);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            finish();
        }
    }
}
