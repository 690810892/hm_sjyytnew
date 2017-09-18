package com.zysapp.sjyyt.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.view.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseRecycleAdapter;
import com.zysapp.sjyyt.activity.R;
import com.zysapp.sjyyt.activity.TypeLiveListActivity;
import com.zysapp.sjyyt.model.Score;
import com.zysapp.sjyyt.model.Type;

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
        if (infor.getKeytype().equals("1")) {
            ((TextView) holder.getView(R.id.tv_state)).setText("获得");
            ((TextView) holder.getView(R.id.tv_score)).setTextColor(0xff212121);
            ((TextView) holder.getView(R.id.tv_score)).setText("+"+infor.getAmount());
        } else {
            ((TextView) holder.getView(R.id.tv_state)).setText("消耗");
            ((TextView) holder.getView(R.id.tv_score)).setTextColor(0xffFF2347);
            ((TextView) holder.getView(R.id.tv_score)).setText("-"+infor.getAmount());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.listitem_score;
    }
}
