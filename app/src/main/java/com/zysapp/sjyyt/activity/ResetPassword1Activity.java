package com.zysapp.sjyyt.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.zysapp.sjyyt.BaseActivity;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.BaseUtil;
import com.zysapp.sjyyt.model.User;
import com.zysapp.sjyyt.view.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xtom.frame.XtomConfig;
import xtom.frame.util.Md5Util;
import xtom.frame.util.XtomSharedPreferencesUtil;
import xtom.frame.util.XtomToastUtil;

/**
 * 修改密码1
 */
public class ResetPassword1Activity extends BaseActivity {

    @BindView(R.id.title_btn_left)
    ImageButton titleBtnLeft;
    @BindView(R.id.title_btn_right)
    Button titleBtnRight;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.tv_oldpassword)
    ClearEditText tvOldpassword;
    @BindView(R.id.tv_password)
    ClearEditText tvPassword;
    @BindView(R.id.tv_repassword)
    ClearEditText tvRepassword;
    @BindView(R.id.tv_button)
    TextView tvButton;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_resetpassword);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        noHideInput.add(titleBtnRight);
        user = BaseApplication.getInstance().getUser();
    }

    @Override
    protected boolean onKeyBack() {
        finish();
        return true;
    }

    @Override
    protected void getExras() {
    }

    @Override
    protected void findView() {
    }

    @Override
    protected void setListener() {
        titleText.setText("修改登录密码");
        titleBtnRight.setText("");
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case PASSWORD_SAVE:
                showProgressDialog("正在保存");
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
            case PASSWORD_SAVE:
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
            case PASSWORD_SAVE:
                String password = netTask.getParams().get("new_password");
                XtomSharedPreferencesUtil.save(mContext, "password", password);
                showTextDialog("修改密码成功");
                titleBtnLeft.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        finish();
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
            case PASSWORD_SAVE:
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
            case PASSWORD_SAVE:
                showTextDialog("保存失败");
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.title_btn_left, R.id.title_btn_right, R.id.tv_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
            case R.id.title_btn_right:
                break;
            case R.id.tv_button:
                BaseUtil.hideInput(mContext, titleBtnLeft);
                String old = tvOldpassword.getText().toString();
                String newpsd = tvPassword.getText().toString();
                String renewpsd = tvRepassword.getText().toString();
                if (isNull(old)) {
                    XtomToastUtil.showLongToast(mContext, "请输入原密码");
                    break;
                }
                if (isNull(newpsd)) {
                    XtomToastUtil.showLongToast(mContext, "请输入新密码");
                    break;
                }
                if (isNull(renewpsd)) {
                    XtomToastUtil.showLongToast(mContext, "请确认新密码");
                    break;
                }
//                if (newpsd.length() < 6 || newpsd.length() > 20) {
//                    XtomToastUtil.showLongToast(mContext, "请设置6-20位密码");
//                    break;
//                }
                if (!newpsd.equals(renewpsd)) {
                    XtomToastUtil.showLongToast(mContext, "两次输入的密码不一致");
                    break;
                }
                getNetWorker().passwordSave(user.getToken(), "1", Md5Util.getMd5(XtomConfig.DATAKEY
                        + Md5Util.getMd5(old)), Md5Util.getMd5(XtomConfig.DATAKEY
                        + Md5Util.getMd5(newpsd)));
                break;
        }
    }
}
