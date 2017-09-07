package com.zysapp.sjyyt.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.zysapp.sjyyt.BaseFragmentActivity;
import com.zysapp.sjyyt.BaseHttpInformation;
import com.zysapp.sjyyt.adapter.MyLovePageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我喜欢的
 */
public class MyLoveActivity extends BaseFragmentActivity {

    @BindView(R.id.title_btn_left)
    ImageButton titleBtnLeft;
    @BindView(R.id.title_btn_right)
    Button titleBtnRight;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.rbt0)
    RadioButton rbt0;
    @BindView(R.id.rbt1)
    RadioButton rbt1;
    @BindView(R.id.type)
    RadioGroup type;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private int pagerPosition = 0;
    private MyLovePageAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mylove);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initData();
    }

    public void initData() {
        pagerAdapter = new MyLovePageAdapter(getSupportFragmentManager());
        viewpager.setAdapter(pagerAdapter);
        viewpager.setCurrentItem(pagerPosition);
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            default:
                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            default:
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask,
                                            HemaBaseResult baseResult) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            default:
                break;
        }

    }

    @Override
    protected void callBackForServerFailed(HemaNetTask netTask,
                                           HemaBaseResult baseResult) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
            default:
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {
        BaseHttpInformation information = (BaseHttpInformation) netTask
                .getHttpInformation();
        switch (information) {
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
        titleText.setText("我喜欢的");
        titleBtnLeft.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBtnRight.setVisibility(View.GONE);

        type.setOnCheckedChangeListener(new TypeListener());
        viewpager.setOnPageChangeListener(new PageChangeListener());
    }

    @Override
    public void onPublish(int progress) {

    }

    @Override
    public void onChange(int position) {

    }

    @OnClick(R.id.title_btn_left)
    public void onViewClicked() {
    }

    private class TypeListener implements OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rbt0:
                    viewpager.setCurrentItem(0);
                    break;
                case R.id.rbt1:
                    viewpager.setCurrentItem(1);
                    break;
            }
        }
    }

    private class PageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int position) {
            pagerPosition = position;
            switch (position) {
                case 0:
                    type.check(R.id.rbt0);
                    break;
                case 1:
                    type.check(R.id.rbt1);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // TODO Auto-generated method stub

        }

    }
}
