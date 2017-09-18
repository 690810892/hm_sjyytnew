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

import java.util.List;

import xtom.frame.util.XtomTimeUtil;

/**
 * 我订阅的频道
 */
public class ChannelLoveAdapter extends BaseRecycleAdapter<Channel> {
    private Context mContext;

    public ChannelLoveAdapter(Context mContext, List<Channel> datas) {
        super(datas);
        this.mContext = mContext;
    }

    @Override
    protected void bindData(BaseViewHolder holder, final int position) {
        ((TextView) holder.getView(R.id.tv_name)).setText(datas.get(position).getName());
        RoundedImageView avatar = (RoundedImageView) holder.getView(R.id.iv_image);
        ImageLoader.getInstance().displayImage(datas.get(position).getImgurl(), avatar, BaseApplication.getInstance()
                .getOptions(R.mipmap.default_blog_img));
        ((TextView) holder.getView(R.id.tv_time)).setText(XtomTimeUtil.TransTime(datas.get(position).getLastdate(), "yyyy年MM月dd日 HH:mm") + "上新");
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                for (Channel normal:datas){
//                    normal.setCheck(false);
//                }
//                datas.get(position).setCheck(!datas.get(position).isCheck());
//                notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.listitem_channel_my;
    }
}
