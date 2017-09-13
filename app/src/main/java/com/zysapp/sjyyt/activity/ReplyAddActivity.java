package com.zysapp.sjyyt.activity;

import android.os.Build;
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
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.BaseUtil;
import com.zysapp.sjyyt.model.User;
import com.zysapp.sjyyt.util.EventBusConfig;
import com.zysapp.sjyyt.util.EventBusModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 评论
 */
public class ReplyAddActivity extends BaseActivity {
    @BindView(R.id.title_btn_left)
    ImageButton titleBtnLeft;
    @BindView(R.id.title_btn_right)
    Button titleBtnRight;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.edittext)
    EditText edittext;
    private String live_id  = "1",keytype="1";
    private String comment_id,content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_replyadd);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        noHideInput.add(titleBtnRight);
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case REPLY_ADD:
                showProgressDialog("正在提交");
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
            case REPLY_ADD:
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
            case REPLY_ADD:
                showTextDialog(baseResult.getMsg());
                EventBus.getDefault().post(new EventBusModel(EventBusConfig.REFRESH_REPLY,content));
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
            case REPLY_ADD:
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
            case REPLY_ADD:
                showTextDialog("提交失败");
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
        live_id=mIntent.getStringExtra("live_id");
        comment_id=mIntent.getStringExtra("comment_id");
    }

    @Override
    protected void setListener() {
        titleBtnRight.setText("提交");
        titleText.setText("评论");
        edittext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                1000)});
    }

    @OnClick({R.id.title_btn_left, R.id.title_btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
            case R.id.title_btn_right:
                BaseUtil.hideInput(mContext, titleBtnLeft);
                 content = edittext.getText().toString();
                if (isNull(content)) {
                    showTextDialog("请输入评论内容");
                    return;
                }
                User user = BaseApplication.getInstance().getUser();
                getNetWorker().replyAdd(user.getToken(),keytype,live_id,comment_id,content);
                break;
        }
    }

}
