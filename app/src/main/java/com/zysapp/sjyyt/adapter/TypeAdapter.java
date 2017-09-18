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
import com.zysapp.sjyyt.model.Song;
import com.zysapp.sjyyt.model.Type;

import java.util.List;

/**
 * 分类
 */
public class TypeAdapter extends BaseRecycleAdapter<Type> {
    private Context mContext;

    public TypeAdapter(Context mContext, List<Type> datas) {
        super(datas);
        this.mContext = mContext;
    }

    @Override
    protected void bindData(BaseViewHolder holder, final int position) {
        ((TextView) holder.getView(R.id.tv_name)).setText(datas.get(position).getName());
        RoundedImageView avatar = (RoundedImageView) holder.getView(R.id.iv_image);
        ImageLoader.getInstance().displayImage(datas.get(position).getImgurl(), avatar, BaseApplication.getInstance()
                .getOptions(R.mipmap.icon_type));
        if (position%2==0){
            holder.getView(R.id.line).setVisibility(View.VISIBLE);
        }else {
            holder.getView(R.id.line).setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(mContext, TypeLiveListActivity.class);
                it.putExtra("keyid",datas.get(position).getId());
                it.putExtra("name",datas.get(position).getName());
                mContext.startActivity(it);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.listitem_type;
    }
}
