package com.ez08.trade.ui.vote;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ez08.trade.Constant;
import com.ez08.trade.R;
import com.ez08.trade.net.BizRequest;
import com.ez08.trade.net.Client;
import com.ez08.trade.net.ClientHelper;
import com.ez08.trade.net.Response;
import com.ez08.trade.net.ResponseCallback;
import com.ez08.trade.ui.BaseActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TradeVoteListActivity extends BaseActivity implements View.OnClickListener {

    ImageView backBtn;
    TextView titleView;
    RecyclerView recyclerView;
    List<MeetingEntity> list;
    MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_vote_list);
        titleView = findViewById(R.id.title);
        titleView.setText("网络投票");
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.HORIZONTAL));

        list = new ArrayList<>();
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        getList();
    }

    @Override
    public void onClick(View v) {
        if (v == backBtn) {
            finish();
        }
    }

    private void getList() {
        String body = "FUN=440001&TBL_IN=market,companycode,votecode;" +
                "," +
                "," +
                ";";
        BizRequest request = new BizRequest();
        request.setBody(body);
        request.setCallback(new ResponseCallback() {
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
                                String out = uri.getQueryParameter(key);
                                String[] split = out.split(";");
                                for (int i = 1; i < split.length; i++) {
                                    String[] var = split[i].split(",");
                                    MeetingEntity entity = new MeetingEntity();
                                    entity.companycode = var[0];
                                    entity.companyinfo = var[1];
                                    entity.meetingdatebegin = var[4];
                                    entity.meetingdateend = var[5];
                                    entity.meetingdesc = var[7];
                                    entity.meetingseq = var[3];
                                    entity.meetingtype = var[6];
                                    entity.votecode = var[9];
                                    entity.tpmarket = var[2];
                                    list.add(entity);
                                    adapter.notifyDataSetChanged();
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

    class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyHolder(LayoutInflater.from(context).inflate(R.layout.trade_holder_meeting, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
            final MeetingEntity entity = list.get(i);
            myHolder.title.setText(entity.meetingdesc);
            myHolder.companyName.setText("股东大会代码：" + entity.companyinfo);
            myHolder.companyCode.setText("公司名称：" + entity.companycode);
            myHolder.meetingCode.setText("公司代码：" + entity.meetingseq);
            myHolder.startDate.setText("起始日期：" + entity.meetingdatebegin);
            myHolder.endDate.setText("到期日期：" + entity.meetingdateend);
            myHolder.enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TradeVoteListActivity.this,TradeVoteDetailActivity.class);
                    intent.putExtra("meeting",entity);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView meetingCode;
        TextView companyName;
        TextView companyCode;
        TextView startDate;
        TextView endDate;
        Button enter;

        public MyHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            meetingCode = view.findViewById(R.id.meeting_code);
            companyName = view.findViewById(R.id.company_name);
            companyCode = view.findViewById(R.id.company_code);
            startDate = view.findViewById(R.id.start_date);
            endDate = view.findViewById(R.id.end_date);
            enter = view.findViewById(R.id.enter);
        }
    }

}
