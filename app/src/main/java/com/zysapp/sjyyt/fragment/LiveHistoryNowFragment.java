package com.zysapp.sjyyt.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayParse;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.hemaapp.hm_FrameWork.view.RefreshLoadmoreLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseFragment;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.BaseRecycleAdapter;
import com.zysapp.sjyyt.ToLogin;
import com.zysapp.sjyyt.activity.MainActivity;
import com.zysapp.sjyyt.activity.R;
import com.zysapp.sjyyt.adapter.LivePlayingAdapter;
import com.zysapp.sjyyt.adapter.TypeLiveAdapter;
import com.zysapp.sjyyt.model.Song;
import com.zysapp.sjyyt.model.User;
import com.zysapp.sjyyt.util.EventBusConfig;
import com.zysapp.sjyyt.util.EventBusModel;
import com.zysapp.sjyyt.util.RecycleUtils;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import xtom.frame.util.XtomTimeUtil;
import xtom.frame.util.XtomToastUtil;
import xtom.frame.view.XtomRefreshLoadmoreLayout;

/**
 * 播放记录-正在播放
 */
public class LiveHistoryNowFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.iv_button)
    ImageView ivButton;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLoadmoreLayout)
    RefreshLoadmoreLayout refreshLoadmoreLayout;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.empty)
    LinearLayout empty;

    private User user;
    private String token;
    private Integer currentPage = 0;
    private ArrayList<Song> blogs = new ArrayList<>();
    private LivePlayingAdapter adapter;
    MainActivity mainActivity;
    private Integer currentPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        adapter = new LivePlayingAdapter(getActivity(), blogs);
        RecycleUtils.initVerticalRecyleNoScrll(rvList);
        rvList.setAdapter(adapter);
        ImageLoader.getInstance().displayImage( blogs.get(currentPosition).getImgurl(), ivPlay, BaseApplication.getInstance()
                .getOptions(R.mipmap.default_blog_img));
        tvName.setText(blogs.get(currentPosition).getName());
        tvTime.setText(XtomTimeUtil.TransTime(blogs.get(currentPosition).getStartdate(),"yyyy/MM/dd HH:mm")+"上新");
        adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                EventBus.getDefault().post(new EventBusModel(EventBusConfig.REFRESH_FIRST_SONG, blogs, position, 1, 1));
            }
        });
        ivPlay.setFocusable(true);
        ivPlay.setFocusableInTouchMode(true);
        ivPlay.requestFocus();
        return rootView;
    }

