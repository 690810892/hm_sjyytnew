package com.zysapp.sjyyt.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.HemaAdapter;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseNetWorker;
import com.zysapp.sjyyt.activity.R;
import com.zysapp.sjyyt.model.Notice;
import com.zysapp.sjyyt.model.User;

import java.util.ArrayList;

import xtom.frame.util.XtomTimeUtil;

/**
 * 系统消息
 */
public class NoticeAdapter extends HemaAdapter implements View.OnClickListener {

    private ArrayList<Notice> notices;
    private BaseNetWorker netWorker;
    public Notice notice;

    public NoticeAdapter(Context mContext, ArrayList<Notice> notices,
                         BaseNetWorker netWorker) {
        super(mContext);
        this.notices = notices;
        this.netWorker = netWorker;
    }

    @Override
    public int getCount() {
        int size = notices == null ? 0 : notices.size();
        return size == 0 ? 0 : notices.size();
    }

    @Override
    public boolean isEmpty() {
        int size = notices == null ? 0 : notices.size();
        return size == 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.listitem_notice, null);
            holder = new ViewHolder();
            findView(convertView, holder);
            convertView.setTag(R.id.TAG_VIEWHOLDER, holder);
        } else {
            holder = (ViewHolder) convertView.getTag(R.id.TAG_VIEWHOLDER);
        }

        Notice notice = notices.get(position);
        setData(position, holder, notice);

        return convertView;
    }

    private void setData(int position, ViewHolder holder, Notice notice) {
        holder.name.setText("系统消息");
        holder.content.setText(notice.getContent());
        String regdate = notice.getRegdate();
        holder.regdate.setText(XtomTimeUtil.TransTime(regdate,
                "yyyy/MM/dd"));
        if (notice.getLooktype().equals("1")){
            holder.point.setVisibility(View.VISIBLE);
        }else {
            holder.point.setVisibility(View.INVISIBLE);
        }
//        holder.all.setTag(notice);
//        holder.all.setOnClickListener(this);
    }

    private void findView(View view, ViewHolder holder) {
        holder.content = (TextView) view.findViewById(R.id.tv_content);
        holder.regdate = (TextView) view.findViewById(R.id.tv_regdate);
        holder.name = (TextView) view.findViewById(R.id.tv_name);
        holder.point = (TextView) view.findViewById(R.id.point);
        holder.all = view.findViewById(R.id.all);
    }

    private static class ViewHolder {
        TextView content;
        TextView regdate;
        TextView name;
        TextView point;
        View all;
    }

    @Override
    public void onClick(View v) {
        notice = (Notice) v.getTag();
        Intent it = null;
        saveRead();
        BaseApplication application = BaseApplication.getInstance();
        User user = application.getUser();
        switch (v.getId()) {
            case R.id.all://
                break;
            default:
                break;
        }
    }

    // 置为已读
    private void saveRead() {
        String looktype = notice.getLooktype();
        if (!"1".equals(looktype))// 已经读过的消息无需再置为已读
            return;
        BaseApplication application = BaseApplication.getInstance();
        User user = application.getUser();
        netWorker.noticeOperate(user.getToken(),"1","0" ,notice.getId(), "1");
    }

}
