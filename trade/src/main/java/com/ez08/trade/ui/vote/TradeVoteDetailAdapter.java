package com.ez08.trade.ui.vote;

import android.content.Context;
import android.view.ViewGroup;

import com.ez08.trade.ui.BaseAdapter;
import com.ez08.trade.ui.BaseViewHolder;
import com.ez08.trade.ui.fresh_stock.entity.TradePhEntity;
import com.ez08.trade.ui.fresh_stock.entity.TradePhTitleEntity;
import com.ez08.trade.ui.fresh_stock.entity.TradeZqEntity;
import com.ez08.trade.ui.fresh_stock.holder.TradePhHolder;
import com.ez08.trade.ui.fresh_stock.holder.TradeTitlePhHolder;
import com.ez08.trade.ui.fresh_stock.holder.TradeZqHolder;
import com.ez08.trade.ui.vote.holder.StockVoteHolder;
import com.ez08.trade.ui.vote.holder.VoteProgressiveTitleHolder;
import com.ez08.trade.ui.vote.holder.VoteUnProgressiveHolder;

import java.security.InvalidParameterException;

public class TradeVoteDetailAdapter extends BaseAdapter<Object> {

    public static final int TITLE = 1;
    public static final int PROGRESSIVE = 2;
    public static final int UNPROGRESSIVE = 3;
    public static final int CHILD_TITLE = 4;



    public TradeVoteDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof MeetingEntity) {
            return TITLE;
        } else if(getItem(position) instanceof TitleEntity){
            return CHILD_TITLE;
        }else if(getItem(position) instanceof VoteEntity){
            return UNPROGRESSIVE;
        }else {
            return -1;
        }
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TITLE) {
            return new StockVoteHolder(parent);
        } else if (viewType == CHILD_TITLE) {
            return new VoteProgressiveTitleHolder(parent);
        } else if (viewType == UNPROGRESSIVE) {
            return new VoteUnProgressiveHolder(parent);
        } else {
            throw new InvalidParameterException();
        }
    }
}
