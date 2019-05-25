package com.ez08.trade.ui.vote;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.request.BizRequest;
import com.ez08.trade.net.Client;
import com.ez08.trade.net.ClientHelper;
import com.ez08.trade.net.Response;
import com.ez08.trade.net.Callback;
import com.ez08.trade.ui.BaseActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TradeVoteDetailActivity extends BaseActivity implements View.OnClickListener {

    ImageView backBtn;
    TextView titleView;
    RecyclerView recyclerView;
    List<Object> list;
    TradeVoteDetailAdapter adapter;

    MeetingEntity entity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_vote_list);
        titleView = findViewById(R.id.title);
        titleView.setText("我要投票");
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.HORIZONTAL));

        list = new ArrayList<>();
        adapter = new TradeVoteDetailAdapter(this);
        recyclerView.setAdapter(adapter);

        entity = (MeetingEntity) getIntent().getSerializableExtra("meeting");
        getList();
    }

    @Override
    public void onClick(View v) {
        if (v == backBtn) {
            finish();
        }
    }

    private void getList() {
        String body = "FUN=440002&TBL_IN=tpmarket,meetingseq,vid,votecode;" +
                entity.tpmarket + "," +
                entity.meetingseq +"," +
                "" +"," +
                entity.votecode + ";";
        BizRequest request = new BizRequest();
        request.setBody(body);
        request.setCallback(new Callback() {
            @Override
            public void callback(Client client, Response data) {
                if (data.isSucceed()) {
                    Log.e("TradeHandFragment", data.getData());
                    try {
                        JSONObject jsonObject = new JSONObject(data.getData());
                        String content = jsonObject.getString("content");
                        Uri uri = Uri.parse(Constant.URI_DEFAULT_HELPER + content);
                        Set<String> pn = uri.getQueryParameterNames();
                        for (Iterator it = pn.iterator(); it.hasNext(); ) {
                            String key = it.next().toString();
                            if ("TBL_OUT".equals(key)) {
                                list.clear();
                                list.add(entity);
                                list.add(new TitleEntity("非累进投票议案"));
                                String out = uri.getQueryParameter(key);
                                String[] split = out.split(";");
                                for (int i = 1; i < split.length; i++) {
                                    String[] var = split[i].split(",");
                                    VoteEntity entity = new VoteEntity();
                                    entity.tpmarket = var[0];
                                    entity.meetingseq = var[1];
                                    entity.vid = var[2];
                                    entity.vinfo = var[3];
                                    entity.vtype = var[4];
                                    entity.vleijino = var[5];
                                    entity.vrole = var[6];
                                    entity.vrelation = var[7];
                                    entity.refcode = var[8];
                                    list.add(entity);
                                    adapter.clearAndAddAll(list);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        ClientHelper.get().send(request);
    }
}
