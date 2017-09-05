package com.zysapp.sjyyt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.HemaUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayParse;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.zysapp.sjyyt.BaseActivity;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.BaseNetWorker;
import com.zysapp.sjyyt.BaseUtil;
import com.zysapp.sjyyt.model.Token;
import com.zysapp.sjyyt.model.User;
import com.zysapp.sjyyt.view.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xtom.frame.XtomActivityManager;
import xtom.frame.XtomConfig;
import xtom.frame.util.Md5Util;
import xtom.frame.util.XtomSharedPreferencesUtil;
import xtom.frame.util.XtomToastUtil;

/**
 * 用户登录界面
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_tomain)
    TextView tvTomain;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_regist)
    TextView tvRegist;
    @BindView(R.id.iv_login)
    ImageView ivLogin;
    @BindView(R.id.iv_regist)
    ImageView ivRegist;
    @BindView(R.id.login_username)
    ClearEditText loginUsername;
    @BindView(R.id.login_password)
    ClearEditText loginPassword;
    @BindView(R.id.tv_forgetpwd)
    TextView tvForgetpwd;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.iv_login_wechat)
    ImageView ivLoginWechat;
    @BindView(R.id.iv_login_qq)
    ImageView ivLoginQq;
    @BindView(R.id.tv_username_regist)
    ClearEditText tvUsernameRegist;
    @BindView(R.id.ev_code)
    EditText evCode;
    @BindView(R.id.second)
    TextView second;
    @BindView(R.id.sendcode)
    Button sendcode;
    @BindView(R.id.ev_regist_password)
    ClearEditText evRegistPassword;
    @BindView(R.id.lv_regist)
    LinearLayout lvRegist;
    @BindView(R.id.lv_login)
    LinearLayout lvLogin;
    @BindView(R.id.tv_agreen)
    TextView tvAgreen;
    @BindView(R.id.lv_agreen)
    LinearLayout lvAgreen;
    int flag = 1;
    private String code;
    private TimeThread timeThread;
    private String tempToken, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
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
        // TODO Auto-generated method stub
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case CLIENT_LOGIN:
                showProgressDialog("正在登录");
                break;
            case THIRD_SAVE:
                showProgressDialog("正在登录");
                break;
            case THIRD_LOGIN_VERIFY:
                //showProgressDialog("正在登录");
                break;
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
        // TODO Auto-generated method stub
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case CLIENT_LOGIN:
            case CLIENT_VERIFY:
            case CODE_GET:
            case CODE_VERIFY:
                cancelProgressDialog();
                break;
            case THIRD_SAVE:
                cancelProgressDialog();
                break;
            case THIRD_LOGIN_VERIFY:
                //showProgressDialog("正在登录");
                break;
            default:
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask,
                                            HemaBaseResult baseResult) {
        // TODO Auto-generated method stub
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case CLIENT_LOGIN:
                @SuppressWarnings("unchecked")
                HemaArrayParse<User> uResult = (HemaArrayParse<User>) baseResult;
                User user = uResult.getObjects().get(0);
                getApplicationContext().setUser(user);
                String username = netTask.getParams().get("username");
                String password = netTask.getParams().get("password");
                XtomSharedPreferencesUtil.save(mContext, "username", username);
                XtomSharedPreferencesUtil.save(mContext, "password", password);
                XtomSharedPreferencesUtil.save(mContext, "isAutoLogin", "true");
                XtomActivityManager.finishAll();
                Intent it = new Intent(this, MainActivity.class);
                startActivity(it);
                break;
            case THIRD_SAVE:
                @SuppressWarnings("unchecked")
                HemaArrayParse<User> tResult = (HemaArrayParse<User>) baseResult;
                User tUser = tResult.getObjects().get(0);
                getApplicationContext().setUser(tUser);
                XtomSharedPreferencesUtil.save(mContext, "isAutoLogin", "true");
                HemaUtil.setThirdSave(mContext, true);// 将第三方登录标记为true,注意在退出登录时将其置为false
                XtomActivityManager.finishAll();
                Intent tIt = new Intent(this, MainActivity.class);
                startActivity(tIt);
                break;
            case CLIENT_VERIFY:
                showTextDialog("该手机号已经被注册了");
                break;
            case CODE_GET:
                timeThread = new TimeThread(new TimeHandler(this));
                timeThread.start();
                break;
            case CODE_VERIFY:
                @SuppressWarnings("unchecked")
                HemaArrayParse<Token> sResult = (HemaArrayParse<Token>) baseResult;
                tempToken = sResult.getObjects().get(0).getTemp_token();
                Intent it2 = new Intent(mContext, Register2Activity.class);
                it2.putExtra("username", this.username);
                it2.putExtra("password", this.password);
                it2.putExtra("token", tempToken);
                startActivity(it2);
//                getNetWorker().clientAdd(tempToken, username, Md5Util.getMd5(XtomConfig.DATAKEY
//                        + Md5Util.getMd5(password)),nickname);
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
            case CLIENT_LOGIN:
                showTextDialog(baseResult.getMsg());
                break;
            case THIRD_SAVE:
                showTextDialog(baseResult.getMsg());
                break;
            case CLIENT_VERIFY:
                username = netTask.getParams().get("username");
                tvUsernameRegist.setFocusable(false);
                getNetWorker().codeGet(username);
                break;
            case CODE_GET:
                showTextDialog(baseResult.getMsg());
                break;
            case CODE_VERIFY:
                showTextDialog(baseResult.getMsg());
                break;
            default:
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        // TODO Auto-generated method stub
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case CLIENT_LOGIN:
                showTextDialog("登录失败");
                break;
            case THIRD_SAVE:
                showTextDialog("登录失败");
                break;
            case CLIENT_VERIFY:
                showTextDialog("验证手机号失败");
                break;
            case CODE_GET:
                showTextDialog("获取验证码失败");
                break;
            case CODE_VERIFY:
                showTextDialog("验证随机码失败");
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
    }

    @Override
    protected void setListener() {
        String username = XtomSharedPreferencesUtil.get(this, "username");
        if (!isNull(username)) {
            loginUsername.setText(username);
        }
    }

    @OnClick({R.id.tv_tomain, R.id.tv_login, R.id.tv_regist, R.id.sendcode, R.id.tv_forgetpwd, R.id.bt_login, R.id.tv_agreen, R.id.iv_login_wechat, R.id.iv_login_qq})
    public void onViewClicked(View view) {
        Intent it;
        switch (view.getId()) {
            case R.id.tv_tomain:
                it = new Intent(mContext, MainActivity.class);
                startActivity(it);
                break;
            case R.id.tv_login:
                ivLogin.setVisibility(View.VISIBLE);
                ivRegist.setVisibility(View.INVISIBLE);
                lvLogin.setVisibility(View.VISIBLE);
                lvRegist.setVisibility(View.INVISIBLE);
                lvAgreen.setVisibility(View.GONE);
                btLogin.setText("登录");
                flag = 1;
                break;
            case R.id.tv_regist:
                ivLogin.setVisibility(View.INVISIBLE);
                ivRegist.setVisibility(View.VISIBLE);
                lvLogin.setVisibility(View.INVISIBLE);
                lvRegist.setVisibility(View.VISIBLE);
                lvAgreen.setVisibility(View.VISIBLE);
                btLogin.setText("注册");
                flag = 2;
                break;
            case R.id.sendcode:
                String uname = tvUsernameRegist.getText().toString();
                if (isNull(uname)) {
                    showTextDialog("请输入手机号");
                    return;
                }
                // String mobile = "^[1][3-8]+\\d{9}";
                String mobile = "\\d{11}";// 只判断11位
                if (!uname.matches(mobile)) {
                    showTextDialog("您输入的手机号不正确");
                    return;
                }
                getNetWorker().clientVerify(uname);
                break;
            case R.id.tv_forgetpwd:
                it = new Intent(mContext, GetPassword0Activity.class);
                it.putExtra("flag", "1");
                it.putExtra("titlename", "忘记密码");
                startActivity(it);
                break;
            case R.id.bt_login:
                if (flag == 1) {
                    String username = loginUsername.getText().toString();
                    String password = loginPassword.getText().toString();
                    if (username.length() != 11) {
                        XtomToastUtil.showLongToast(mContext, "请输入11位手机号");
                        return;
                    }
                    if (password.length() < 6 || password.length() > 20) {
                        XtomToastUtil.showLongToast(mContext, "请输入6-20位密码");
                        return;
                    }
                    BaseNetWorker netWorker = getNetWorker();
                    netWorker.clientLogin(username, Md5Util.getMd5(XtomConfig.DATAKEY
                            + Md5Util.getMd5(password)));
                } else {
                    BaseUtil.hideInput(mContext, tvAgreen);
                    if (isNull(username)) {
                        showTextDialog("请先验证手机号");
                        return;
                    }
                    code = evCode.getText().toString();
                    if (isNull(code)) {
                        showTextDialog("验证码不能为空");
                        return;
                    }
                    password = evRegistPassword.getText().toString();
                    if (isNull(password)) {
                        showTextDialog("请输入密码");
                        return;
                    }
//                    if (password.length() < 6 || password.length() > 16) {
//                        showTextDialog("请设置6-16位密码");
//                        return;
//                    }
                    getNetWorker().codeVerify(username, code);
                }
                break;
            case R.id.tv_agreen:
                String sys_web_service = getApplicationContext().getSysInitInfo()
                        .getSys_web_service();
                String path = sys_web_service + "webview/parm/protocal";
                it = new Intent(mContext, ShowInternetPageActivity.class);
                it.putExtra("path", path);
                it.putExtra("name", "用户协议");
                startActivity(it);
                break;
            case R.id.iv_login_wechat:
                break;
            case R.id.iv_login_qq:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case 11:
                break;
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
        LoginActivity activity;

        public TimeHandler(LoginActivity activity) {
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
