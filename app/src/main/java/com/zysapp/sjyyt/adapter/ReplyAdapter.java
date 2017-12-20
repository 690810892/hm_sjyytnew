package com.zysapp.sjyyt.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.view.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseNetWorker;
import com.zysapp.sjyyt.BaseRecycleAdapter;
import com.zysapp.sjyyt.ToLogin;
import com.zysapp.sjyyt.activity.R;
import com.zysapp.sjyyt.activity.ReplyAddActivity;
import com.zysapp.sjyyt.model.Reply;
import com.zysapp.sjyyt.model.User;
import com.zysapp.sjyyt.util.RecycleUtils;

import java.util.List;

import xtom.frame.util.XtomTimeUtil;

/**
 * 评论列表
 */
public class ReplyAdapter extends BaseRecycleAdapter<Reply> {
    private Context mContext;
    private User user;
    private String live_id = "0", keytype = "1";
    private BaseNetWorker netWorker;

    public ReplyAdapter(Context mContext, List<Reply> datas, BaseNetWorker netWorker) {
        super(datas);
        this.mContext = mContext;
        this.netWorker = netWorker;
        user = BaseApplication.getInstance().getUser();
    }

    public void setLive_id(String id) {
        live_id = id;
    }

    public void setKeytype(String keytype) {
        this.keytype = keytype;
    }

    @Override
    protected void bindData(final BaseViewHolder holder, final int position) {
        final Reply infor = datas.get(position);
        ((TextView) holder.getView(R.id.tv_name)).setText(datas.get(position).getNickname());
        RoundedImageView avatar = (RoundedImageView) holder.getView(R.id.iv_image);
        ImageLoader.getInstance().displayImage(infor.getAvatar(), avatar, BaseApplication.getInstance()
                .getOptions(R.mipmap.default_avatar));
        avatar.setCornerRadius(100);
        ((TextView) holder.getView(R.id.tv_time)).setText(XtomTimeUtil.TransTime(infor.getRegdate(), "yyyy年MM月dd日"));
        ((TextView) holder.getView(R.id.tv_content)).setText(infor.getContent());
        if (infor.getReplies().size() == 0 && infor.getDogoods().size() == 0) {
            holder.getView(R.id.iv_huifu).setVisibility(View.GONE);
            holder.getView(R.id.lv_huifu).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.iv_huifu).setVisibility(View.VISIBLE);
            holder.getView(R.id.lv_huifu).setVisibility(View.VISIBLE);
        }
        ReplyChildAdapter replyChildAdapter = new ReplyChildAdapter(mContext, infor.getReplies());
        RecycleUtils.initVerticalRecyleNoScrll((RecyclerView) holder.getView(R.id.rv_reply));
        ((RecyclerView) holder.getView(R.id.rv_reply)).setAdapter(replyChildAdapter);
        DogoodAdapter dogoodAdapter = new DogoodAdapter(mContext, infor.getDogoods());
        RecycleUtils.initHorizontalRecyleNoScrll((RecyclerView) holder.getView(R.id.rv_good));
        ((RecyclerView) holder.getView(R.id.rv_good)).setAdapter(dogoodAdapter);
        holder.getView(R.id.iv_reply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.getView(R.id.fv_dogood).getVisibility() == View.INVISIBLE) {
                    holder.getView(R.id.fv_dogood).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.fv_dogood).setVisibility(View.INVISIBLE);
                }
            }
        });
        holder.getView(R.id.tv_dogood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = BaseApplication.getInstance().getUser();
                if (user == null) {
                    ToLogin.showLogin((Activity) mContext);
                    return;
                }
                if (infor.getLoveflag().equals("0"))
                    netWorker.dataOperate(user.getToken(), "6", infor.getId());
                else
                    netWorker.dataOperate(user.getToken(), "8", infor.getId());
            }
        });
        holder.getView(R.id.tv_reply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = BaseApplication.getInstance().getUser();
                if (user == null) {
                    ToLogin.showLogin((Activity) mContext);
                    return;
                }
                Intent it = new Intent(mContext, ReplyAddActivity.class);
                if (keytype.equals("3"))
                    it.putExtra("currentPosition", 0);
                it.putExtra("live_id", live_id);
                it.putExtra("comment_id", infor.getId());
                mContext.startActivity(it);
            }
        });
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
        return R.layout.listitem_reply;
    }
}
