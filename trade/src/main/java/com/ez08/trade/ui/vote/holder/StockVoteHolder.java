package com.ez08.trade.ui.vote.holder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.vote.MeetingEntity;

public class StockVoteHolder extends BaseViewHolder<MeetingEntity> {

    TextView name;
    TextView code;
    public StockVoteHolder(ViewGroup itemView) {
        super(itemView, R.layout.trade_holder_vote_title);
        name = $(R.id.name);
        code = $(R.id.code);
    }

    @Override
    public void setData(MeetingEntity data) {
        name.setText(data.companyinfo);
        code.setText(data.companycode);
    }
}
