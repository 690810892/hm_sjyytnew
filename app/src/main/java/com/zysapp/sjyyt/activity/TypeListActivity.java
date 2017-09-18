package com.zysapp.sjyyt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayParse;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.zysapp.sjyyt.BaseActivity;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.BaseUtil;
import com.zysapp.sjyyt.adapter.TypeAdapter;
import com.zysapp.sjyyt.model.Type;
import com.zysapp.sjyyt.model.User;
import com.zysapp.sjyyt.util.EventBusModel;
import com.zysapp.sjyyt.util.RecycleUtils;
import com.zysapp.sjyyt.util.RecyclerGridDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import xtom.frame.util.XtomToastUtil;
import xtom.frame.view.XtomRefreshLoadmoreLayout;

/**
 * 提醒列表-更多
 */
public class TypeListActivity extends BaseActivity {

    @BindView(R.id.title_btn_left)
    ImageButton titleBtnLeft;
    @BindView(R.id.title_btn_right)
    Button titleBtnRight;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.rv_hot)
    RecyclerView rvHot;
    @BindView(R.id.rv_other)
    RecyclerView rvOther;
    private User user;
    private String token = "";
    private TypeAdapter hotAdapter,otherAdapter;
    private ArrayList<Type> hots = new ArrayList<>();
    private ArrayList<Type> others = new ArrayList<>();
    private Integer currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_type);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        user = BaseApplication.getInstance().getUser();
        if (user == null)
            token = "";
        else
            token = user.getToken();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvHot.setLayoutManager(layoutManager);
//        rvHot.setPadding(0, 15, 15, 0);
        hotAdapter = new TypeAdapter(mContext, hots);
        rvHot.setAdapter(hotAdapter);
        StaggeredGridLayoutManager layoutManager2 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvOther.setLayoutManager(layoutManager2);
//        rvOther.setPadding(0, 15, 15, 0);
        otherAdapter = new TypeAdapter(mContext, others);
        rvOther.setAdapter(otherAdapter);
        getNetWorker().typeList();
    }

    public void onEventMainThread(EventBusModel event) {
        switch (event.getType()) {
            case REFRESH_CAR_LIST:
                currentPage = 0;
//                getNetWorker().typeList();
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        // TODO Auto-generated method stub
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            case LIVE_TYPE_LIST:
                showProgressDialog("正在加载");
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
            case LIVE_TYPE_LIST:
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
            case LIVE_TYPE_LIST:
                @SuppressWarnings("unchecked")
                HemaArrayParse<Type> gResult = (HemaArrayParse<Type>) baseResult;
                Type goods = gResult.getObjects().get(0);
                hots.clear();
                hots.addAll(goods.getHots());
                others.clear();
                others.addAll(goods.getOthers());
                hotAdapter.notifyDataSetChanged();
                otherAdapter.notifyDataSetChanged();
                break;
            case INDEX_LIST:
                break;
            default:
                break;
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
            case LIVE_TYPE_LIST:
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
            case LIVE_TYPE_LIST:
                showTextDialog("加载失败");
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
        titleText.setText("分类");
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
                break;
        }
    }
}
