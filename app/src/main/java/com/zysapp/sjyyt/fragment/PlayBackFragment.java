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

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayParse;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.hemaapp.hm_FrameWork.view.RefreshLoadmoreLayout;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseFragment;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.BaseRecycleAdapter;
import com.zysapp.sjyyt.BaseUtil;
import com.zysapp.sjyyt.ToLogin;
import com.zysapp.sjyyt.activity.R;
import com.zysapp.sjyyt.adapter.BackPlayAdapter;
import com.zysapp.sjyyt.model.Reply;
import com.zysapp.sjyyt.model.Song;
import com.zysapp.sjyyt.model.User;
import com.zysapp.sjyyt.util.EventBusConfig;
import com.zysapp.sjyyt.util.EventBusModel;
import com.zysapp.sjyyt.util.RecycleUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import xtom.frame.util.XtomToastUtil;
import xtom.frame.view.XtomRefreshLoadmoreLayout;

/**
 * 回听
 */
public class PlayBackFragment extends BaseFragment {

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
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.lv_top)
    LinearLayout lvTop;
    private User user;
    private String token, data;
    private Integer currentPage = 0;
    private ArrayList<Song> blogs = new ArrayList<>();
    private BackPlayAdapter adapter;
    private Calendar selectedDate;//系统当前时间
    private Calendar startDate;
    private Calendar endDate;
    private TimePickerView pvDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        titleText.setText("回听");
        titleBtnLeft.setVisibility(View.GONE);
        String s[] = data.split("-");
        tvYear.setText(s[0]);
        tvMonth.setText(s[1]);
        tvDay.setText(s[2]);
        adapter = new BackPlayAdapter(getActivity(), blogs);
        RecycleUtils.initVerticalRecyle(rvList);
        rvList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                EventBus.getDefault().post(new EventBusModel(EventBusConfig.REFRESH_FIRST_SONG,blogs,position,3,Integer.parseInt(tvYear.getText().toString()
                        +tvMonth.getText().toString()+tvDay.getText().toString())));
//                EventBus.getDefault().post(new EventBusModel(EventBusConfig.PLAY, blogs, position, 3, Integer.parseInt(tvYear.getText().toString()+tvMonth.getText().toString()+tvDay.getText().toString())));
            }
        });
        carList(0);

        return rootView;
    }

    private void carList(int page) {
        getNetWorker().liveList("3", data, page);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_playback);
        super.onCreate(savedInstanceState);
        user = BaseApplication.getInstance().getUser();
        EventBus.getDefault().register(this);
        selectedDate = Calendar.getInstance();
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        startDate.set(1931, 1, 10);
        endDate.set(2120, 2, 28);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        selectedDate.set(Calendar.DATE, selectedDate.get(Calendar.DATE));
        data = format.format(selectedDate.getTime());
        log_d("data==" + data);
        if (user == null)
            token = "";
        else
            token = user.getToken();
    }


    public void onEventMainThread(EventBusModel event) {
        switch (event.getType()) {
            case REFRESH_CAR_LIST:
                currentPage = 0;
                carList(0);
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
                carList(currentPage);
            }

            @Override
            public void onStartLoadmore(XtomRefreshLoadmoreLayout v) {
                currentPage++;
                carList(currentPage);
            }
        });
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case LIVE_LIST:
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
            case LIVE_LIST:
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
            case LIVE_LIST:
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
                if (blogs.size()>0)
                    empty.setVisibility(View.GONE);
                else
                    empty.setVisibility(View.VISIBLE);
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
            case LIVE_LIST:
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
            case LIVE_LIST:
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

    @OnClick(R.id.lv_top)
    public void onViewClicked() {
        pvDate = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                data = (BaseUtil.getTime(date));
                currentPage=0;
                carList(0);
                String s[] = data.split("-");
                log_d("data1==" + data);
                tvYear.setText(s[0]);
                tvMonth.setText(s[1]);
                tvDay.setText(s[2]);
            }
        }).setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvDate.returnData();
                                pvDate.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvDate.dismiss();
                            }
                        });
                    }
                }).setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xff1aad19)
                .build();
        pvDate.show();
    }
}
