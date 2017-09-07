package com.zysapp.sjyyt.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayParse;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.hemaapp.hm_FrameWork.view.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseFragment;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.BaseUtil;
import com.zysapp.sjyyt.activity.R;
import com.zysapp.sjyyt.model.Image;
import com.zysapp.sjyyt.model.Song;
import com.zysapp.sjyyt.model.User;
import com.zysapp.sjyyt.util.EventBusConfig;
import com.zysapp.sjyyt.util.EventBusModel;
import com.zysapp.sjyyt.view.MyDrawable;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.util.IOUtils;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 *
 */
public class FirstPageFragment extends BaseFragment {

    @BindView(R.id.title_btn_left)
    ImageButton titleBtnLeft;
    @BindView(R.id.title_btn_right)
    ImageButton titleBtnRight;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.tv_channel)
    TextView tvChannel;
    @BindView(R.id.iv_music)
    ImageView ivMusic;
    @BindView(R.id.danmaku_view)
    DanmakuView danmakuView;
    @BindView(R.id.iv_open)
    ImageView ivOpen;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_reply)
    TextView tvReply;
    @BindView(R.id.iv_previous)
    ImageView ivPrevious;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.avatar)
    RoundedImageView avatar;
    @BindView(R.id.tv_player)
    TextView tvPlayer;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    @BindView(R.id.iv_line1)
    ImageView ivLine1;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_line2)
    ImageView ivLine2;
    @BindView(R.id.tv_replylist)
    TextView tvReplylist;
    @BindView(R.id.iv_line3)
    ImageView ivLine3;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.rv_reply)
    RecyclerView rvReply;
    Unbinder unbinder;
    private User user;
    private String district = "", city;
    private String token;
    private int screenWide;
    private ArrayList<Image> images = new ArrayList<Image>();
    private Integer currentPage = 0;
    //    private CarAdapter adapter;
    private PopupWindow mWindow_exit;
    private ViewGroup mViewGroup_exit;
    ArrayList<Song> songs = new ArrayList<>();
    private Integer currentPosition = 0;
    private boolean showDanmaku;
    private DanmakuContext danmakuContext;
    ArrayList<String> strings = new ArrayList<>();
    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_first);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        screenWide = BaseUtil.getScreenWidth(getActivity());
        user = BaseApplication.getInstance().getUser();
        if (user == null)
            token = "";
        else
            token = user.getToken();
        Song song = new Song();
        song.setAvatar("");
        song.setContent("张延山");
        song.setName("双节棍");
        song.setPath("http://sc1.111ttt.com/2015/1/06/06/99060941326.mp3");
        Song song2 = new Song();
        song2.setAvatar("");
        song2.setContent("薛之谦");
        song2.setName("演员");
        song2.setPath("http://sc1.111ttt.com/2015/1/06/06/99060941326.mp3");
        songs.add(song);
        songs.add(song2);

    }

    public void onEventMainThread(EventBusModel event) {
        switch (event.getType()) {
            case REFRESH_SONG:
                currentPosition = event.getCode();
                tvName.setText(songs.get(currentPosition).getName());
                ImageLoader.getInstance().displayImage(songs.get(currentPosition).getAvatar(), ivMusic, BaseApplication.getInstance()
                        .getOptions(R.mipmap.login_bg));
                break;
            case STATE_PLAY:
                ivPlay.setImageResource(R.mipmap.img_play);
                break;
            case STATE_PAUSE:
                ivPlay.setImageResource(R.mipmap.img_pause);
                break;
        }
    }

    @Override
    protected void findView() {
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case INDEX_LIST:
                // showProgressDialog("正在提交您的宝贵意见");
                break;
            case BLOG_LIST:
                break;
            default:
                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask hemaNetTask) {
        BaseHttpInformation information = (BaseHttpInformation) hemaNetTask
                .getHttpInformation();
        switch (information) {
            case INDEX_LIST:
                break;
            case BLOG_LIST:
//                layout.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case INDEX_LIST:
                HemaArrayParse<Image> iResult = (HemaArrayParse<Image>) baseResult;
                ArrayList<Image> imageList = iResult.getObjects();
                if (images != null && images.size() > 0)
                    images.clear();
                images.addAll(imageList);
                break;
            default:
                break;
        }
    }

    @Override
    protected void callBackForServerFailed(HemaNetTask netTask, HemaBaseResult baseResult) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case INDEX_LIST:
            case NEW_LIST:
                break;
            default:
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int i) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case INDEX_LIST:
            case NEW_LIST:
            case BROADCAST_LIST:
                showTextDialog("获取信息失败");
                break;
            default:
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (danmakuView != null && danmakuView.isPrepared()) {
            danmakuView.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        showDanmaku = false;
        if (danmakuView != null) {
            danmakuView.release();
            danmakuView = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused()) {
            danmakuView.resume();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode) {
            case 1:
                city = data.getStringExtra("name");
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        ivMusic.getLayoutParams().height = screenWide*3/5;
        titleBtnLeft.setVisibility(View.GONE);
        titleBtnRight.setImageResource(R.mipmap.first_right);
        titleText.setText("江阴主播电台");
//        if (songs.size() > 0)
//            EventBus.getDefault().post(new EventBusModel(EventBusConfig.PLAY, songs, currentPosition,1));
        danmakuContext = DanmakuContext.create();
        danmakuContext.setCacheStuffer(new SpannedCacheStuffer(), mCacheStufferAdapter);
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_LR, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_BOTTOM, true);
        danmakuContext.preventOverlapping(overlappingEnablePair);
        danmakuView.prepare(parser, danmakuContext);
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                showDanmaku = true;
                danmakuView.start();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.title_btn_right, R.id.tv_channel, R.id.iv_open, R.id.tv_save, R.id.tv_share, R.id.tv_reply, R.id.iv_previous, R.id.iv_play, R.id.iv_next, R.id.tv_tip, R.id.tv_center, R.id.tv_content, R.id.tv_replylist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_btn_right:
                addDanmaKuShowTextAndImage("http://fangchan.dpthinking.com/uploadfiles/2017/07/201707181254382898.jpg","张延山", "你好哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈", true);
                break;
            case R.id.tv_channel:
                addDanmaKuShowTextAndImage("http://fangchan.dpthinking.com/uploadfiles/2017/07/201707181254382898.jpg","林俊杰", "呵呵，不错", true);
                break;
            case R.id.iv_open:
                if (showDanmaku) {
                    danmakuView.hide();
                    showDanmaku = false;
                } else {
                    danmakuView.show();
                    showDanmaku = true;
                }
                break;
            case R.id.tv_save:
                break;
            case R.id.tv_share:
                break;
            case R.id.tv_reply:
                break;
            case R.id.iv_previous:
                if (songs.size() > 0)
                    EventBus.getDefault().post(new EventBusModel(EventBusConfig.PRE, songs, currentPosition));
                break;
            case R.id.iv_play:
                if (songs.size() > 0)
                    EventBus.getDefault().post(new EventBusModel(EventBusConfig.PLAY, songs, currentPosition, 1));
                break;
            case R.id.iv_next:
                if (songs.size() > 0)
                    EventBus.getDefault().post(new EventBusModel(EventBusConfig.NEXT, songs, currentPosition));
                break;
            case R.id.tv_tip:
                break;
            case R.id.tv_center:
                tvCenter.setTextColor(0xff000000);
                tvContent.setTextColor(0xff212121);
                tvReplylist.setTextColor(0xff212121);
                ivLine1.setVisibility(View.VISIBLE);
                ivLine2.setVisibility(View.INVISIBLE);
                ivLine3.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_content:
                tvCenter.setTextColor(0xff212121);
                tvContent.setTextColor(0xff000000);
                tvReplylist.setTextColor(0xff212121);
                ivLine1.setVisibility(View.INVISIBLE);
                ivLine2.setVisibility(View.VISIBLE);
                ivLine3.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_replylist:
                tvCenter.setTextColor(0xff212121);
                tvContent.setTextColor(0xff212121);
                tvReplylist.setTextColor(0xff000000);
                ivLine1.setVisibility(View.INVISIBLE);
                ivLine2.setVisibility(View.INVISIBLE);
                ivLine3.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 向弹幕View中添加一条弹幕
     *
     * @param content    弹幕的具体内容
     * @param withBorder 弹幕是否有边框
     */
    private void addDanmaku(String content, boolean withBorder) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = sp2px(20);
        danmaku.textColor = Color.BLACK;
        danmaku.setTime(danmakuView.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }
        danmakuView.addDanmaku(danmaku);
    }

    private void addDanmaKuShowTextAndImage(String avatar, String name, String content, boolean islive) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.listitem_danmu, null);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        RoundedImageView iv_avatar = (RoundedImageView) view.findViewById(R.id.iv_image);
        iv_avatar.setCornerRadius(100);
        tv_content.setText(content);
        tv_name.setText(name);
        ImageLoader.getInstance().loadImage(avatar, new imgload(iv_avatar, view));
