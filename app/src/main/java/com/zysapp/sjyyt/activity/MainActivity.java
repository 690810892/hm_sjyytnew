package com.zysapp.sjyyt.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.PushService;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseFragmentActivity;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.UpGrade;
import com.zysapp.sjyyt.model.Song;
import com.zysapp.sjyyt.model.User;
import com.zysapp.sjyyt.newgetui.GeTuiIntentService;
import com.zysapp.sjyyt.newgetui.PushUtils;
import com.zysapp.sjyyt.util.EventBusConfig;
import com.zysapp.sjyyt.util.EventBusModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import xtom.frame.util.XtomDeviceUuidFactory;
/*
666666666666666666666666
 */
public class MainActivity extends BaseFragmentActivity {

    @BindView(R.id.start)
    TextView start;
    @BindView(R.id.danmu)
    TextView danmu;
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
        user = BaseApplication.getInstance().getUser();
    }

    public void onEventMainThread(EventBusModel event) {
        switch (event.getType()) {
            case REFRESH_MAIN_DATA:
                break;
            case CLIENT_ID:
                saveDevice(event.getContent());
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        allowUnbindService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        upGrade.check();
        checkPermission();
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
        danmu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(mContext,DanMuActivity.class);
                startActivity(it);
            }
        });
    }


//    public void ChangeFragment(Class<? extends Fragment> c) {
//        FragmentManager manager = getSupportFragmentManager();
//        String tag = c.getName();
//        FragmentTransaction transaction = manager.beginTransaction();
//        Fragment fragment = manager.findFragmentByTag(tag);
//
//        if (fragment == null) {
//            try {
//                fragment = c.newInstance();
//                // 替换时保留Fragment,以便复用
//                transaction.add(R.id.fragment_contentview, fragment, tag);
//            } catch (Exception e) {
//            }
//        } else {
//        }
//
//        // 遍历存在的Fragment,隐藏其他Fragment
//        List<Fragment> fragments = manager.getFragments();
//        if (fragments != null)
//            for (Fragment fm : fragments)
//                if (!fm.equals(fragment))
//                    transaction.hide(fm);
//
//        transaction.show(fragment);
//        //  transaction.commit();
//        transaction.commitAllowingStateLoss();
//    }

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

    }

    @Override
    public void onChange(int position) {

    }

    @OnClick(R.id.start)
    public void onViewClicked() {
        Song song = new Song();
        song.setAvatar("");
        song.setContent("张延山");
        song.setName("双节棍");
        song.setPath("http://sc1.111ttt.com/2015/1/06/06/99060941326.mp3");
        ArrayList<Song> songs = new ArrayList<>();
        songs.add(song);
        EventBus.getDefault().post(new EventBusModel(EventBusConfig.PLAY, songs, 0));
    }
}
