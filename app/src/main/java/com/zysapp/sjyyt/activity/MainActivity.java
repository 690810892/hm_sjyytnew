package com.zysapp.sjyyt.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.PushService;
import com.zysapp.sjyyt.BaseAppCompatActivity;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseFragmentActivity;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.UpGrade;
import com.zysapp.sjyyt.fragment.CenterFragment;
import com.zysapp.sjyyt.fragment.FirstPageFragment;
import com.zysapp.sjyyt.fragment.PlayBackFragment;
import com.zysapp.sjyyt.fragment.ReplyFragment;
import com.zysapp.sjyyt.model.ID;
import com.zysapp.sjyyt.model.User;
import com.zysapp.sjyyt.newgetui.GeTuiIntentService;
import com.zysapp.sjyyt.newgetui.PushUtils;
import com.zysapp.sjyyt.service.PlayerService;
import com.zysapp.sjyyt.util.EventBusConfig;
import com.zysapp.sjyyt.util.EventBusModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import de.greenrobot.event.EventBus;
import xtom.frame.util.XtomDeviceUuidFactory;

/*

 */
public class MainActivity extends BaseFragmentActivity {

    @BindView(R.id.fragment_contentview)
    FrameLayout fragmentContentview;
    @BindView(R.id.iv_first)
    ImageView ivFirst;
    @BindView(R.id.tv_first)
    TextView tvFirst;
    @BindView(R.id.rl_first)
    RelativeLayout rlFirst;
    @BindView(R.id.iv_playback)
    ImageView ivPlayback;
    @BindView(R.id.tv_playback)
    TextView tvPlayback;
    @BindView(R.id.rl_playback)
    RelativeLayout rlPlayback;
    @BindView(R.id.iv_play)
    ImageButton ivPlay;
    @BindView(R.id.rl_play)
    RelativeLayout rlPlay;
    @BindView(R.id.iv_reply)
    ImageView ivReply;
    @BindView(R.id.tv_reply)
    TextView tvReply;
    @BindView(R.id.rl_reply)
    RelativeLayout rlReply;
    @BindView(R.id.iv_center)
    ImageView ivCenter;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    @BindView(R.id.rl_center)
    RelativeLayout rlCenter;
    @BindView(R.id.point_message)
    View pointMessage;
    @BindView(R.id.layout)
    LinearLayout layout;
    private UpGrade upGrade;
    private long time;// 用于判断二次点击返回键的时间间隔

    private User user;
    private static MainActivity activity;
    private int cart = 0;

    public static MainActivity getInstance() {
        return activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        upGrade = new UpGrade(mContext);
        ShareSDK.initSDK(this);
        user = BaseApplication.getInstance().getUser();
        if (user != null)
            getNetWorker().unreadGet(user.getToken());
        ChangeFragment(CenterFragment.class);
        ChangeFragment(FirstPageFragment.class);
    }

    public void onEventMainThread(EventBusModel event) {
        switch (event.getType()) {
            case REFRESH_MAIN_DATA:
                break;
            case CLIENT_ID:
                saveDevice(event.getContent());
                break;
            case NEW_MESSAGE:
                if (user != null)
                    getNetWorker().unreadGet(user.getToken());
                break;
            case MAIN_SEEK:
                mPlayService.seek(event.getCode());
                break;
            case STATE_PLAY:
                ivPlay.setImageResource(R.mipmap.main_pause);
                break;
            case STATE_PAUSE:
                ivPlay.setImageResource(R.mipmap.main_play);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        allowUnbindService();
        ShareSDK.stopSDK(this);
    }

    @Override
    protected void onResume() {
        checkPermission();
        super.onResume();
        upGrade.check();
        allowBindService();
    }

    @Override
    protected boolean onKeyBack() {
        moveTaskToBack(false);
        return true;
    }

    @Override
    protected void findView() {
    }

    @Override
    protected void getExras() {

    }


    @Override
    protected void setListener() {
//        danmu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent it = new Intent(mContext, DanMuActivity.class);
//                startActivity(it);
//            }
//        });
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent it = new Intent(mContext, LoginActivity.class);
//                startActivity(it);
//            }
//        });
    }


    public void ChangeFragment(Class<? extends Fragment> c) {
        FragmentManager manager = getSupportFragmentManager();
        String tag = c.getName();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = manager.findFragmentByTag(tag);

        if (fragment == null) {
            try {
                fragment = c.newInstance();
                // 替换时保留Fragment,以便复用
                transaction.add(R.id.fragment_contentview, fragment, tag);
            } catch (Exception e) {
            }
        } else {
        }

        // 遍历存在的Fragment,隐藏其他Fragment
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null)
            for (Fragment fm : fragments)
                if (!fm.equals(fragment))
                    transaction.hide(fm);

        transaction.show(fragment);
        //  transaction.commit();
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask hemaNetTask) {

    }

