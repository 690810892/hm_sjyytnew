package com.zysapp.sjyyt.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zysapp.sjyyt.BaseUtil;
import com.zysapp.sjyyt.activity.R;

import xtom.frame.XtomObject;
import xtom.frame.util.XtomToastUtil;


public class PopDrawGet extends XtomObject {
    private Context mContext;
    private PopupWindow mWindow;
    private ViewGroup mViewGroup;
    TextView tv_join;
    ImageView iv_cancel;
    ClearEditText tvUsername;
    EditText evCode;
    TextView second;
    Button sendcode;

    public PopDrawGet(Context context) {
        mContext = context;
        mWindow = new PopupWindow(mContext);
        mWindow.setWidth(FrameLayout.LayoutParams.MATCH_PARENT);
        mWindow.setHeight(FrameLayout.LayoutParams.MATCH_PARENT);
        mWindow.setBackgroundDrawable(new BitmapDrawable());
        mWindow.setFocusable(true);
        mWindow.setAnimationStyle(R.style.PopupAnimation);
        mViewGroup = (ViewGroup) LayoutInflater.from(mContext).inflate(
                R.layout.pop_lingjiang, null);
        tv_join = (TextView) mViewGroup.findViewById(R.id.tv_join);
        iv_cancel = (ImageView) mViewGroup.findViewById(R.id.iv_cancel);
        tvUsername = (ClearEditText) mViewGroup.findViewById(R.id.tv_username);
        evCode = (EditText) mViewGroup.findViewById(R.id.ev_code);
        second = (TextView) mViewGroup.findViewById(R.id.second);
        sendcode = (Button) mViewGroup.findViewById(R.id.sendcode);
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
                String tel = tvUsername.getText().toString();
                if (isNull(tel)) {
                    XtomToastUtil.showShortToast(mContext, "请输入手机号");
                    return;
                }
               String code = evCode.getText().toString();
                if (isNull(code)) {
                    XtomToastUtil.showShortToast(mContext,"验证码不能为空");
                    return;
                }
                ButtonSure(code);
            }
        });
        sendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = tvUsername.getText().toString();
                if (isNull(tel)) {
                    XtomToastUtil.showShortToast(mContext, "请输入手机号");
                    return;
                }
                String mobile = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
                if (!tel.matches(mobile)) {
                    XtomToastUtil.showShortToast(mContext, "您输入的手机号不正确");
                    return;
                }
                CodeGet(tel);
            }
        });
    }

    public void show() {
        mWindow.showAtLocation(mViewGroup, Gravity.CENTER, 0, 0);
    }

    public void cancel() {
        mWindow.dismiss();
    }

    public void ButtonSure(String code) {

    }

    public void CodeGet(String tel) {
    }
    public static class TimeThread extends Thread {
        private int curr;

        private TimeHandler timeHandler;

        public TimeThread(TimeHandler timeHandler) {
            this.timeHandler = timeHandler;
        }

        public void cancel() {
            curr = 0;
        }

        @Override
        public void run() {
            curr = 60;
            while (curr > 0) {
                timeHandler.sendEmptyMessage(curr);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // ignore
                }
                curr--;
            }
            timeHandler.sendEmptyMessage(-1);
        }
    }

    public static class TimeHandler extends Handler {
        PopDrawGet activity;

        public TimeHandler(PopDrawGet activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -1:
                    activity.sendcode.setText("重新发送");
                    activity.sendcode.setVisibility(View.VISIBLE);
                    break;
                default:
                    activity.sendcode.setVisibility(View.GONE);
                    activity.second.setText("" + msg.what);
                    break;
            }
        }
    }
}
