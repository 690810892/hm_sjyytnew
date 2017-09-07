package com.zysapp.sjyyt.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayParse;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.hemaapp.hm_FrameWork.view.RefreshLoadmoreLayout;
import com.zysapp.sjyyt.BaseActivity;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.BaseUtil;
import com.zysapp.sjyyt.adapter.NoticeAdapter;
import com.zysapp.sjyyt.model.Notice;
import com.zysapp.sjyyt.model.User;
import com.zysapp.sjyyt.util.EventBusConfig;
import com.zysapp.sjyyt.util.EventBusModel;
import com.zysapp.swipelistview.SwipeMenu;
import com.zysapp.swipelistview.SwipeMenuCreator;
import com.zysapp.swipelistview.SwipeMenuItem;
import com.zysapp.swipelistview.SwipeMenuListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import xtom.frame.util.XtomToastUtil;
import xtom.frame.view.XtomRefreshLoadmoreLayout;

/**
 * 系统通知
 */
public class NoticeListActivity extends BaseActivity {
    @BindView(R.id.title_btn_left)
    ImageButton titleBtnLeft;
    @BindView(R.id.title_btn_right)
    ImageButton titleBtnRight;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.refreshLoadmoreLayout)
    RefreshLoadmoreLayout refreshLoadmoreLayout;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.empty)
    LinearLayout empty;
    @BindView(R.id.listview)
    SwipeMenuListView listview;
    @BindView(R.id.line)
    ImageView line;
    private User user;
    private String token = "";
    private NoticeAdapter adapter;
    private ArrayList<Notice> blogs = new ArrayList<>();
    private Integer currentPage = 0;
    private Notice item;
    private PopupWindow mWindow_exit;
    private ViewGroup mViewGroup_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_notice_list);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        user = BaseApplication.getInstance().getUser();
        if (user == null)
            token = "";
        else
            token = user.getToken();
        setList();
        getNetWorker().noticeList(token, currentPage);
    }

    public void onEventMainThread(EventBusModel event) {
        switch (event.getType()) {
            case REFRESH_BLOG_LIST:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setList() {
// step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext);
                // set item background
                deleteItem.setBackground(R.drawable.bg_delete_color);
                // set item width
                deleteItem.setWidth(BaseUtil.dip2px(mContext, 70));
                // set a icon
                deleteItem.setIcon(R.mipmap.img_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listview.setMenuCreator(creator);
        listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                item = blogs.get(position);
                switch (index) {
                    case 0:
                        getNetWorker().noticeOperate(user.getToken(),"1","0", item.getId(), "3");
                        break;
                }
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = blogs.get(position);
                if ("1".equals(item.getLooktype()))
                    getNetWorker().noticeOperate(user.getToken(),"1","0", item.getId(), "1");
//                Intent it=new Intent(mContext,MemoListActivity.class);
//                it.putExtra("id",item.getId());
//                it.putExtra("title",item.getName());
//                startActivity(it);
            }
        });
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        // TODO Auto-generated method stub
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case NOTICE_SAVEOPERATE:
                String operatetype = netTask.getParams().get("operatetype");
                if (operatetype.equals("1")) {
                }
                if (operatetype.equals("2")) {
                    showProgressDialog("请稍后");
                }
                if (operatetype.equals("3")) {
                    showProgressDialog("正在删除");
                }
                if (operatetype.equals("4")) {
                    showProgressDialog("正在清空");
                }

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
            case NOTICE_LIST:
                progressbar.setVisibility(View.GONE);
                refreshLoadmoreLayout.setVisibility(View.VISIBLE);
                break;
            case NOTICE_SAVEOPERATE:
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
            case NOTICE_LIST:
                String page = netTask.getParams().get("page");
                @SuppressWarnings("unchecked")
                HemaArrayParse<Notice> gResult = (HemaArrayParse<Notice>) baseResult;
                ArrayList<Notice> goods = gResult.getObjects();
                if (page.equals("0")) {// 刷新
                    refreshLoadmoreLayout.refreshSuccess();
                    this.blogs.clear();
                    this.blogs.addAll(goods);
                    int sysPagesize = getApplicationContext().getSysInitInfo()
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
                        XtomToastUtil.showShortToast(mContext, "已经到最后啦");
                    }
                }
                if (blogs.size() == 0) {
                    empty.setVisibility(View.VISIBLE);
                } else {
                    empty.setVisibility(View.INVISIBLE);
                }
                freshData();
                break;
            case NOTICE_SAVEOPERATE:
                EventBus.getDefault().post(new EventBusModel(EventBusConfig.NEW_MESSAGE));
                String operatetype = netTask.getParams().get("operatetype");
                if (operatetype.equals("1")) {
                    item.setLooktype("2");
                    adapter.notifyDataSetChanged();
                }
                if (operatetype.equals("2")) {
                    getNetWorker().noticeList(token, currentPage);
                }
                if (operatetype.equals("3")) {
                    blogs.remove(item);
                    adapter.notifyDataSetChanged();
                }
                if (operatetype.equals("4")) {
                    getNetWorker().noticeList(token, currentPage);
                }
                break;
            default:
                break;
        }
    }

    private void freshData() {
        if (this.blogs.size() == 0)
            empty.setVisibility(View.VISIBLE);
        else
            empty.setVisibility(View.GONE);
        if (adapter == null) {
            adapter = new NoticeAdapter(mContext, blogs, getNetWorker());
            listview.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
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
            case NOTICE_LIST:
            case NOTICE_SAVEOPERATE:
                showTextDialog(baseResult.getMsg());
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
            case NOTICE_LIST:
                showTextDialog("加载失败");
                break;
            case NOTICE_SAVEOPERATE:
                showTextDialog("操作失败");
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
        titleText.setText("消息盒子");
//        titleBtnRight.setImageResource(R.mipmap.first_add);
        refreshLoadmoreLayout.setOnStartListener(new XtomRefreshLoadmoreLayout.OnStartListener() {

            @Override
            public void onStartRefresh(XtomRefreshLoadmoreLayout v) {
                currentPage = 0;
                getNetWorker().noticeList(token, currentPage);
            }

            @Override
            public void onStartLoadmore(XtomRefreshLoadmoreLayout v) {
                currentPage++;
                getNetWorker().noticeList(token, currentPage);
            }
        });
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

    @OnClick({R.id.title_btn_left, R.id.title_btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_btn_left:
                finish();
                break;
            case R.id.title_btn_right:
                moreDialog();
                break;
        }
    }
    private void moreDialog() {
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
                R.layout.pop_type, null);
        TextView remove = (TextView) mViewGroup_exit.findViewById(R.id.tv_1);
        TextView read = (TextView) mViewGroup_exit.findViewById(R.id.tv_2);
        TextView cancel = (TextView) mViewGroup_exit.findViewById(R.id.tv_cancel);
        remove.setText("清空");
        read.setText("全部设为已读");
        mWindow_exit.setContentView(mViewGroup_exit);
        mWindow_exit.showAtLocation(mViewGroup_exit, Gravity.CENTER, 0, 0);
        read.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mWindow_exit.dismiss();
                getNetWorker().noticeOperate(user.getToken(),"1","0", "0", "2");
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mWindow_exit.dismiss();
                getNetWorker().noticeOperate(user.getToken(),"1","0", "0", "4");
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mWindow_exit.dismiss();
            }
        });
    }
}
