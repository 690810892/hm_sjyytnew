package com.zysapp.sjyyt.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.view.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseNetWorker;
import com.zysapp.sjyyt.BaseRecycleAdapter;
import com.zysapp.sjyyt.ToLogin;
import com.zysapp.sjyyt.activity.R;
import com.zysapp.sjyyt.model.Author;
import com.zysapp.sjyyt.model.Song;
import com.zysapp.sjyyt.model.User;

import java.util.List;

/**
 * 主播
 */
public class AuthorAdapter extends BaseRecycleAdapter<Author> {
    private Context mContext;
    User user;
    BaseNetWorker netWorker;
    String live_id;

    public AuthorAdapter(Context mContext, List<Author> datas, BaseNetWorker netWorker) {
        super(datas);
        this.mContext = mContext;
        this.netWorker = netWorker;
        user = BaseApplication.getInstance().getUser();
    }

    public void setLive_id(String live_id) {
        this.live_id = live_id;
    }

    @Override
    protected void bindData(BaseViewHolder holder, final int position) {
        ((TextView) holder.getView(R.id.tv_player)).setText(datas.get(position).getName());
        RoundedImageView avatar = (RoundedImageView) holder.getView(R.id.avatar);
        ImageLoader.getInstance().displayImage(datas.get(position).getImgurl(), avatar, BaseApplication.getInstance()
                .getOptions(R.mipmap.default_blog_img));
        ((TextView) holder.getView(R.id.tv_tip)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    ToLogin.showLogin((Activity) mContext);
                    return;
                }
                netWorker.clockAdd(user.getToken(), live_id);
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
        return R.layout.listitem_author;
    }
}
