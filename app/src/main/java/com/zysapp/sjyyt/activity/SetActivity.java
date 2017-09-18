package com.zysapp.sjyyt.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.HemaUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayParse;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.zysapp.sjyyt.BaseActivity;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.BaseUtil;
import com.zysapp.sjyyt.ToLogin;
import com.zysapp.sjyyt.model.SysInitInfo;
import com.zysapp.sjyyt.model.User;
import com.zysapp.sjyyt.util.EventBusModel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import de.greenrobot.event.EventBus;
import xtom.frame.XtomActivityManager;
import xtom.frame.image.cache.XtomImageCache;
import xtom.frame.media.XtomVoicePlayer;
import xtom.frame.util.XtomFileUtil;
import xtom.frame.util.XtomSharedPreferencesUtil;

/**
 * 设置
 */
public class SetActivity extends BaseActivity implements PlatformActionListener {


    @BindView(R.id.title_btn_left)
    ImageButton titleBtnLeft;
    @BindView(R.id.title_btn_right)
    Button titleBtnRight;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.tv_aboutapp)
    TextView tvAboutapp;
    @BindView(R.id.tv_advice)
    TextView tvAdvice;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.lv_version)
    LinearLayout lvVersion;
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.lv_clear)
    LinearLayout lvClear;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    private User user;
    private String token = "";
    private PopupWindow mWindow_exit;
    private ViewGroup mViewGroup_exit;
    private String phone;
    private String sys_plugins;
    private String pathWX;
    private String imageurl;
    private OnekeyShare oks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        phone = BaseApplication.getInstance().getSysInitInfo().getSys_service_phone();
        user = BaseApplication.getInstance().getUser();
        if (user == null)
            token = "";
        else {
            token = user.getToken();
        }
        // 获取图片的缓存
        long size1 = XtomImageCache.getInstance(mContext).getCacheSize();
        // 获取音频缓存
        long size2 = XtomVoicePlayer.getInstance(mContext).getCacheSize();
        long size = size1 + size2;
        String content = BaseUtil.getSize(size);
        tvCache.setText(content);
        String version = HemaUtil.getAppVersionForTest(mContext);
        tvVersion.setText(version);
        SysInitInfo initInfo = getApplicationContext()
                .getSysInitInfo();
        sys_plugins = initInfo.getSys_plugins();
    }

    public void onEventMainThread(EventBusModel event) {
        switch (event.getType()) {
            case REFRESH_USER:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        // TODO Auto-generated method stub
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case CLIENT_LOGINOUT:
                showProgressDialog("正在注销");
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
            case CLIENT_SAVE:
            case CLIENT_LOGINOUT:
                cancelProgressDialog();
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
            case CLIENT_GET:
                HemaArrayParse<User> uResult = (HemaArrayParse<User>) baseResult;
                user = uResult.getObjects().get(0);
                BaseApplication.getInstance().setUser(user);
                break;
            case CLIENT_LOGINOUT:
                cancellationSuccess();
                break;
            default:
                break;
        }
    }

    private void cancellationSuccess() {
//		XtomSharedPreferencesUtil.save(this, "username", "");
        XtomSharedPreferencesUtil.save(this, "password", "");
        XtomSharedPreferencesUtil.save(mContext, "isAutoLogin", "false");
        BaseApplication.getInstance().setUser(null);
        HemaUtil.setThirdSave(this, false);// 将第三方登录标记置为false
        XtomActivityManager.finishAll();
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
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
            case CLIENT_GET:
                showTextDialog(baseResult.getMsg());
                break;
            case CLIENT_LOGINOUT:
                cancellationSuccess();
                log_i("退出登录失败");
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
            case CLIENT_GET:
                showTextDialog("加载失败");
                break;
            case CLIENT_LOGINOUT:
                cancellationSuccess();
                log_i("退出登录失败");
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
        titleText.setText("设置");
        if (user!=null&&HemaUtil.isThirdSave(mContext)){
            tvPassword.setVisibility(View.GONE);
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

    @OnClick({R.id.title_btn_left, R.id.title_btn_right, R.id.tv_password, R.id.tv_aboutapp, R.id.tv_advice, R.id.tv_share, R.id.tv_version, R.id.lv_version, R.id.lv_clear, R.id.tv_logout})
    public void onViewClicked(View view) {
        String sys_web_service = getApplicationContext().getSysInitInfo()
                .getSys_web_service();
        Intent it;
        String path;
        user = BaseApplication.getInstance().getUser();
        switch (view.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
            case R.id.title_btn_right:
                break;
            case R.id.tv_password:
                if (user == null) {
                    ToLogin.showLogin(mContext);
                    break;
                }
                it = new Intent(mContext, ResetPassword1Activity.class);
                startActivity(it);
                break;
            case R.id.tv_aboutapp:
                path = sys_web_service + "webview/parm/aboutus";
                it = new Intent(mContext, ShowInternetPageActivity.class);
                it.putExtra("path", path);
                it.putExtra("name", "关于软件");
                startActivity(it);
                break;
            case R.id.tv_advice:
                if (user == null) {
                    ToLogin.showLogin(mContext);
                    break;
                }
                it = new Intent(mContext, AdviceActivity.class);
                startActivity(it);
                break;
            case R.id.tv_share:
                if (user == null) {
                    ToLogin.showLogin(mContext);
                    break;
                }
                share();
                break;
            case R.id.tv_version:
                break;
            case R.id.lv_version:
                break;
            case R.id.lv_clear:
                new ClearTask().execute();
                break;
            case R.id.tv_logout:
                if (user == null) {
                    ToLogin.showLogin(mContext);
                    break;
                }
                loginoutDialog();
                break;
        }
    }

    private class ClearTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // 删除图片缓存
            XtomImageCache.getInstance(mContext).deleteCache();
            // 删除语音缓存
            XtomVoicePlayer player = XtomVoicePlayer.getInstance(mContext);
            player.deleteCache();
            player.release();
            return null;
        }

        @Override
        protected void onPreExecute() {
            showProgressDialog("正在清除缓存");
        }

        @Override
        protected void onPostExecute(Void result) {
            cancelProgressDialog();
            showTextDialog("清除完成");
            tvCache.setText("0KB");
        }
    }

    private void telDialog() {
        if (mWindow_exit != null) {
            mWindow_exit.dismiss();
        }
        mWindow_exit = new PopupWindow(mContext);
        mWindow_exit.setWidth(FrameLayout.LayoutParams.MATCH_PARENT);
        mWindow_exit.setHeight(FrameLayout.LayoutParams.MATCH_PARENT);
        mWindow_exit.setBackgroundDrawable(new BitmapDrawable());
        mWindow_exit.setFocusable(true);
        mViewGroup_exit = (ViewGroup) LayoutInflater.from(mContext).inflate(
                R.layout.pop_content, null);
        TextView bt_ok = (TextView) mViewGroup_exit.findViewById(R.id.tv_ok);
        TextView bt_cancel = (TextView) mViewGroup_exit.findViewById(R.id.tv_cancel);
        TextView content = (TextView) mViewGroup_exit.findViewById(R.id.tv_title);
        content.setText(phone);
        bt_ok.setText("拨打");
        mWindow_exit.setContentView(mViewGroup_exit);
        mWindow_exit.showAtLocation(mViewGroup_exit, Gravity.CENTER, 0, 0);
        bt_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mWindow_exit.dismiss();
            }
        });
        bt_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                        + phone));
                startActivity(intent);
                mWindow_exit.dismiss();
            }
        });
    }

    private void loginoutDialog() {
        if (mWindow_exit != null) {
            mWindow_exit.dismiss();
        }
        mWindow_exit = new PopupWindow(mContext);
        mWindow_exit.setWidth(FrameLayout.LayoutParams.MATCH_PARENT);
        mWindow_exit.setHeight(FrameLayout.LayoutParams.MATCH_PARENT);
        mWindow_exit.setBackgroundDrawable(new BitmapDrawable());
        mWindow_exit.setFocusable(true);
        mViewGroup_exit = (ViewGroup) LayoutInflater.from(mContext).inflate(
                R.layout.pop_content, null);
        TextView bt_ok = (TextView) mViewGroup_exit.findViewById(R.id.tv_ok);
        TextView bt_cancel = (TextView) mViewGroup_exit.findViewById(R.id.tv_cancel);
        TextView content = (TextView) mViewGroup_exit.findViewById(R.id.tv_title);
        mWindow_exit.setContentView(mViewGroup_exit);
        mWindow_exit.showAtLocation(mViewGroup_exit, Gravity.CENTER, 0, 0);
        bt_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mWindow_exit.dismiss();
            }
        });
        bt_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (user != null)
                    getNetWorker().clientLoginout(user.getToken());
                mWindow_exit.dismiss();
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void share() {
        if (mWindow_exit != null) {
            mWindow_exit.dismiss();
        }
        mWindow_exit = new PopupWindow(mContext);
        mWindow_exit.setWidth(FrameLayout.LayoutParams.MATCH_PARENT);
        mWindow_exit.setHeight(FrameLayout.LayoutParams.MATCH_PARENT);
        mWindow_exit.setBackgroundDrawable(new BitmapDrawable());
        mWindow_exit.setFocusable(true);
        mWindow_exit.setAnimationStyle(R.style.PopupAnimation);
        mViewGroup_exit = (ViewGroup) LayoutInflater.from(mContext).inflate(
                R.layout.pop_share, null);
        TextView wechat = (TextView) mViewGroup_exit.findViewById(R.id.wechat);
        TextView moment = (TextView) mViewGroup_exit.findViewById(R.id.moment);
        TextView qqshare = (TextView) mViewGroup_exit.findViewById(R.id.qq);
        TextView qzone = (TextView) mViewGroup_exit.findViewById(R.id.zone);
        TextView weibo = (TextView) mViewGroup_exit.findViewById(R.id.weibo);
        TextView cancel = (TextView) mViewGroup_exit.findViewById(R.id.tv_cancel);
        mWindow_exit.setContentView(mViewGroup_exit);
        mWindow_exit.showAtLocation(mViewGroup_exit, Gravity.CENTER, 0, 0);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mWindow_exit.dismiss();
            }
        });
        qqshare.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showShare(QQ.NAME);
                mWindow_exit.dismiss();
            }
        });
        wechat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showShare(Wechat.NAME);
                mWindow_exit.dismiss();
            }
        });
        moment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showShare(WechatMoments.NAME);
                mWindow_exit.dismiss();
            }
        });
        qzone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showShare(QZone.NAME);
                mWindow_exit.dismiss();
            }
        });
        weibo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showShare(SinaWeibo.NAME);
                mWindow_exit.dismiss();
            }
        });

    }

    private void showShare(String platform) {
        pathWX = sys_plugins + "share/sdk.php?id=0&keytype=0";
        imageurl = initImagePath();
        if (oks == null) {
            oks = new OnekeyShare();
            oks.setTitle("手机音乐台");
            oks.setTitleUrl(pathWX); // 标题的超链接
            oks.setText("手机音乐台软件");
            oks.setImageUrl(imageurl);
            oks.setFilePath(imageurl);
            oks.setImagePath(imageurl);
            oks.setUrl(pathWX);
            oks.setSiteUrl(pathWX);
            oks.setCallback(this);
        }
        oks.setPlatform(platform);
        oks.show(mContext);
    }

    @Override
    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> hashMap) {
        if (arg0.getName().equals(Wechat.NAME)) {// 判断成功的平台是不是微信
            handler.sendEmptyMessage(1);
        }
        if (arg0.getName().equals(WechatMoments.NAME)) {// 判断成功的平台是不是微信朋友圈
            handler.sendEmptyMessage(2);
        }
        if (arg0.getName().equals(QQ.NAME)) {// 判断成功的平台是不是QQ
            handler.sendEmptyMessage(3);
        }
        if (arg0.getName().equals(QZone.NAME)) {// 判断成功的平台是不是空间
            handler.sendEmptyMessage(4);
        }
        if (arg0.getName().equals(WechatFavorite.NAME)) {// 判断成功的平台是不是微信收藏
            handler.sendEmptyMessage(5);
        }
        if (arg0.getName().equals(SinaWeibo.NAME)) {// 判断成功的平台是不是微博
            handler.sendEmptyMessage(8);
        }
    }

    @Override
    public void onError(Platform arg0, int arg1, Throwable arg2) {
        arg2.printStackTrace();
        Message msg = new Message();
        msg.what = 6;
        msg.obj = arg2.getMessage();
        handler.sendMessage(msg);

    }

    @Override
    public void onCancel(Platform platform, int i) {
        handler.sendEmptyMessage(7);
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplicationContext(), "微信分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), "朋友圈分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), "QQ分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    Toast.makeText(getApplicationContext(), "QQ空间分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 5:
                    Toast.makeText(getApplicationContext(), "微信收藏分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 8:
                    Toast.makeText(getApplicationContext(), "微博分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 7:
                    Toast.makeText(getApplicationContext(), "取消分享", Toast.LENGTH_LONG).show();
                    break;
                case 6:
                    Toast.makeText(getApplicationContext(), "分享失败", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }

    };

    private String initImagePath() {
        String imagePath;
        try {

            String cachePath_internal = XtomFileUtil.getCacheDir(mContext)
                    + "images/";// 获取缓存路径
            File dirFile = new File(cachePath_internal);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            imagePath = cachePath_internal + "share.png";
            File file = new File(imagePath);
            if (!file.exists()) {
                file.createNewFile();
                Bitmap pic;

                pic = BitmapFactory.decodeResource(mContext.getResources(),
                        R.mipmap.ic_launcher);

                FileOutputStream fos = new FileOutputStream(file);
                pic.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            }
        } catch (Throwable t) {
            t.printStackTrace();
            imagePath = null;
        }
        log_i("imagePath:" + imagePath);
        return imagePath;
    }
}
