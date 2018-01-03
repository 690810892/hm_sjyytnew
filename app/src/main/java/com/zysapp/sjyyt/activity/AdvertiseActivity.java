package com.zysapp.sjyyt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zysapp.sjyyt.BaseActivity;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.view.CountDownProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xtom.frame.util.XtomSharedPreferencesUtil;

/**
 * 广告--------------------------
 */
public class AdvertiseActivity extends BaseActivity {

    @BindView(R.id.imageview)
    ImageView imageview;
    @BindView(R.id.countdownProgressView)
    CountDownProgressView countdownProgressView;
    private String url = "",imgurl;
    private boolean isShowed;//
    private int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_advertisement);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        countdownProgressView.setTimeMillis(3000);
        countdownProgressView.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (flag==1) {
            countdownProgressView.setTimeMillis(100);
            countdownProgressView.reStart();
        }
        flag=1;
    }

    // 检查是否已经展示过登录界面了
    private boolean isShow() {
        isShowed = "true".equals(XtomSharedPreferencesUtil.get(mContext,
                "isShowed"));
        return true;
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
        ImageLoader.getInstance().displayImage(imgurl,imageview, BaseApplication.getInstance()
                .getOptions(R.mipmap.start));
    }

    @Override
    protected void getExras() {
        url=mIntent.getStringExtra("url");
        imgurl=mIntent.getStringExtra("imgurl");
    }

    @Override
    protected void setListener() {
        countdownProgressView.setProgressListener( new CountDownProgressView.OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                log_d(progress+"");
                if(progress==0)
                    dealPage();
            }
        });
    }

    @OnClick({R.id.imageview, R.id.countdownProgressView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageview:
                if (!isNull(url)) {
                    countdownProgressView.stop();
                    Intent it = new Intent(mContext, ShowInternetPageActivity.class);
                    it.putExtra("name", "详情");
                    it.putExtra("path", url);
                    mContext.startActivity(it);
                }
                break;
            case R.id.countdownProgressView:
                countdownProgressView.stop();
                dealPage();
                break;
        }
    }
    // 根据各种情况判断即将跳转的页面是那个
    private void dealPage() {
        // 判断信息引导页是否展示过了
        if (!isShow()) {
            Intent it = new Intent(mContext, ShowActivity.class);
            startActivity(it);
            finish();
        }else{
            toMain();
        }
    }
    private void toMain(){
        Intent it = new Intent(mContext, MainActivity.class);
        startActivity(it);
        finish();
    }
}
