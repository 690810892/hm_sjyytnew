package com.zysapp.sjyyt.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.hemaapp.hm_FrameWork.view.HemaWebView;
import com.zysapp.sjyyt.BaseActivity;

/**
 * 显示网页内容
 * @param //需要从上一个界面中，传入要显示界面的标题和路径
 * */
public class ShowInternetPageActivity extends BaseActivity {

	private TextView titleText;
	private ImageButton titleLeft;
	private Button titleRight;

	private HemaWebView webView;
	private String titlename;
	private String path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_areement);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void callBeforeDataBack(HemaNetTask netTask) {
	}

	@Override
	protected void callAfterDataBack(HemaNetTask netTask) {
	}

	@Override
	protected void callBackForServerSuccess(HemaNetTask netTask,
			HemaBaseResult baseResult) {

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
	}

	@Override
	protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
	}

	@Override
	protected void findView() {
		titleText = (TextView) findViewById(R.id.title_text);
		titleLeft = (ImageButton) findViewById(R.id.title_btn_left);
		titleRight = (Button) findViewById(R.id.title_btn_right);

		webView = (HemaWebView) findViewById(R.id.webview);
	}

	@Override
	protected void getExras() {
		titlename = mIntent.getStringExtra("name");
		path = mIntent.getStringExtra("path");
	}

	@Override
	protected void setListener() {
		titleText.setText(titlename);
		titleLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		titleRight.setVisibility(View.GONE);
		webView.loadUrl(path);
		log_d(path);
	}
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
			webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
