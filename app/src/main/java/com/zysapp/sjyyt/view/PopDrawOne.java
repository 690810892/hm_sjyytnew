package com.zysapp.sjyyt.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseUtil;
import com.zysapp.sjyyt.activity.R;
import com.zysapp.sjyyt.activity.ShowInternetPageActivity;
import com.zysapp.sjyyt.model.SysInitInfo;

import xtom.frame.XtomObject;


public class PopDrawOne extends XtomObject {
    private Context mContext;
    private PopupWindow mWindow;
    private ViewGroup mViewGroup;
    TextView tv_title;
    TextView tv_content;
    ImageView iv_image;
    LinearLayout lv_score;
    TextView tv_score;
    TextView tv_join;
    TextView tv_aboutscore;
    ImageView iv_cancel;

    public PopDrawOne(Context context) {
        mContext = context;
        mWindow = new PopupWindow(mContext);
        mWindow.setWidth(FrameLayout.LayoutParams.MATCH_PARENT);
        mWindow.setHeight(FrameLayout.LayoutParams.MATCH_PARENT);
        mWindow.setBackgroundDrawable(new BitmapDrawable());
        mWindow.setFocusable(true);
        mWindow.setAnimationStyle(R.style.PopupAnimation);
        mViewGroup = (ViewGroup) LayoutInflater.from(mContext).inflate(
                R.layout.pop_choujiang, null);
         tv_title = (TextView) mViewGroup.findViewById(R.id.tv_title);
        tv_content = (TextView) mViewGroup.findViewById(R.id.tv_content);
        tv_score = (TextView) mViewGroup.findViewById(R.id.tv_score);
        tv_join = (TextView) mViewGroup.findViewById(R.id.tv_join);
        tv_aboutscore = (TextView) mViewGroup.findViewById(R.id.tv_aboutscore);
        iv_image = (ImageView) mViewGroup.findViewById(R.id.iv_image);
        iv_cancel = (ImageView) mViewGroup.findViewById(R.id.iv_cancel);
        lv_score = (LinearLayout) mViewGroup.findViewById(R.id.lv_score);
        BaseUtil.fitPopupWindowOverStatusBar(mWindow, true);
        mWindow.setContentView(mViewGroup);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWindow.dismiss();
            }
        });
        tv_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonSure();
            }
        });
        tv_aboutscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SysInitInfo initInfo = BaseApplication.getInstance()
                        .getSysInitInfo();
                String sys_web_service = initInfo.getSys_web_service();
                String path = sys_web_service + "webview/parm/score";
                Intent it = new Intent(mContext, ShowInternetPageActivity.class);
                it.putExtra("path", path);
                it.putExtra("name", "关于积分");
                mContext.startActivity(it);
            }
        });
    }


    public void setContent(String content) {
        tv_content.setText(content);
    }
    public void setTitle(String content) {
        tv_title.setText(content);
    }
    public void setScore(String content) {
        tv_score.setText(content);
    }
    public void setImage(int content) {
        iv_image.setImageResource(content);
    }
    public void show() {
        mWindow.showAtLocation(mViewGroup, Gravity.CENTER, 0, 0);
    }

    public void cancel() {
        mWindow.dismiss();
    }

    public void ButtonSure() {

    }
}