//    private void carList(int page) {
//        getNetWorker().playHistoryList(token, page);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_history_now);
        super.onCreate(savedInstanceState);
        user = BaseApplication.getInstance().getUser();
        EventBus.getDefault().register(this);
        mainActivity = MainActivity.getInstance();
        blogs.addAll(mainActivity.mPlayService.getmQueue());
        currentPosition = mainActivity.mPlayService.getmQueueIndex();
        blogs.get(currentPosition).setState("1");

        if (user == null)
            token = "";
        else
            token = user.getToken();
    }


    public void onEventMainThread(EventBusModel event) {
        switch (event.getType()) {
            case REFRESH_SONG:

                currentPosition = event.getCode();
                for (Song song : blogs)
                    song.setState("0");
                blogs.get(currentPosition).setState("1");
                adapter.notifyDataSetChanged();
                ImageLoader.getInstance().displayImage( blogs.get(currentPosition).getImgurl(), ivPlay, BaseApplication.getInstance()
                        .getOptions(R.mipmap.default_blog_img));
                tvName.setText(blogs.get(currentPosition).getName());
                tvTime.setText(XtomTimeUtil.TransTime(blogs.get(currentPosition).getStartdate(),"yyyy/MM/dd HH:mm")+"上新");
                break;
            case REFRESH_RECORDE:
                blogs.clear();
                blogs.addAll(event.getSongs());
                currentPosition = event.getCode();
                for (Song song : blogs)
                    song.setState("0");
                blogs.get(currentPosition).setState("1");
                adapter.notifyDataSetChanged();
                ImageLoader.getInstance().displayImage( blogs.get(currentPosition).getImgurl(), ivPlay, BaseApplication.getInstance()
                        .getOptions(R.mipmap.default_blog_img));
                tvName.setText(blogs.get(currentPosition).getName());
                tvTime.setText(XtomTimeUtil.TransTime(blogs.get(currentPosition).getStartdate(),"yyyy/MM/dd HH:mm")+"上新");
                break;
        }
    }

    @Override
    protected void findView() {
        refreshLoadmoreLayout = (RefreshLoadmoreLayout) findViewById(R.id.refreshLoadmoreLayout);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
        } else {
            if (user == null) {
                ToLogin.showLogin(getActivity());
            }
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    protected void setListener() {
        refreshLoadmoreLayout.setOnStartListener(new XtomRefreshLoadmoreLayout.OnStartListener() {

            @Override
            public void onStartRefresh(XtomRefreshLoadmoreLayout v) {
                currentPage = 0;
//                carList(currentPage);
            }

            @Override
            public void onStartLoadmore(XtomRefreshLoadmoreLayout v) {
                currentPage++;
//                carList(currentPage);
            }
        });
        refreshLoadmoreLayout.setLoadmoreable(false);
        refreshLoadmoreLayout.setRefreshable(false);
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case DATA_SAVEOPERATE:
                showProgressDialog("请稍后");
                break;
            case PLAY_HISTORY_LIST:
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
            case DATA_SAVEOPERATE:
                cancelProgressDialog();
                break;
            case PLAY_HISTORY_LIST:
                progressbar.setVisibility(View.GONE);
                refreshLoadmoreLayout.setVisibility(View.VISIBLE);
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
            case PLAY_HISTORY_LIST:
                String page = netTask.getParams().get("page");
                HemaArrayParse<Song> gResult = (HemaArrayParse<Song>) baseResult;
                ArrayList<Song> goods = gResult.getObjects();
                if (page.equals("0")) {// 刷新
                    refreshLoadmoreLayout.refreshSuccess();
                    this.blogs.clear();
                    this.blogs.addAll(goods);
                    int sysPagesize = BaseApplication.getInstance().getSysInitInfo()
                            .getSys_pagesize();
                    if (goods.size() < sysPagesize)
                        refreshLoadmoreLayout.setLoadmoreable(false);
                    else
                        refreshLoadmoreLayout.setLoadmoreable(true);
                } else {// 更多
                    refreshLoadmoreLayout.loadmoreSuccess();
                    if (goods.size() > 0)
                        this.blogs.addAll(goods);
                    else {
                        refreshLoadmoreLayout.setLoadmoreable(false);
                        XtomToastUtil.showShortToast(getActivity(), "已经到最后啦");
                    }
                }
                adapter.notifyDataSetChanged();
                break;
            case DATA_SAVEOPERATE:
//                carList(0);
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
            case DATA_SAVEOPERATE:
            case PLAY_HISTORY_LIST:
                showTextDialog(baseResult.getMsg());
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
            case DATA_SAVEOPERATE:
                showTextDialog("操作失败");
                break;
            case PLAY_HISTORY_LIST:
                showTextDialog("获取信息失败");
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode) {
            case 1:
                break;
            case 2:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    @OnClick({R.id.tv_cycle, R.id.tv_random, R.id.tv_clear, R.id.iv_play})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.tv_cycle:
//                EventBus.getDefault().post(new EventBusModel(EventBusConfig.REFRESH_FIRST_SONG, blogs, 0, 5, 1));
//                break;
//            case R.id.tv_random:
//                ArrayList<Song> random = new ArrayList<>();
//                random.addAll(blogs);
//                Collections.shuffle(random);
//                EventBus.getDefault().post(new EventBusModel(EventBusConfig.REFRESH_FIRST_SONG, random, 0, 5, 1));
//                break;
//            case R.id.tv_clear:
//                break;
//            case R.id.iv_play:
//                EventBus.getDefault().post(new EventBusModel(EventBusConfig.REFRESH_FIRST_SONG, blogs, 0, 5, 1));
//                break;
//        }
//    }


    @OnClick(R.id.iv_button)
    public void onViewClicked() {

    }
}
