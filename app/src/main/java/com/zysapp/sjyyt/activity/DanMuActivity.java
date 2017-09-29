package com.zysapp.sjyyt.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.zysapp.sjyyt.BaseActivity;
import com.zysapp.sjyyt.BaseHttpInformation;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
//import master.flame.danmaku.controller.DrawHandler;
//import master.flame.danmaku.danmaku.model.BaseDanmaku;
//import master.flame.danmaku.danmaku.model.DanmakuTimer;
//import master.flame.danmaku.danmaku.model.IDanmakus;
//import master.flame.danmaku.danmaku.model.android.DanmakuContext;
//import master.flame.danmaku.danmaku.model.android.Danmakus;
//import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
//import master.flame.danmaku.ui.widget.DanmakuView;
import xtom.frame.XtomActivityManager;

/**
 *
 */
//public class DanMuActivity extends BaseActivity {


//    @BindView(R.id.danmaku_view)
//    DanmakuView danmakuView;
//    private boolean showDanmaku;
//    private DanmakuContext danmakuContext;
//    ArrayList<String> strings = new ArrayList<>();
//    private BaseDanmakuParser parser = new BaseDanmakuParser() {
//        @Override
//        protected IDanmakus parse() {
//            return new Danmakus();
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_danmu);
//        ButterKnife.bind(this);
//        super.onCreate(savedInstanceState);
//        strings.add("张延山1111111111");
//        strings.add("张延山2222222221");
//        strings.add("张延山3333333333111");
//        strings.add("张延山18888888888811");
//        strings.add("张延山19999999999999911");
//        strings.add("张延山7777777777711");
//        strings.add("张延山1144444444444444");
//        strings.add("张延山113333333333333333111");
//        strings.add("张延山10000000000000011");
//        strings.add("张延山1111111111");
//
//        danmakuView.enableDanmakuDrawingCache(true);
//        danmakuView.setCallback(new DrawHandler.Callback() {
//            @Override
//            public void prepared() {
//                showDanmaku = true;
//                danmakuView.start();
//                generateSomeDanmaku();
//                for (String d : strings) {
//                    addDanmaku(d, false);
//                }
//            }
//
//            @Override
//            public void updateTimer(DanmakuTimer timer) {
//
//            }
//
//            @Override
//            public void danmakuShown(BaseDanmaku danmaku) {
//
//            }
//
//            @Override
//            public void drawingFinished() {
//
//            }
//        });
//        danmakuContext = DanmakuContext.create();
//        danmakuView.prepare(parser, danmakuContext);
//    }
//
//    /**
//     * 向弹幕View中添加一条弹幕
//     *
//     * @param content    弹幕的具体内容
//     * @param withBorder 弹幕是否有边框
//     */
//    private void addDanmaku(String content, boolean withBorder) {
//        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
//        danmaku.text = content;
//        danmaku.padding = 5;
//        danmaku.textSize = sp2px(20);
//        danmaku.textColor = Color.BLACK;
//        danmaku.setTime(danmakuView.getCurrentTime());
//        if (withBorder) {
//            danmaku.borderColor = Color.GREEN;
//        }
//        danmakuView.addDanmaku(danmaku);
//    }
//
//    /**
//     * 随机生成一些弹幕内容以供测试
//     */
//    private void generateSomeDanmaku() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (showDanmaku) {
//                    int time = new Random().nextInt(300);
//                    String content = "" + time + time;
//                    addDanmaku(content, false);
//                    try {
//                        Thread.sleep(time);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//    }
//
//    /**
//     * sp转px的方法。
//     */
//    public int sp2px(float spValue) {
//        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
//        return (int) (spValue * fontScale + 0.5f);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (danmakuView != null && danmakuView.isPrepared()) {
//            danmakuView.pause();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused()) {
//            danmakuView.resume();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        showDanmaku = false;
//        if (danmakuView != null) {
//            danmakuView.release();
//            danmakuView = null;
//        }
//    }
//
//    @Override
//    protected void callBeforeDataBack(HemaNetTask netTask) {
//        BaseHttpInformation information = (BaseHttpInformation) netTask
//                .getHttpInformation();
//        switch (information) {
//            case CLIENT_VERIFY:
//                showProgressDialog("正在验证手机号");
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    protected void callAfterDataBack(HemaNetTask netTask) {
//        BaseHttpInformation information = (BaseHttpInformation) netTask
//                .getHttpInformation();
//        switch (information) {
//            case CLIENT_VERIFY:
//                cancelProgressDialog();
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    protected void callBackForServerSuccess(HemaNetTask netTask,
//                                            HemaBaseResult baseResult) {
//        BaseHttpInformation information = (BaseHttpInformation) netTask
//                .getHttpInformation();
//        switch (information) {
//            case CLIENT_VERIFY:
//                showTextDialog("该手机号已经被注册了");
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void onPublish(int progress) {
//
//    }
//
//    @Override
//    public void onChange(int position) {
//
//    }
//
//    @Override
//    protected void callBackForServerFailed(HemaNetTask netTask,
//                                           HemaBaseResult baseResult) {
//        BaseHttpInformation information = (BaseHttpInformation) netTask
//                .getHttpInformation();
//        switch (information) {
//            case CODE_GET:
//                showTextDialog(baseResult.getMsg());
//                break;
//            default:
//                break;
//        }
//    }
//
//    private void toLogin() {
//        XtomActivityManager.finishAll();
//        Intent it = new Intent(mContext, LoginActivity.class);
//        startActivity(it);
//    }
//
//    @Override
//    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
//        BaseHttpInformation information = (BaseHttpInformation) netTask
//                .getHttpInformation();
//        switch (information) {
//            case CLIENT_VERIFY:
//                showTextDialog("验证手机号失败");
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    protected void findView() {
//    }
//
//    @Override
//    protected void getExras() {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    protected void setListener() {
//    }
//

//}
