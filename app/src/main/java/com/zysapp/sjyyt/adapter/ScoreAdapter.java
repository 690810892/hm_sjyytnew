package com.zysapp.sjyyt.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zysapp.sjyyt.BaseRecycleAdapter;
import com.zysapp.sjyyt.activity.R;
import com.zysapp.sjyyt.model.Score;

import java.util.List;

import xtom.frame.util.XtomTimeUtil;

/**
 * 积分
 */
public class ScoreAdapter extends BaseRecycleAdapter<Score> {
    private Context mContext;

    public ScoreAdapter(Context mContext, List<Score> datas) {
        super(datas);
        this.mContext = mContext;
    }

    @Override
    protected void bindData(BaseViewHolder holder, final int position) {
        Score infor = datas.get(position);
        ((TextView) holder.getView(R.id.tv_time)).setText(XtomTimeUtil.TransTime(infor.getRegdate(), "yyyy/MM/dd HH:mm"));
        if (infor.getAmount().startsWith("-")) {
            ((TextView) holder.getView(R.id.tv_score)).setTextColor(0xffFF2347);
            ((TextView) holder.getView(R.id.tv_score)).setText(infor.getAmount());
        } else {
            ((TextView) holder.getView(R.id.tv_score)).setTextColor(0xff212121);
            ((TextView) holder.getView(R.id.tv_score)).setText("+" + infor.getAmount());
        }
        ((TextView) holder.getView(R.id.tv_state)).setText(infor.getContent());
    }

    @Override
    public int getLayoutId() {
        return R.layout.listitem_score;
    }
}
