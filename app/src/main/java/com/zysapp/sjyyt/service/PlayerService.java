package com.zysapp.sjyyt.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import com.zysapp.sjyyt.BaseUtil;
import com.zysapp.sjyyt.activity.MainActivity;
import com.zysapp.sjyyt.activity.R;
import com.zysapp.sjyyt.model.Song;
import com.zysapp.sjyyt.player.MusicPlayer;
import com.zysapp.sjyyt.util.EventBusConfig;
import com.zysapp.sjyyt.util.EventBusModel;
import com.zysapp.sjyyt.util.ImageTools;
import com.zysapp.sjyyt.util.MusicIconLoader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.greenrobot.event.EventBus;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import xtom.frame.util.XtomToastUtil;

public class PlayerService extends Service implements
        IjkMediaPlayer.OnCompletionListener {
    private PowerManager.WakeLock mWakeLock = null;//获取设备电源锁，防止锁屏后服务被停止
    private Notification notification;//通知栏
    private RemoteViews remoteViews;//通知栏布局
    private NotificationManager notificationManager;
    //    private MediaPlayer mPlayer;
    IjkMediaPlayer mPlayer;
    private ArrayList<Song> mQueue = new ArrayList<>();
    private int mQueueIndex = 0, playType = 0, typeid = 0;
    private PlayerService.OnMusicEventListener mListener;
    // 单线程池
    private ExecutorService mProgressUpdatedListener = Executors
            .newSingleThreadExecutor();

    @Override
    public void onCompletion(IMediaPlayer mp) {
        next();
    }

    public class PlayBinder extends Binder {
        public PlayerService getService() {
            return PlayerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
//        throw new UnsupportedOperationException("Not yet implemented");
        return new PlayerService.PlayBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        acquireWakeLock();//获取设备电源锁
        // 开始更新进度的线程
        mProgressUpdatedListener.execute(mPublishProgressRunnable);
        mPlayer = new IjkMediaPlayer();
        mPlayer.setOnCompletionListener(this);
        PendingIntent pendingIntent = PendingIntent
                .getActivity(PlayerService.this,
                        0, new Intent(PlayerService.this, MainActivity.class), 0);
        remoteViews = new RemoteViews(getPackageName(),
                R.layout.play_notification);
        notification = new Notification(R.drawable.ic_launcher,
                "歌曲正在播放", System.currentTimeMillis());
        notification.contentIntent = pendingIntent;
        notification.contentView = remoteViews;
        //标记位，设置通知栏一直存在
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        Intent intent = new Intent(PlayerService.class.getSimpleName());
        intent.putExtra("BUTTON_NOTI", 1);
        PendingIntent preIntent = PendingIntent.getBroadcast(
                PlayerService.this,
                1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(
                R.id.music_play_pre, preIntent);

        intent.putExtra("BUTTON_NOTI", 2);
        PendingIntent pauseIntent = PendingIntent.getBroadcast(
                PlayerService.this,
                2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(
                R.id.music_play_pause, pauseIntent);

        intent.putExtra("BUTTON_NOTI", 3);
        PendingIntent nextIntent = PendingIntent.getBroadcast
                (PlayerService.this,
                        3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(
                R.id.music_play_next, nextIntent);

        intent.putExtra("BUTTON_NOTI", 4);
        PendingIntent exit = PendingIntent.getBroadcast(PlayerService.this,
                4, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(
                R.id.music_play_notifi_exit, exit);

        notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        setRemoteViews();
/**
 * 注册广播接收者
 * 功能：
 * 监听通知栏按钮点击事件
 */
        IntentFilter filter = new IntentFilter(
                PlayerService.class.getSimpleName());
        PlayerService.MyBroadCastReceiver receiver = new MyBroadCastReceiver();
        registerReceiver(receiver, filter);
    }

    public void setRemoteViews() {
        if (mQueue.size() > 0) {
            remoteViews.setTextViewText(R.id.music_name,
                    mQueue.get(mQueueIndex).getName());
            remoteViews.setTextViewText(R.id.music_author,
                    mQueue.get(mQueueIndex).getAuthor());
            Bitmap icon = MusicIconLoader.getInstance().load(
                    mQueue.get(mQueueIndex).getAuthor_imgurl());
            remoteViews.setImageViewBitmap(R.id.music_icon, icon == null
                    ? ImageTools.scaleBitmap(R.mipmap.ic_launcher)
                    : ImageTools.scaleBitmap(icon));

        }
        if (isPlaying()) {
            remoteViews.setImageViewResource(R.id.music_play_pause,
                    R.mipmap.btn_notification_player_stop_normal);
        } else {
            remoteViews.setImageViewResource(R.id.music_play_pause,
                    R.mipmap.btn_notification_player_play_normal);
        }
        //通知栏更新
        notificationManager.notify(5, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(0, notification);//让服务前台运行
        return Service.START_STICKY;
    }

    /**
     * 更新进度的线程
     */
    private Runnable mPublishProgressRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (mPlayer != null && mPlayer.isPlaying()
                        && mListener != null) {
                    mListener.onPublish((int) mPlayer.getCurrentPosition());
                }
            /*
             * SystemClock.sleep(millis) is a utility function very similar
			 * to Thread.sleep(millis), but it ignores InterruptedException.
			 * Use this function for delays if you do not use
			 * Thread.interrupt(), as it will preserve the interrupted state
			 * of the thread. 这种sleep方式不会被Thread.interrupt()所打断
			 */
                SystemClock.sleep(1000);
            }
        }
    };

    public void setOnMusicEventListener(PlayerService.OnMusicEventListener l) {
        mListener = l;
    }

    public int play(int position) {
        if (mPlayer == null) {
            mPlayer = new IjkMediaPlayer();
            mPlayer.setOnCompletionListener(this);
        }
        if (mQueue.size() <= 0) {
            return -1;
        }
        if (position < 0)
            position = 0;
        if (position >= mQueue.size())
            position = mQueue.size() - 1;
        try {
            mPlayer.reset();
            mPlayer.setDataSource(mQueue.get(position).getUrl());
            if (BaseUtil.isBefore(mQueue.get(position).getStartdate(), mQueue.get(position).getEnddate())) {
                EventBus.getDefault().post(new EventBusModel(EventBusConfig.SEEKBAR_VISIBLE));
            } else {
                EventBus.getDefault().post(new EventBusModel(EventBusConfig.SEEKBAR_INVISIBLE));
            }
            //XtomToastUtil.showLongToast(getApplicationContext(), "开始播放");
            final int p = position;
            mPlayer.prepareAsync();
            mPlayer.setOnPreparedListener(new IjkMediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(IMediaPlayer mp) {
                    start();
                    if (mListener != null)
                        mListener.onChange(p);
                }

            });

        } catch (Exception e) {
            XtomToastUtil.showLongToast(getApplicationContext(), "网络异常");
            e.printStackTrace();
        }

        mQueueIndex = position;
        setRemoteViews();
        return mQueueIndex;
    }

    public int resume() {
        if (mPlayer == null) {
            return -1;
        }
        if (isPlaying())
            return -1;
        mPlayer.start();
        EventBus.getDefault().post(new EventBusModel(EventBusConfig.STATE_PLAY));
        setRemoteViews();
        return mQueueIndex;
    }

    public int pause() {
        if (mQueue.size() == 0) {
            return -1;
        }
        if (!isPlaying())
            return -1;
        mPlayer.pause();
        EventBus.getDefault().post(new EventBusModel(EventBusConfig.STATE_PAUSE));
        setRemoteViews();
        return mQueueIndex;
    }

    public int next() {
        if (mQueueIndex >= mQueue.size() - 1) {
            return play(0);
        }
        return play(mQueueIndex + 1);
    }

    public int pre() {
        if (mQueueIndex <= 0) {
            return play(mQueue.size() - 1);
        }
        return play(mQueueIndex - 1);
    }

    public boolean isPlaying() {
        return null != mPlayer && mPlayer.isPlaying();
    }

    public int getPlayingPosition() {
        return mQueueIndex;
    }

    public int getDuration() {
        if (!isPlaying())
            return 0;
        Log.e("shichang==",mPlayer.getDuration()+"");
        return  (int)mPlayer.getDuration();

    }

    /**
     * 拖放到指定位置进行播放
     *
     * @param msec
     */
    public void seek(int msec) {
        if (!isPlaying())
            return;
        mPlayer.seekTo(msec);
    }

    /**
     * 开始播放
     */
    private void start() {
        mPlayer.start();
        EventBus.getDefault().post(new EventBusModel(EventBusConfig.STATE_PLAY));
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        if (mListener != null)
            mListener.onChange(mQueueIndex);
    }

    @Override
    public void onDestroy() {
        release();
        stopForeground(true);
        super.onDestroy();
    }

    /**
     * 服务销毁时，释放各种控件
     */
    private void release() {
        if (!mProgressUpdatedListener.isShutdown())
            mProgressUpdatedListener.shutdownNow();
        mProgressUpdatedListener = null;
        //释放设备电源锁
        releaseWakeLock();
        if (mPlayer != null)
            mPlayer.release();
        mPlayer = null;
    }

    // 申请设备电源锁
    private void acquireWakeLock() {
        if (null == mWakeLock) {
            PowerManager pm = (PowerManager) this
                    .getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                    | PowerManager.ON_AFTER_RELEASE, "");
            if (null != mWakeLock) {
                mWakeLock.acquire();
            }
        }
    }

    // 释放设备电源锁
    private void releaseWakeLock() {
        if (null != mWakeLock) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    private class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(
                    PlayerService.class.getSimpleName())) {
                switch (intent.getIntExtra("BUTTON_NOTI", 0)) {
                    case 1:
                        pre();
                        break;
                    case 2:
                        if (isPlaying()) {
                            pause(); // 暂停
                        } else {
                            resume(); // 播放
                        }
                        break;
                    case 3:
                        if (!BaseUtil.CompareTo_Date(mQueue.get(mQueueIndex).getStartdate(), mQueue.get(mQueueIndex).getEnddate())) {
                            next();
                        }

                        break;
                    case 4:
                        if (isPlaying()) {
                            pause();
                        }
                        //取消通知栏
                        notificationManager.cancel(5);
                        break;
                    default:
                        break;
                }
            }
            if (mListener != null) {
                mListener.onChange(getPlayingPosition());
            }
        }
    }

    public void onEventMainThread(EventBusModel event) {
        switch (event.getType()) {
            case PLAY:
                mQueue.clear();
                mQueue.addAll(event.getSongs());
                int position = event.getCode();
                int Type = event.getPlaytype();//频道或分类
                int TypeId = event.getTypeid();//频道或分类的id
                //判断是否当前播放的列表还是新列表
                if (Type != playType) {
                    playType = Type;
                    typeid = TypeId;
                    mQueueIndex = position;
                    play(mQueueIndex);
                } else if (TypeId != typeid) {
                    typeid = TypeId;
                    mQueueIndex = position;
                    play(mQueueIndex);
                } else if (Type == playType && TypeId == typeid) {
                    if (isPlaying()) {
                        if (mQueueIndex == position) {
//                            pause();
                            EventBus.getDefault().post(new EventBusModel(EventBusConfig.STATE_PLAY));
                        } else {
                            mQueueIndex = position;
                            play(mQueueIndex);
                        }
                    } else {
                        if (mQueueIndex == position)
                            resume();
                        else {
                            mQueueIndex = position;
                            play(mQueueIndex);
                        }
                    }
                }
                break;
            case NEXT:
                if (!BaseUtil.CompareTo_Date(mQueue.get(mQueueIndex).getStartdate(), mQueue.get(mQueueIndex).getEnddate())) {
                    next();
                }
                break;
            case PRE:
                pre();
                break;
        }
    }

