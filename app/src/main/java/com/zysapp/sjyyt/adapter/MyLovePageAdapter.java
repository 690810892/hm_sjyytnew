package com.zysapp.sjyyt.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zysapp.sjyyt.fragment.FirstPageFragment;
import com.zysapp.sjyyt.fragment.MyLoveChannelFragment;
import com.zysapp.sjyyt.fragment.MyLoveLiveFragment;

public class MyLovePageAdapter extends FragmentPagerAdapter {
	public MyLovePageAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			try {
				fragment = MyLoveLiveFragment.class.newInstance();
			} catch (Exception e) {
				// nothing
			}
			break;
		case 1:
			try {
				fragment = MyLoveChannelFragment.class.newInstance();
			} catch (Exception e) {
				// nothing
			}
			break;
		default:
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
