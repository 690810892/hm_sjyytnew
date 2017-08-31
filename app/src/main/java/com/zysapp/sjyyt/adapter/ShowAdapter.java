package com.zysapp.sjyyt.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;

import java.io.IOException;

import xtom.frame.XtomActivity;


/**
 * 信息展示界面的图片适配器
 */
public class ShowAdapter extends PagerAdapter {
	private XtomActivity mContext;
	private String[] imgs;
	private View view;
	private RadioGroup mIndicator;
	private int size;

	public ShowAdapter(Context mContext, String[] imgs, View view) {
		this.mContext = (XtomActivity) mContext;
		this.imgs = imgs;
		this.view = view;
		//init();
	}
	
	private void init() {
//		size = ((BitmapDrawable) mContext.getResources().getDrawable(
//				R.mipmap.indicator_show_s)).getBitmap().getWidth();
//		mIndicator = (RadioGroup) view.findViewById(R.id.radiogroup);
//		mIndicator.removeAllViews();
//		if (getCount() > 1){
//			mIndicator.setVisibility(View.INVISIBLE);
//			for (int i = 0; i < getCount(); i++) {
//				RadioButton button = new RadioButton(mContext);
//				button.setButtonDrawable(R.drawable.indicator_show);
//				button.setId(i);
//				button.setClickable(false);
//				LayoutParams params2 = new LayoutParams(
//						(i == getCount() - 1) ? size : size * 2, size);
//				button.setLayoutParams(params2);
//				if (i == 0)
//					button.setChecked(true);
//				mIndicator.addView(button);
//			}
//		}else{
//			mIndicator.setVisibility(View.INVISIBLE);
//		}
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

	}

	@SuppressWarnings("deprecation")
	@Override
	public Object instantiateItem(ViewGroup container, int position) { // 这个方法用来实例化页卡
		ImageView mView;
		if (container.getChildAt(position) == null) {
			mView = new ImageView(mContext);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			mView.setLayoutParams(params);
			mView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			try {
				BitmapDrawable d = new BitmapDrawable(mContext.getAssets()
						.open(imgs[position]));
				mView.setImageDrawable(d);
				//mView.setBackgroundDrawable(d);
			} catch (IOException e) {
				e.printStackTrace();
			}
			container.addView(mView, position);
		} else
			mView = (ImageView)container.getChildAt(position);

		return mView;
	}

	@Override
	public int getCount() {
		return imgs.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	public RadioGroup getmIndicator() {
		return mIndicator;
	}

}
