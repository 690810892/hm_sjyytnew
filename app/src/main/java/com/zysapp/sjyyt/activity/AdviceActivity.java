package com.zysapp.sjyyt.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.HemaUtil;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.zysapp.sjyyt.BaseActivity;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.BaseUtil;
import com.zysapp.sjyyt.model.User;

/**
 * 意见反馈页面
 */
public class AdviceActivity extends BaseActivity {
    private TextView titleText;
    private ImageButton titleLeft;
    private Button titleRight;

    private EditText editText;
    private TextView textView;
    private String keytype = "1";
    private String appversion;
    private String sysversion;
    private String phonebrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_advice);
        super.onCreate(savedInstanceState);
        noHideInput.add(titleRight);
        appversion = HemaUtil.getAppVersionForSever(mContext);
        sysversion = android.os.Build.VERSION.RELEASE;
        phonebrand = android.os.Build.MODEL;
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case ADVICE_ADD:
                showProgressDialog("正在提交您的宝贵意见");
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
            case ADVICE_ADD:
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
            case ADVICE_ADD:
                showTextDialog(baseResult.getMsg());
                titleText.postDelayed(new Runnable() {

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
            case ADVICE_ADD:
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
            case ADVICE_ADD:
                showTextDialog("意见提交失败");
                break;
            default:
                break;
        }
    }

    @Override
    protected void findView() {
        titleText = (TextView) findViewById(R.id.title_text);
        titleLeft = (ImageButton) findViewById(R.id.title_btn_left);
        titleRight = (Button) findViewById(R.id.title_btn_right);
        editText = (EditText) findViewById(R.id.edittext);
        textView = (TextView) findViewById(R.id.textview);
    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void setListener() {
        titleRight.setText("提交");
//        titleRight.setTextColor(getResources().getColor(R.color.main));
        titleText.setText("意见反馈");
        titleLeft.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                1000)});
        editText.addTextChangedListener(new TextChangeListener());
        titleRight.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                BaseUtil.hideInput(mContext, titleLeft);
                String content = editText.getText().toString();
                if (isNull(content)) {
                    showTextDialog("请输入您的意见");
                    return;
                }
                User user = getApplicationContext().getUser();
                getNetWorker().adviceAdd(user.getToken(), content, appversion, phonebrand, sysversion);
            }
        });
    }

    private class TextChangeListener implements TextWatcher {

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
            textView.setText(s.toString().length() + "");
        }


    }

}
