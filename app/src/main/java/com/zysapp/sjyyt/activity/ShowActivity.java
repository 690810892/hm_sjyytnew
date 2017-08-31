package com.zysapp.sjyyt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.HemaUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.zysapp.sjyyt.BaseActivity;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.BaseNetWorker;
import com.zysapp.sjyyt.adapter.ShowAdapter;
import com.zysapp.sjyyt.model.User;

import xtom.frame.util.XtomSharedPreferencesUtil;

/**
 * 软件的信息引导页 1、页面数据后，判断是否选择了城市，如果未选择，则进入城市选择界面，如果选择了，则判断是否自动登录
 * 2、如果自动登录，则去调用接口，然后无论成功与否都直接进入主页面，如果未登录，也进入主页面
 * */
public class ShowActivity extends BaseActivity {

	private ViewPager mViewPager;
	private RelativeLayout layout;
	private ImageView start;
	private ShowAdapter mAdapter;

	private boolean isAutomaticLogin = false;// 是否自动登录
	public boolean isSelectCity = false;// 是否已经选择了城市
	
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_show);
		super.onCreate(savedInstanceState);

		String[] imgs = new String[] { "start_1.png", "start_2.png",
				"start_3.png" };
		mAdapter = new ShowAdapter(mContext, imgs,layout);
		mViewPager.setAdapter(mAdapter);
	}


	// 检查是否自动登录
	private boolean isAutoLogin() {
		isAutomaticLogin = "true".equals(XtomSharedPreferencesUtil.get(
				mContext, "isAutoLogin"));
		return isAutomaticLogin;
	}
	// 判断是否选择了城市
		private boolean isSelectCity() {
			String city = getCityName();
			isSelectCity = isNull(city);
			return isSelectCity;
		}
	@Override
	protected void onDestroy() {
		XtomSharedPreferencesUtil.save(mContext, "isShowed", "true"); // 将isShowed参数保存到XtomSharedPreferencesUtils里面
		super.onDestroy();
	}

	@Override
	public void finish() {
			//判断是否自动登录
			if(isAutoLogin()){
				String username = XtomSharedPreferencesUtil.get(this, "username");
				String password = XtomSharedPreferencesUtil.get(this, "password");
				if (!isNull(username) && !isNull(password)) {
					BaseNetWorker netWorker = getNetWorker();
					netWorker.clientLogin(username, password);
				}else if (HemaUtil.isThirdSave(mContext)) {// 如果是第三方登录
					BaseNetWorker netWorker = getNetWorker();
					netWorker.thirdSave();
				}else {
					toMain();
				}
			}else {
				toMain();
			}
		super.finish();
	}
	
	private void toMain(){
		Intent it = new Intent(mContext, MainActivity.class);
		startActivity(it);
	}
	private void toLogin(){
		Intent it = new Intent(mContext, LoginActivity.class);
		startActivity(it);
	}

	@Override
	protected void findView() {
		start = (ImageView) findViewById(R.id.button);
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		layout = (RelativeLayout) findViewById(R.id.layout);
	}

	@Override
	protected void getExras() {

	}

	@Override
	protected boolean onKeyBack() {
		return false;
	}

	@Override
	protected boolean onKeyMenu() {
		return false;
	}

	@Override
	protected void setListener() {
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				ViewGroup indicator = mAdapter.getmIndicator();
				if (indicator != null) {
					RadioButton rbt = (RadioButton) indicator
							.getChildAt(position);
					if (rbt != null)
						rbt.setChecked(true);
				}
				
				if (position == mAdapter.getCount() - 1)
					start.setVisibility(View.VISIBLE);
				else
					start.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void callBeforeDataBack(HemaNetTask netTask) {

	}

	@Override
	protected void callAfterDataBack(HemaNetTask netTask) {

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void callBackForServerSuccess(HemaNetTask netTask,
			HemaBaseResult baseResult) {
		BaseHttpInformation information = (BaseHttpInformation) netTask
				.getHttpInformation();
		switch (information) {
		case CLIENT_LOGIN:
			HemaArrayResult<User> uResult = (HemaArrayResult<User>) baseResult;
			user = uResult.getObjects().get(0);
			getApplicationContext().setUser(user);
			toMain();
			break;
		case THIRD_SAVE:
			HemaArrayResult<User> mResult = (HemaArrayResult<User>) baseResult;
			user = mResult.getObjects().get(0);
			getApplicationContext().setUser(user);
			toMain();
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
			toMain();
			break;
		case THIRD_SAVE:
			toMain();
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
		case CLIENT_LOGIN:
			toMain();
			break;
		case THIRD_SAVE:
			toMain();
			break;
		default:
			break;
		}
	}
}
