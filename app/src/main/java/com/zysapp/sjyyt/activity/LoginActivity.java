package com.zysapp.sjyyt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.HemaUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayParse;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.hemaapp.hm_FrameWork.view.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zysapp.sjyyt.BaseActivity;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.BaseNetWorker;
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
 * 用户登录界面----------------------------------------------------
 * 55555555555555
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.title_btn_left)
    ImageButton titleBtnLeft;
    @BindView(R.id.title_btn_right)
    Button titleBtnRight;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.iv_avatar)
    RoundedImageView ivAvatar;
    @BindView(R.id.login_username)
    ClearEditText loginUsername;
    @BindView(R.id.login_password)
    ClearEditText loginPassword;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_forgetpwd)
    TextView tvForgetpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
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
        titleBtnRight.setText("注册");
        loginUsername.addTextChangedListener(new OnTextChangeListener());
        loginPassword.addTextChangedListener(new OnTextChangeListener());
        String username = XtomSharedPreferencesUtil.get(this, "username");
        String avatar = XtomSharedPreferencesUtil.get(this, "avatar");
        if (!isNull(username)) {
            loginUsername.setText(username);
        }
        if (!isNull(avatar)) {
            ImageLoader.getInstance().displayImage(avatar, ivAvatar, BaseApplication.getInstance()
                    .getOptions(R.mipmap.default_avatar));
        }
    }
    @OnClick({R.id.title_btn_left, R.id.title_btn_right, R.id.bt_login, R.id.tv_forgetpwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
            case R.id.title_btn_right:
//                Intent it = new Intent(mContext, Register0Activity.class);
//                startActivity(it);
                break;
            case R.id.bt_login:
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
                break;
            case R.id.tv_forgetpwd:
//                Intent it2 = new Intent(mContext, GetPassword0Activity.class);
//                it2.putExtra("flag", "1");
//                it2.putExtra("titlename", "找回密码");
//                startActivity(it2);
                break;
        }
    }

    private class OnTextChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkNextable();
        }
    }

    private void checkNextable() {
        String username = loginUsername.getText().toString();
        // String mobile = "^[1][3-8]+\\d{9}";
        String mobile = "\\d{11}";// 只判断11位
        boolean c = !isNull(username) && username.matches(mobile);
        if (c) {
            String password = loginPassword.getText().toString();
            c = !isNull(password);
        }

        if (c) {
            btLogin.setEnabled(true);
        } else {
            btLogin.setEnabled(false);
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
}