    @Override
    protected void callAfterDataBack(HemaNetTask hemaNetTask) {

    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask hemaNetTask, HemaBaseResult hemaBaseResult) {
        BaseHttpInformation information = (BaseHttpInformation) hemaNetTask
                .getHttpInformation();
        switch (information) {
            case CLIENT_SAVE:
                HemaArrayResult<User> uResult = (HemaArrayResult<User>) hemaBaseResult;
                user = uResult.getObjects().get(0);
                BaseApplication.getInstance().setUser(user);
                break;
            case UNREAD_GET:
                HemaArrayResult<ID> TResult = (HemaArrayResult<ID>) hemaBaseResult;
                String count = TResult.getObjects().get(0).getNum();
                if (isNull(count) || count.equals("0"))
                    pointMessage.setVisibility(View.INVISIBLE);
                else
                    pointMessage.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void callBackForServerFailed(HemaNetTask hemaNetTask, HemaBaseResult hemaBaseResult) {

    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask hemaNetTask, int i) {

    }

    /*个推相关*/
    // DemoPushService.class 自定义服务名称, 核心服务
    private Class userPushService = PushService.class;
    private static final int REQUEST_PERMISSION = 0;

    private void checkPermission() {
        PackageManager pkgManager = getPackageManager();

        // 读写 sd card 权限非常重要, android6.0默认禁止的, 建议初始化之前就弹窗让用户赋予该权限
        boolean sdCardWritePermission =
                pkgManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        // read phone state用于获取 imei 设备信息
        boolean phoneSatePermission =
                pkgManager.checkPermission(Manifest.permission.READ_PHONE_STATE, getPackageName()) == PackageManager.PERMISSION_GRANTED;

        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission || !phoneSatePermission) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},
                    REQUEST_PERMISSION);
        } else {
            log_d("hhahahah------------------------------------------------------");
            PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
        }

        // 注册 intentService 后 PushDemoReceiver 无效, sdk 会使用 DemoIntentService 传递数据,
        // AndroidManifest 对应保留一个即可(如果注册 DemoIntentService, 可以去掉 PushDemoReceiver, 如果注册了
        // IntentService, 必须在 AndroidManifest 中声明)
        PushManager.getInstance().initialize(mContext.getApplicationContext(), PushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), GeTuiIntentService.class);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
            } else {
                Log.e("MainActivity", "We highly recommend that you need to grant the special permissions before initializing the SDK, otherwise some "
                        + "functions will not work");
                PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void saveDevice(String channelId) {
        User user = getApplicationContext().getUser();
        if (user == null) {
            return;
        }
        String deviceId = PushUtils.getUserId(mContext);
        if (isNull(deviceId)) {// 如果deviceId为空时，保存为手机串号
            deviceId = XtomDeviceUuidFactory.get(mContext);
        }
        getNetWorker().deviceSave(user.getToken(),
                deviceId, "2", channelId);
    }

    /*个推相关结束*/
    @Override
    public void onPublish(int progress) {
        EventBus.getDefault().post(new EventBusModel(EventBusConfig.onPublish, progress));
    }

    @Override
    public void onChange(int position) {
        EventBus.getDefault().post(new EventBusModel(EventBusConfig.REFRESH_SONG, position));
    }

    @OnClick({R.id.rl_first, R.id.rl_playback, R.id.iv_play, R.id.rl_reply, R.id.rl_center})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_first:
                ivFirst.setImageResource(R.mipmap.play_p);
                ivPlayback.setImageResource(R.mipmap.playback_n);
                ivReply.setImageResource(R.mipmap.notice_n);
                ivCenter.setImageResource(R.mipmap.center_n);
                ChangeFragment(FirstPageFragment.class);
                break;
            case R.id.rl_playback:
                ivFirst.setImageResource(R.mipmap.play_n);
                ivPlayback.setImageResource(R.mipmap.playback_p);
                ivReply.setImageResource(R.mipmap.notice_n);
                ivCenter.setImageResource(R.mipmap.center_n);
                ChangeFragment(PlayBackFragment.class);
                break;
            case R.id.iv_play:
                if (mPlayService.isPlaying()){
                    mPlayService.pause();
                }else {
                    mPlayService.resume();
                }
                break;
            case R.id.rl_reply:
                ivFirst.setImageResource(R.mipmap.play_n);
                ivPlayback.setImageResource(R.mipmap.playback_n);
                ivReply.setImageResource(R.mipmap.notice_p);
                ivCenter.setImageResource(R.mipmap.center_n);
                ChangeFragment(ReplyFragment.class);
                break;
            case R.id.rl_center:
                ivFirst.setImageResource(R.mipmap.play_n);
                ivPlayback.setImageResource(R.mipmap.playback_n);
                ivReply.setImageResource(R.mipmap.notice_n);
                ivCenter.setImageResource(R.mipmap.center_p);
                ChangeFragment(CenterFragment.class);
                break;
        }
    }
}