//        ImageLoader.getInstance().displayImage( avatar,iv_avatar, BaseApplication.getInstance()
//                .getOptions(R.mipmap.default_avatar));

    }

    private class imgload implements ImageLoadingListener {
        public imgload(RoundedImageView iv_avatar, View view) {
            this.iv_avatar = iv_avatar;
            this.view0 = view;
        }

        RoundedImageView iv_avatar;
        View view0;

        @Override
        public void onLoadingStarted(String s, View view) {

        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {

        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bit) {
            iv_avatar.setImageBitmap(bit);
            Bitmap bitmap = BaseUtil.convertViewToBitmap(view0);
            BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
            SpannableStringBuilder spannable = createSpannable(bitmap, "");
            danmaku.text = spannable;
            danmaku.padding = 20;
            danmaku.priority = 1;  // 一定会显示, 一般用于本机发送的弹幕
            danmaku.isLive = true;
            danmaku.setTime(danmakuView.getCurrentTime() + 1500);
            danmaku.textShadowColor = 0; // 重要：如果有图文混排，最好不要设置描边(设textShadowColor=0)，否则会进行两次复杂的绘制导致运行效率降低
            danmakuView.addDanmaku(danmaku);
        }

        @Override
        public void onLoadingCancelled(String s, View view) {

        }
    }

    /**
     * 随机生成一些弹幕内容以供测试
     */
    private void generateSomeDanmaku() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (showDanmaku) {
                    int time = new Random().nextInt(300);
                    String content = "" + time + time;
//                    addDanmaku(content, false);
//                    addDanmaKuShowTextAndImage("http://fangchan.dpthinking.com/uploadfiles/2017/07/201707181254382898.jpg", content, true);
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * sp转px的方法。
     */
    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private BaseCacheStuffer.Proxy mCacheStufferAdapter = new BaseCacheStuffer.Proxy() {

        /**
         * 在弹幕显示前使用新的text,使用新的text
         * @param danmaku
         * @param fromWorkerThread 是否在工作(非UI)线程,在true的情况下可以做一些耗时操作(例如更新Span的drawblae或者其他IO操作)
         * @return 如果不需重置，直接返回danmaku.text
         */
        @Override
        public void prepareDrawing(final BaseDanmaku danmaku, boolean fromWorkerThread) {
//            if (danmaku.text instanceof Spanned) { // 根据你的条件检查是否需要需要更新弹幕
//                addDanmaKuShowTextAndImage("http://fangchan.dpthinking.com/uploadfiles/2017/07/201707181254382898.jpg", danmaku.text.toString(), true);
//            }
        }

        @Override
        public void releaseResource(BaseDanmaku danmaku) {
            // TODO 重要:清理含有ImageSpan的text中的一些占用内存的资源 例如drawable
        }
    };

    /**
     * 创建图文混排模式
     *
     * @param drawable
     * @return
     */
    private SpannableStringBuilder createSpannable(Bitmap drawable, String content) {
        String text = " ";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        ImageSpan span = new ImageSpan(getActivity(), drawable, ImageSpan.ALIGN_BASELINE);//ImageSpan.ALIGN_BOTTOM);
        spannableStringBuilder.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//        spannableStringBuilder.append(content);
//        spannableStringBuilder.setSpan(new BackgroundColorSpan(Color.parseColor("#ffffff")), 0, spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableStringBuilder;
    }

}
