package com.zysapp.sjyyt.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zysapp.sjyyt.BaseUtil;
import com.zysapp.sjyyt.activity.R;

import xtom.frame.XtomObject;


public class PopDrawNoWin extends XtomObject {
    private Context mContext;
    private PopupWindow mWindow;
    private ViewGroup mViewGroup;
    TextView tv_join;
    ImageView iv_cancel;

    public PopDrawNoWin(Context context) {
        mContext = context;
        mWindow = new PopupWindow(mContext);
        mWindow.setWidth(FrameLayout.LayoutParams.MATCH_PARENT);
        mWindow.setHeight(FrameLayout.LayoutParams.MATCH_PARENT);
        mWindow.setBackgroundDrawable(new BitmapDrawable());
        mWindow.setFocusable(true);
        mWindow.setAnimationStyle(R.style.PopupAnimation);
        mViewGroup = (ViewGroup) LayoutInflater.from(mContext).inflate(
                R.layout.pop_meizhongjiang, null);
        tv_join = (TextView) mViewGroup.findViewById(R.id.tv_join);
        iv_cancel = (ImageView) mViewGroup.findViewById(R.id.iv_cancel);
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
