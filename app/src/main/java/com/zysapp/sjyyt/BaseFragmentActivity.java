package com.zysapp.sjyyt;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.hemaapp.hm_FrameWork.HemaAppCompatActivity;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.HemaNetWorker;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.zysapp.sjyyt.activity.LoginActivity;
import com.zysapp.sjyyt.service.PlayerService;

import xtom.frame.XtomActivityManager;
import xtom.frame.net.XtomNetWorker;
import xtom.frame.util.XtomSharedPreferencesUtil;

/**
 * 
 */
public abstract class BaseFragmentActivity extends HemaAppCompatActivity {
	public PlayerService mPlayService;
	private ServiceConnection mPlayServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mPlayService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mPlayService = ((PlayerService.PlayBinder) service).getService();
			mPlayService.setOnMusicEventListener(mMusicEventListener);
			onChange(mPlayService.getPlayingPosition());
		}
	};
	/**
	 * 音乐播放服务回调接口的实现类
	 */
	private PlayerService.OnMusicEventListener mMusicEventListener =
			new PlayerService.OnMusicEventListener() {
				@Override
				public void onPublish(int progress) {
					BaseFragmentActivity.this.onPublish(progress);
				}

				@Override
				public void onChange(int position) {
					BaseFragmentActivity.this.onChange(position);
				}
			};
	/**
	 * Fragment的view加载完成后回调
	 *
	 * 注意：
	 * allowBindService()使用绑定的方式启动歌曲播放的服务
	 * allowUnbindService()方法解除绑定
	 *
	 * 在SplashActivity.java中使用startService()方法启动过该音乐播放服务了
	 * 那么大家需要注意的事，该服务不会因为调用allowUnbindService()方法解除绑定
	 * 而停止。
	 */
	public void allowBindService() {
		getApplicationContext().bindService(new Intent(this, PlayerService.class),
				mPlayServiceConnection,
				Context.BIND_AUTO_CREATE);
	}

	/**
	 * fragment的view消失后回调
	 */
	public void allowUnbindService() {
		getApplicationContext().unbindService(mPlayServiceConnection);
	}
	/**
	 * 更新进度
	 * 抽象方法由子类实现
	 * 实现service与主界面通信
	 * @param progress 进度
	 */
	public abstract void onPublish(int progress);
	/**
	 * 切换歌曲
	 * 抽象方法由子类实现
	 * 实现service与主界面通信
	 * @param position 歌曲在list中的位置
	 */
	public abstract void onChange(int position);
	@Override
	protected HemaNetWorker initNetWorker() {
		return new BaseNetWorker(mContext);
	}

	@Override
	public BaseNetWorker getNetWorker() {
		return (BaseNetWorker) super.getNetWorker();
	}

	@Override
	public BaseApplication getApplicationContext() {
		return (BaseApplication) super.getApplicationContext();
	}
	
	@Override
	public boolean onAutoLoginFailed(HemaNetWorker netWorker,
                                     HemaNetTask netTask, int failedType, HemaBaseResult baseResult) {
		switch (failedType) {
		case 0:// 服务器处理失败
			int error_code = baseResult.getError_code();
			switch (error_code) {
			case 102:// 密码错误
				XtomActivityManager.finishAll();
				Intent it = new Intent(mContext, LoginActivity.class);
				startActivity(it);
				return true;
			default:
				break;
			}
		case XtomNetWorker.FAILED_HTTP:// 网络异常
		case XtomNetWorker.FAILED_DATAPARSE:// 数据异常
		case XtomNetWorker.FAILED_NONETWORK:// 无网络
			break;
		}
		return false;
	}

	// ------------------------下面填充项目自定义方法---------------------------

	/**
	 * 保存城市名称
	 * 
	 * @param cityName
	 */
	public void saveCityName(String cityName) {
		XtomSharedPreferencesUtil.save(mContext, "city_name", cityName);
	}

	/**
	 * @return 获取城市名称
	 */
	public String getCityName() {
		if (isNull(XtomSharedPreferencesUtil.get(this, "city_name"))) {
			return "济南市";
		}else {
			return XtomSharedPreferencesUtil.get(this, "city_name");
		}
	}

	/**
	 * 保存城市id
	 * 
	 * @param cityId
	 */
	public void saveCityId(String cityId) {
		XtomSharedPreferencesUtil.save(mContext, "city_id", cityId);
	}

	/**
	 * @return 获取城市id
	 */
	public String getCityId() {
		return XtomSharedPreferencesUtil.get(mContext, "city_id");
	}

	/**
	 * 保存地区名称(此地区名称为用户最终获取数据的地区)
	 * 
	 * @param districtName
	 */
	public void saveDistrictName(String districtName) {
		XtomSharedPreferencesUtil.save(mContext, "district_name",
				districtName);
	}

	/**
	 * @return 获取地区名称(此地区名称为用户最终获取数据的地区)
	 */
	public String getDistrictName() {
		return XtomSharedPreferencesUtil.get(mContext, "district_name");
	}
	
}
