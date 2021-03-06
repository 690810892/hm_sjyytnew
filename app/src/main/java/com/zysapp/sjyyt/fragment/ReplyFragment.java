package com.zysapp.sjyyt.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayParse;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.hemaapp.hm_FrameWork.view.RefreshLoadmoreLayout;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseFragment;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.ToLogin;
import com.zysapp.sjyyt.activity.R;
import com.zysapp.sjyyt.adapter.ReplyAdapter;
import com.zysapp.sjyyt.model.Reply;
import com.zysapp.sjyyt.model.User;
import com.zysapp.sjyyt.util.EventBusModel;
import com.zysapp.sjyyt.util.RecycleUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import xtom.frame.util.XtomToastUtil;
import xtom.frame.view.XtomRefreshLoadmoreLayout;

/**
 * 评论
 */
public class ReplyFragment extends BaseFragment {

    @BindView(R.id.title_btn_left)
    ImageButton titleBtnLeft;
    @BindView(R.id.title_btn_right)
    Button titleBtnRight;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.refreshLoadmoreLayout)
    RefreshLoadmoreLayout refreshLoadmoreLayout;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.empty)
    LinearLayout empty;
    Unbinder unbinder;
    private User user;
    private String token;
    private Integer currentPage = 0;
    private ArrayList<Reply> blogs = new ArrayList<>();
    private ReplyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        titleText.setText("最近评论");
        titleBtnLeft.setVisibility(View.GONE);
        adapter = new ReplyAdapter(getActivity(), blogs,getNetWorker());
        adapter.setKeytype("2");
        RecycleUtils.initVerticalRecyle(rvList);
        rvList.setAdapter(adapter);

        carList("0");

        return rootView;
    }

    private void carList(String page) {
        getNetWorker().replyList("2", "0", page);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_reply);
        super.onCreate(savedInstanceState);
        user = BaseApplication.getInstance().getUser();
        EventBus.getDefault().register(this);
        if (user == null)
            token = "";
        else
            token = user.getToken();
    }


    public void onEventMainThread(EventBusModel event) {
        switch (event.getType()) {
            case REFRESH_REPLY:
                currentPage = 0;
                carList("0");
                break;
            case REFRESH_USER:
                user = BaseApplication.getInstance().getUser();
                if (user == null)
                    token = "";
                else
                    token = user.getToken();
                break;
        }
    }

    @Override
    protected void findView() {
        refreshLoadmoreLayout = (RefreshLoadmoreLayout) findViewById(R.id.refreshLoadmoreLayout);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        user = BaseApplication.getInstance().getUser();
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
                carList(currentPage.toString());
            }

            @Override
            public void onStartLoadmore(XtomRefreshLoadmoreLayout v) {
                currentPage++;
                carList(currentPage.toString());
            }
        });
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case DATA_SAVEOPERATE:
                showProgressDialog("请稍后");
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
            case DATA_SAVEOPERATE:
                cancelProgressDialog();
                break;
            case REPLY_LIST:
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
            case REPLY_LIST:
                String page = netTask.getParams().get("page");
                HemaArrayParse<Reply> gResult = (HemaArrayParse<Reply>) baseResult;
                ArrayList<Reply> goods = gResult.getObjects();
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
                currentPage = 0;
                carList(currentPage.toString());
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
            case REPLY_LIST:
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
            case REPLY_LIST:
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
}
