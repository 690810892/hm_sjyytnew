package com.zysapp.sjyyt.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.view.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseRecycleAdapter;
import com.zysapp.sjyyt.activity.R;
import com.zysapp.sjyyt.model.Channel;
import com.zysapp.sjyyt.model.Reply;

import java.util.List;

import xtom.frame.util.XtomTimeUtil;

/**
 * 回复
 */
public class ReplyChildAdapter extends BaseRecycleAdapter<Reply> {
    private Context mContext;

    public ReplyChildAdapter(Context mContext, List<Reply> datas) {
        super(datas);
        this.mContext = mContext;
    }

    @Override
    protected void bindData(BaseViewHolder holder, final int position) {
        ((TextView) holder.getView(R.id.tv_name)).setText(datas.get(position).getNickname()+":");
        ((TextView) holder.getView(R.id.tv_content)).setText(datas.get(position).getContent());
    }

    @Override
    public int getLayoutId() {
        return R.layout.listitem_reply_child;
    }
}