//    @Override
//    public void onCompletion(MediaPlayer mediaPlayer) {
//        next();
//    }

    /**
     * 音乐播放回调接口
     */
    public interface OnMusicEventListener {
        public void onPublish(int percent);

        public void onChange(int position);
    }

    public ArrayList<Song> getmQueue() {
        return mQueue;
    }

    public int getmQueueIndex() {
        return mQueueIndex;
    }

    /**
     * 解析m3u8格式地址
     */
    private String parsePlaylistFile(String url) {
        String filePath = "";
        // 为了从Web获取M3U文件，可以使用Apache软件基金会的HttpClient库，
        // 首先创建一个HttpClient对象，其代表类似Web浏览器的事物；
        HttpClient httpClient = new DefaultHttpClient();
        // 然后创建一个HttpGet对象，其表示指向一个文件的具体请求。
        HttpGet getRequest = new HttpGet(url);
        // HttpClient将执行HttpGet，并返回一个HttpResponse
        try {
            HttpResponse httpResponse = httpClient.execute(getRequest);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                Log.v("HTTP ERROR", httpResponse.getStatusLine()
                        .getReasonPhrase());
                return "";
            } else {
                // 在发出请求之后，可以从HttpRequest中获取一个InputStream，
                // 其包含了所请求文件的内容
                InputStream inputStream = httpResponse.getEntity().getContent();
                // 借助一个BufferedReader可以逐行得遍历该文件
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    Log.v("PLAYLISTLINE", "ORIG:" + line);
                    if (line.startsWith("#")) {
                        // 元数据，可以做更多的处理，但现在忽略它
                        return "";
                    } else if (line.length() > 0) {
                        // 如果它的长度大于0，那么就假设它是一个播放列表条目

                        if (line.startsWith("http://")) {
                            // 如果行以“http://”开头那么就把它作为流的完整URL
                            filePath = line;
                            Log.d("1111", "parsePlaylistFile:-111111111111111111111111111111111111111111111111111111111111111111111 ");
                        } else {
                            // 否则把它作为一个相对的URL，
                            // 同时把针对该M3U文件的原始请求的URL附加上去
                            filePath = getRequest.getURI().resolve(line)
                                    .toString();
                        }
                        return filePath;
                    }

                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

}