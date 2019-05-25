package com.ez08.trade.ui.vote.holder;

import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ez08.trade.R;
import com.ez08.trade.tools.CommonUtils;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.vote.VoteEntity;

public class VoteUnProgressiveHolder extends BaseViewHolder<VoteEntity> {
    TextView title;
    RadioGroup group;
    RadioButton agree;
    RadioButton disagree;
    RadioButton giveUp;
    public VoteUnProgressiveHolder(ViewGroup itemView) {
        super(itemView,R.layout.trade_holder_unprogressive);
        title = $(R.id.title);
        group = $(R.id.radio_group);
        agree = $(R.id.agree);
        disagree = $(R.id.disagree);
        giveUp = $(R.id.give_up);
    }

    @Override
    public void setData(VoteEntity data) {
        title.setText((getDataPosition() - 1) +"、" + CommonUtils.deleteAllCRLF(data.vinfo));
    }
}
