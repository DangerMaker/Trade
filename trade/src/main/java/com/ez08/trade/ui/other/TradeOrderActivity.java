package com.ez08.trade.ui.other;

import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class TradeOrderActivity extends BaseActivity implements View.OnClickListener {
    ImageView backBtn;
    TextView titleView;

    TextView submmit;
    TextView copy;
    TextView newItem;
    TextView alter;
    TextView delete;
    RecyclerView recyclerView;

    List<OrderEntity> list;
    MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_activity_order);

        titleView = findViewById(R.id.title);
        titleView.setText("预埋单");
        backBtn = findViewById(R.id.img_back);
        backBtn.setOnClickListener(this);
        submmit = findViewById(R.id.submit);
        copy = findViewById(R.id.copy);
        newItem = findViewById(R.id.new_item);
        alter = findViewById(R.id.alter);
        delete = findViewById(R.id.delete);
        submmit.setOnClickListener(this);
        copy.setOnClickListener(this);
        newItem.setOnClickListener(this);
        alter.setOnClickListener(this);
        delete.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.HORIZONTAL));

        list = new ArrayList<>();
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
    }


    class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyHolder(LayoutInflater.from(context).inflate(R.layout.trade_holder_order, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
            OrderEntity entity = list.get(i);
            myHolder.checkBox.setChecked(entity.isSelect);
            myHolder.name.setText(entity.name);
            myHolder.dict.setText(entity.dict);
            myHolder.date.setText(entity.date);
            myHolder.price.setText(entity.price);
            myHolder.number.setText(entity.qty);

            if(entity.dict.equals("买入")){
                myHolder.dict.setTextColor(setTextColor(R.color.trade_red));
            }else{
                myHolder.dict.setTextColor(setTextColor(R.color.trade_green));
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView date;
        TextView dict;
        TextView name;
        TextView price;
        TextView number;
        public MyHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkbox);
            date = view.findViewById(R.id.date);
            dict = view.findViewById(R.id.dict);
            name = view.findViewById(R.id.name);
            price = view.findViewById(R.id.price);
            number = view.findViewById(R.id.num);
        }
    }


    @Override
    public void onClick(View v) {
        if (backBtn == v) {
            finish();
        } else if (submmit == v) {

        } else if (copy == v) {

        } else if (newItem == v) {
            Intent intent = new Intent(context, TradeNewOrderActivity.class);
            startActivityForResult(intent, 1);
        } else if (alter == v) {

        } else if (delete == v) {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            OrderEntity entity = (OrderEntity) data.getSerializableExtra("entity");
            Log.e("onActivityResult", entity.name);
            list.add(entity);
            adapter.notifyDataSetChanged();
        }
    }
}
