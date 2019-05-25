package com.ez08.trade.ui.vote.holder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.vote.MeetingEntity;
import com.ez08.trade.ui.vote.TitleEntity;

public class VoteProgressiveTitleHolder extends BaseViewHolder<TitleEntity> {

    TextView name;
    public VoteProgressiveTitleHolder(ViewGroup itemView) {
        super(itemView, R.layout.trade_holder_vote_progresssive_title);
        name = $(R.id.name);
    }

    @Override
    public void setData(TitleEntity data) {
        name.setText(data.title);
    }
}
