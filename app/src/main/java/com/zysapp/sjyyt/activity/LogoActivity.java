package com.zysapp.sjyyt.activity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.HemaUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.zysapp.sjyyt.BaseActivity;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.BaseLocation;
import com.zysapp.sjyyt.BaseNetWorker;
import com.zysapp.sjyyt.model.SysInitInfo;
import com.zysapp.sjyyt.model.User;

import xtom.frame.util.XtomSharedPreferencesUtil;

/**
 * 软件启动后运行的界面。 根据客户要求，用户可以自己改变开机的动画，参考校园呱呱 此软件逻辑：
 * 1、软件启动后，获取保存的系统信息和用户登录信息,如果为空，在动画结束后，进行系统初始化，并获取广告位的地址，
 * 2、如果地址为空，判断引导页是否展示过，若未展示，进入引导页，若展示过，获取地区名称，
 * 3、如果地区名称为空，进入选择地区界面。如果地区不为空，判断用户是否登录过，如果登录过，自动登录， 登录成功进入主页，登录失败也进入主页。若未登录过，
 * 直接以游客的身份进入主页。 4、如果广告也地址不为空，则进入广告界面
 * */
public class LogoActivity extends BaseActivity {
	private View imageView;
	private SysInitInfo infor; // 系统初始化
	private User user;

	private boolean isShowed;// 展示页是否看过，预留下来，现在默认为已经展示过了
	private boolean isAutomaticLogin = false;// 是否自动登录
	private boolean isSelectCity = false;// 是否已经选择了城市

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_logo);
		super.onCreate(savedInstanceState);
		requestPermission();
		infor = getApplicationContext().getSysInitInfo();
		BaseLocation.getInstance().startLocation();
		init();
	}
	private void requestPermission() {
		if (Build.VERSION.SDK_INT >= 23) {
			if ((ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
					|| (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
					|| (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
					|| (ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)) {
				ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
						Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, 1);
			}
		}
	}

	private void init() {
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.logo);
		animation.setAnimationListener(new StartAnimationListener());
		imageView.startAnimation(animation);
	}


	// 检查是否已经展示过引导页界面了
	private boolean isShow() {
		isShowed = "true".equals(XtomSharedPreferencesUtil.get(mContext,
				"isShowed"));
		return true;
	}


	// 检查是否自动登录
	private boolean isAutoLogin() {
		isAutomaticLogin = "true".equals(XtomSharedPreferencesUtil.get(
				mContext, "isAutoLogin"));
		return isAutomaticLogin;
	}
	//判断是否选择了城市
	private boolean isSelectCity() {
		String city = getCityName();
		isSelectCity = isNull(city);
		return isSelectCity;
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
		case INIT:
			HemaArrayResult<SysInitInfo> sResult = (HemaArrayResult<SysInitInfo>) baseResult;
			infor = sResult.getObjects().get(0);
			getApplicationContext().setSysInitInfo(infor);
			toAdvertisement();
			break;
		case CLIENT_LOGIN:
			HemaArrayResult<User> uResult = (HemaArrayResult<User>) baseResult;
			user = uResult.getObjects().get(0);
			getApplicationContext().setUser(user);
			if (isNull(infor.getStart_img()))
				toMain();
			else {
				Intent it = new Intent(mContext, AdvertiseActivity.class);
				it.putExtra("imgurl",infor.getStart_img());
				it.putExtra("url",infor.getStart_url());
				startActivity(it);
				finish();
			}
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
	private void toAdvertisement() {
		if (!isNull(infor.getStart_img())){
			if (isAutoLogin()) {
				String username = XtomSharedPreferencesUtil.get(this, "username");
				String password = XtomSharedPreferencesUtil.get(this, "password");
				if (!isNull(username) && !isNull(password)) {
					BaseNetWorker netWorker = getNetWorker();
					netWorker.clientLogin(username, password);
				}
			} else {
				Intent it = new Intent(mContext, AdvertiseActivity.class);
				it.putExtra("imgurl",infor.getStart_img());
				it.putExtra("url",infor.getStart_url());
				startActivity(it);
				finish();
			}
		}else {
			if (!isShow()) {
				Intent it = new Intent(mContext, ShowActivity.class);
				startActivity(it);
				finish();
			} else {
				//判断是否自动登录
				if (isAutoLogin()) {
					String username = XtomSharedPreferencesUtil.get(this, "username");
					String password = XtomSharedPreferencesUtil.get(this, "password");
					if (!isNull(username) && !isNull(password)) {
						BaseNetWorker netWorker = getNetWorker();
						netWorker.clientLogin(username, password);
						//	XtomToastUtil.showShortToast(mContext, "登录");
					} else if (HemaUtil.isThirdSave(mContext)) {// 如果是第三方登录
						BaseNetWorker netWorker = getNetWorker();
						netWorker.thirdSave();
					} else {
						toMain();
					}
				} else {
					toMain();
				}
			}
		}
	}

	private void toMain(){
		Intent it = new Intent(mContext, MainActivity.class);
		startActivity(it);
		finish();
	}
	private void toLogin(){
		Intent it = new Intent(mContext, LoginActivity.class);
		startActivity(it);
		finish();
	}

	@Override
	protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
		BaseHttpInformation information = (BaseHttpInformation) netTask
				.getHttpInformation();
		switch (information) {
		case INIT:
			showTextDialog("获取系统初始化信息失败啦\n请检查网络连接重试");
			break;
		case CLIENT_LOGIN:
			//showTextDialog("登录失败");
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
		case INIT:
			getInitFailed();
			break;
		case CLIENT_LOGIN:
		case THIRD_SAVE:
			toMain();
			break;
		default:
			break;
		}
	}

	private void getInitFailed() {
		if (infor != null) {
			toAdvertisement();
		} else {
			showTextDialog("获取系统初始化信息失败啦\n请检查网络连接重试");
		}
	}
	@Override
	protected void findView() {
		imageView = findViewById(R.id.imageview);
	}

	@Override
	protected void getExras() {
	}

	@Override
	protected void setListener() {
	}

	private class StartAnimationListener implements AnimationListener {

		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			BaseNetWorker netWorker = getNetWorker();
			netWorker.init();
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}
	}
}
