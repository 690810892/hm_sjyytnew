package com.zysapp.sjyyt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayParse;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.zysapp.sjyyt.BaseActivity;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.model.Token;
import com.zysapp.sjyyt.view.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xtom.frame.XtomActivityManager;
import xtom.frame.XtomConfig;
import xtom.frame.util.Md5Util;

/**
 * 找回密码第一步
 */
public class GetPassword0Activity extends BaseActivity {
    @BindView(R.id.title_btn_left)
    ImageButton titleBtnLeft;
    @BindView(R.id.title_btn_right)
    Button titleBtnRight;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.tv_username)
    ClearEditText tvUsername;
    @BindView(R.id.ev_code)
    EditText evCode;
    @BindView(R.id.second)
    TextView second;
    @BindView(R.id.sendcode)
    Button sendcode;
    @BindView(R.id.ev_password)
    ClearEditText evPassword;
    @BindView(R.id.tv_button)
    TextView tvButton;
    private String username;
    private String password;
    private TimeThread timeThread;

    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_getpassword);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (timeThread != null)
            timeThread.cancel();
        super.onDestroy();
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case CLIENT_VERIFY:
                showProgressDialog("正在验证手机号");
                break;
            case CODE_GET:
                showProgressDialog("正在获取验证码");
                break;
            case CODE_VERIFY:
                showProgressDialog("正在验证随机码");
                break;
            default:
                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case CLIENT_VERIFY:
            case CODE_GET:
            case CODE_VERIFY:
                cancelProgressDialog();
                break;
            default:
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask,
                                            HemaBaseResult baseResult) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case CLIENT_VERIFY:
                username = netTask.getParams().get("username");
                tvUsername.setFocusable(false);
                getNetWorker().codeGet(username);
                break;
            case CODE_GET:
                timeThread = new TimeThread(new TimeHandler(this));
                timeThread.start();
                break;
            case CODE_VERIFY:
                @SuppressWarnings("unchecked")
                HemaArrayParse<Token> sResult = (HemaArrayParse<Token>) baseResult;
                String tempToken = sResult.getObjects().get(0).getTemp_token();
                getNetWorker().passwordReset(tempToken, "1", Md5Util.getMd5(XtomConfig.DATAKEY
                        + Md5Util.getMd5(password)));
                break;
            case PASSWORD_RESET:
                titleText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        XtomActivityManager.finishAll();
                        Intent it = new Intent(mContext, LoginActivity.class);
                        startActivity(it);
                    }
                }, 1000);
                break;
            default:
                break;
        }

    }

    @Override
    public void onPublish(int progress) {

    }

    @Override
    public void onChange(int position) {

    }

    @Override
    protected void callBackForServerFailed(HemaNetTask netTask,
                                           HemaBaseResult baseResult) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case CLIENT_VERIFY:
                showTextDialog(baseResult.getMsg());
                break;
            case CODE_GET:
                showTextDialog(baseResult.getMsg());
                break;
            case CODE_VERIFY:
                showTextDialog(baseResult.getMsg());
                break;
            case PASSWORD_RESET:
                showTextDialog(baseResult.getMsg());
                break;
            default:
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case CLIENT_VERIFY:
                showTextDialog("验证手机号失败");
                break;
            case CODE_GET:
                showTextDialog("获取验证码失败");
                break;
            case CODE_VERIFY:
                showTextDialog("验证随机码失败");
                break;
            case PASSWORD_RESET:
                showTextDialog("重设密码失败");
                break;
            default:
                break;
        }
    }

    @Override
    protected void findView() {
    }

    @Override
    protected void getExras() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void setListener() {
        titleText.setText("找回密码");
    }

    @OnClick({R.id.title_btn_left, R.id.sendcode, R.id.tv_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
            case R.id.sendcode:
                String uname = tvUsername.getText().toString();
                if (isNull(uname)) {
                    showTextDialog("请输入手机号");
                    return;
                }

                String mobile = "^[1][3-8]+\\d{9}";
                if (!uname.matches(mobile)) {
                    showTextDialog("您输入的手机号不正确");
                    return;
                }

                getNetWorker().clientVerify(uname);
                break;
            case R.id.tv_button:
                if (isNull(username)) {
                    showTextDialog("请先验证手机号");
                    return;
                }
                code = evCode.getText().toString();
                if (isNull(code)) {
                    showTextDialog("验证码不能为空");
                    return;
                }
                password = evPassword.getText().toString();
                if (isNull(password)) {
                    showTextDialog("请输入密码");
                    return;
                }
//                if (password.length() < 6 || password.length() > 16) {
//                    showTextDialog("请设置6-16位的密码");
//                    return;
//                }
                getNetWorker().codeVerify(username, code);
                break;
        }
    }

    private class SendButtonListener implements OnClickListener {

        @Override
        public void onClick(View v) {


        }

    }

    private class TimeThread extends Thread {
        private int curr;

        private TimeHandler timeHandler;

        public TimeThread(TimeHandler timeHandler) {
            this.timeHandler = timeHandler;
        }

        void cancel() {
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

    private static class TimeHandler extends Handler {
        GetPassword0Activity activity;

        public TimeHandler(GetPassword0Activity activity) {
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
