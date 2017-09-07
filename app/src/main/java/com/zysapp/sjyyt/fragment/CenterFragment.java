package com.zysapp.sjyyt.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.hemaapp.hm_FrameWork.view.RefreshLoadmoreLayout;
import com.hemaapp.hm_FrameWork.view.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zysapp.sjyyt.BaseApplication;
import com.zysapp.sjyyt.BaseFragment;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.ToLogin;
import com.zysapp.sjyyt.activity.MyInforActivity;
import com.zysapp.sjyyt.activity.MyLoveActivity;
import com.zysapp.sjyyt.activity.MyPlayedActivity;
import com.zysapp.sjyyt.activity.NoticeListActivity;
import com.zysapp.sjyyt.activity.R;
import com.zysapp.sjyyt.activity.SetActivity;
import com.zysapp.sjyyt.model.ID;
import com.zysapp.sjyyt.model.SysInitInfo;
import com.zysapp.sjyyt.model.User;
import com.zysapp.sjyyt.util.EventBusModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import xtom.frame.view.XtomRefreshLoadmoreLayout;


/**
 *
 */
public class CenterFragment extends BaseFragment {

    @BindView(R.id.title_btn_left)
    ImageButton titleBtnLeft;
    @BindView(R.id.title_btn_right)
    ImageButton titleBtnRight;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.iv_avatar)
    RoundedImageView ivAvatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.iv_sex)
    RoundedImageView ivSex;
    @BindView(R.id.lv_infor)
    LinearLayout lvInfor;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.tv_notice_num)
    TextView tvNoticeNum;
    @BindView(R.id.lv_notice)
    LinearLayout lvNotice;
    @BindView(R.id.tv_played)
    TextView tvPlayed;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_love)
    TextView tvLove;
    @BindView(R.id.refreshLoadmoreLayout)
    RefreshLoadmoreLayout refreshLoadmoreLayout;
    Unbinder unbinder;
    private User user;
    private SysInitInfo sysInitInfo;
    private PopupWindow mWindow_exit;
    private ViewGroup mViewGroup_exit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_center);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        sysInitInfo = BaseApplication.getInstance().getSysInitInfo();

    }

    public void onEventMainThread(EventBusModel event) {
        switch (event.getType()) {
            case NEW_MESSAGE:
                if (user != null)
                    getNetWorker().unreadGet(user.getToken());
                break;
            case REFRESH_USER:
                getNetWorker().clientGet(user.getToken(), user.getId());
                break;
        }
    }

    private void init() {
        user = BaseApplication.getInstance().getUser();
        if (user == null) {
            tvNickname.setText("未登录");
        } else {
            getNetWorker().clientGet(user.getToken(), user.getId());
            getNetWorker().unreadGet(user.getToken());
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void findView() {
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        BaseHttpInformation information = (BaseHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case CLIENT_GET:
                break;
            case CLIENT_LOGINOUT:
                showProgressDialog("正在注销");
                break;
            default:
                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        BaseHttpInformation information = (BaseHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case CLIENT_GET:
                break;
            case CLIENT_LOGINOUT:
                cancelProgressDialog();
                break;
            default:
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        BaseHttpInformation information = (BaseHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case CLIENT_GET:
                refreshLoadmoreLayout.refreshSuccess();
                HemaArrayResult<User> uResult = (HemaArrayResult<User>) baseResult;
                user = uResult.getObjects().get(0);
                BaseApplication.getInstance().setUser(user);
                initUserInfor();
                break;
            case UNREAD_GET:
                HemaArrayResult<ID> TResult = (HemaArrayResult<ID>) baseResult;
                String count = TResult.getObjects().get(0).getNum();
                tvNoticeNum.setText(count);
                break;
            default:
                break;
        }
    }

    private void initUserInfor() {
        ImageLoader.getInstance().displayImage(user.getAvatar(), ivAvatar, BaseApplication.getInstance()
                .getOptions(R.mipmap.default_avatar));
        ivAvatar.setCornerRadius(100);
        tvNickname.setText(user.getNickname());
        if (user.getSex().equals("男")) {
            ivSex.setImageResource(R.mipmap.sex_m);
        } else {
            ivSex.setImageResource(R.mipmap.sex_f);
        }
    }

    @Override
    protected void callBackForServerFailed(HemaNetTask netTask, HemaBaseResult baseResult) {
        BaseHttpInformation information = (BaseHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case CLIENT_GET:
                showTextDialog(baseResult.getMsg());
                break;
            case CLIENT_LOGINOUT:
                log_i("退出登录失败");
                break;
            default:
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int i) {
        BaseHttpInformation information = (BaseHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case CLIENT_GET:
                showTextDialog("获取信息失败");
                break;
            case CLIENT_LOGINOUT:
                log_i("退出登录失败");
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode) {
            case 1:
                getNetWorker().clientGet(user.getToken(), user.getId());
                break;
            case 2:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        init();
        titleBtnRight.setImageResource(R.mipmap.center_set);
        titleText.setText("我的");
        titleBtnLeft.setVisibility(View.GONE);
        refreshLoadmoreLayout.setOnStartListener(new XtomRefreshLoadmoreLayout.OnStartListener() {

            @Override
            public void onStartRefresh(XtomRefreshLoadmoreLayout v) {
                getNetWorker().clientGet(user.getToken(), user.getId());
            }

            @Override
            public void onStartLoadmore(XtomRefreshLoadmoreLayout v) {
            }
        });
        refreshLoadmoreLayout.setLoadmoreable(false);
        user = BaseApplication.getInstance().getUser();
        if (user == null)
            refreshLoadmoreLayout.setRefreshable(false);
        else
            refreshLoadmoreLayout.setRefreshable(true);

        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.title_btn_left, R.id.title_btn_right, R.id.lv_infor, R.id.lv_notice, R.id.tv_played, R.id.tv_score, R.id.tv_tip, R.id.tv_love})
    public void onViewClicked(View view) {
        Intent it;
        switch (view.getId()) {
            case R.id.title_btn_left:
                break;
            case R.id.title_btn_right:
                it = new Intent(getActivity(), SetActivity.class);
                startActivity(it);
                break;
            case R.id.lv_infor:
                if (user == null) {
                    ToLogin.showLogin(getActivity());
                    break;
                }
                it = new Intent(getActivity(), MyInforActivity.class);
                startActivity(it);
                break;
            case R.id.lv_notice:
                if (user == null) {
                    ToLogin.showLogin(getActivity());
                    break;
                }
                it = new Intent(getActivity(), NoticeListActivity.class);
                startActivity(it);
                break;
            case R.id.tv_played:
                if (user == null) {
                    ToLogin.showLogin(getActivity());
                    break;
                }
                it = new Intent(getActivity(), MyPlayedActivity.class);
                startActivity(it);
                break;
            case R.id.tv_score:
                break;
            case R.id.tv_tip:
                break;
            case R.id.tv_love:
                if (user == null) {
                    ToLogin.showLogin(getActivity());
                    break;
                }
                it = new Intent(getActivity(), MyLoveActivity.class);
                startActivity(it);
                break;
        }
    }
}
