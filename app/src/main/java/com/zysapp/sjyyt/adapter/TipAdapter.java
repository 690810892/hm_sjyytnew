package com.zysapp.sjyyt.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.view.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseNetWorker;
import com.zysapp.sjyyt.BaseRecycleAdapter;
import com.zysapp.sjyyt.activity.R;
import com.zysapp.sjyyt.model.Clock;
import com.zysapp.sjyyt.model.Song;
import com.zysapp.sjyyt.model.User;

import java.util.List;

import xtom.frame.util.XtomTimeUtil;

/**
 * 叫早
 */
public class TipAdapter extends BaseRecycleAdapter<Clock> {
    private Context mContext;
    public Clock infor;
private BaseNetWorker netWorker;
    public TipAdapter(Context mContext, List<Clock> datas,BaseNetWorker netWorker) {
        super(datas);
        this.mContext = mContext;
        this.netWorker = netWorker;
    }

    @Override
    protected void bindData(BaseViewHolder holder, final int position) {
        Clock clock=datas.get(position);
        ((TextView) holder.getView(R.id.tv_name)).setText(datas.get(position).getContent());
        ((TextView) holder.getView(R.id.tv_time)).setText(XtomTimeUtil.TransTime(clock.getCalldate(),"yyyy/MM/dd HH:mm"));
        holder.getView(R.id.iv_delete).setTag(R.id.TAG,clock);
        holder.getView(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infor= (Clock) v.getTag(R.id.TAG);
                User user=BaseApplication.getInstance().getUser();
                netWorker.clockOperate(user.getToken(),"2",infor.getId());
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
        return R.layout.listitem_tip;
    }
}
