package com.zysapp.sjyyt.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zysapp.sjyyt.BaseRecycleAdapter;
import com.zysapp.sjyyt.activity.R;
import com.zysapp.sjyyt.model.Reply;

import java.util.List;

/**
 * 点赞
 */
public class DogoodAdapter extends BaseRecycleAdapter<Reply> {
    private Context mContext;

    public DogoodAdapter(Context mContext, List<Reply> datas) {
        super(datas);
        this.mContext = mContext;
    }

    @Override
    protected void bindData(BaseViewHolder holder, final int position) {
        if (position == datas.size() - 1)
            ((TextView) holder.getView(R.id.tv_name)).setText(datas.get(position).getNickname());
        else
            ((TextView) holder.getView(R.id.tv_name)).setText(datas.get(position).getNickname() + "、");
    }

    @Override
    public int getLayoutId() {
        return R.layout.listitem_dogood;
    }
}
