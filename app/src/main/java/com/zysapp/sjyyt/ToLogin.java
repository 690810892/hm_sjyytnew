package com.zysapp.sjyyt;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zysapp.sjyyt.activity.LoginActivity;
import com.zysapp.sjyyt.activity.R;


public class ToLogin {
	
	private static Builder mBuilder;
	private static Activity mActivity;

	public static void showLogin(final Activity activity) {
		if (mBuilder == null || activity != mActivity) {
			mBuilder = new Builder(activity);
			View view = LayoutInflater.from(activity).inflate(
					R.layout.dialog_item1, null);
			mBuilder.setView(view);
			TextView textview = (TextView) view.findViewById(R.id.textview);
			textview.setText("当前操作需要登录才能完成,\n是否去登录");
			mBuilder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent it = new Intent(activity,
									LoginActivity.class);
							it.putExtra("keytype",1);
							activity.startActivity(it);
						}
					});
			mBuilder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			mBuilder.create().show();
		} else
			mBuilder.show();
	}
}
