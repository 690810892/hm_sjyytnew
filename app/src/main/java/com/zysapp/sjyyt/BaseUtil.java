package com.zysapp.sjyyt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.SpannableString;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hemaapp.hm_FrameWork.emoji.EmojiParser;
import com.hemaapp.hm_FrameWork.emoji.ParseEmojiMsgUtil;
import com.zysapp.sjyyt.activity.LoginActivity;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import xtom.frame.XtomActivityManager;
import xtom.frame.util.XtomBaseUtil;
import xtom.frame.util.XtomSharedPreferencesUtil;
import xtom.frame.util.XtomTimeUtil;
import xtom.frame.util.XtomToastUtil;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * 
 */
 public class BaseUtil {
	private static double EARTH_RADIUS = 6378.137;// 地球半径
	public static int getScreenWidth(Context context) {
		// 取得窗口属性
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		// 窗口的宽度
		return wm.getDefaultDisplay().getWidth();
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static String transDuration(long duration) {
		String ds = "";
		long min = duration / 60;
		if (min < 60) {
			ds += (min + "分钟");
		} else {
			long hour = min / 60;
			long rm = min % 60;
			if (rm > 0)
				ds += (hour + "小时" + rm + "分钟");
			else
				ds += (hour + "小时");
		}
		return ds;
	}
	public static String getMin(long duration) {
		String ds = "";
		long min = duration / 60;
		if (min < 60) {
			if(min<0)
				ds="0";
			else
				ds += min;
		} else {
			long hour = min / 60;
			long rm = min % 60;
			if (rm > 0)
				ds += rm ;
			else
				ds = "";
		}
		return ds;
	}
	public static String getHour(long duration) {
		String ds = "";
		long min = duration / 60;
		if (min < 60) {
			ds = "";
		} else {
			long hour = min / 60;
			long rm = min % 60;
			ds = hour+"" ;
		}
		return ds;
	}
	public static String transDistance(float distance) {
		String ds = "";
		if (distance < 1000) {
			ds += (distance + "m");
		} else {
			float km = distance / 1000;
			ds += (String.format(Locale.getDefault(), "%.1f", km) + "km");
		}
		return ds;
	}
	public static String TransTime(String time, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());
		try {
			Date date1 = sdf.parse(time);
			SimpleDateFormat dateFormat = new SimpleDateFormat(format,
					Locale.getDefault());// "yyyy年MM月dd HH:mm"
			return dateFormat.format(date1);
		} catch (Exception e) {
			return null;
		}
	}
	public static String TransTime2(String time, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日",
				Locale.getDefault());
		try {
			Date date1 = sdf.parse(time);
			SimpleDateFormat dateFormat = new SimpleDateFormat(format,
					Locale.getDefault());// "yyyy年MM月dd HH:mm"
			return dateFormat.format(date1);
		} catch (Exception e) {
			return null;
		}
	}
	public static String TransTime3(String time, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日 HH:mm:ss",
				Locale.getDefault());
		try {
			Date date1 = sdf.parse(time);
			SimpleDateFormat dateFormat = new SimpleDateFormat(format,
					Locale.getDefault());// "yyyy年MM月dd HH:mm"
			return dateFormat.format(date1);
		} catch (Exception e) {
			return null;
		}
	}
	public static String TransTime4(String time, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		try {
			Date date1 = sdf.parse(time);
			SimpleDateFormat dateFormat = new SimpleDateFormat(format,
					Locale.getDefault());// "yyyy年MM月dd HH:mm"
			return dateFormat.format(date1);
		} catch (Exception e) {
			return null;
		}
	}
	public static String TransTime5(String time, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.getDefault());
		try {
			Date date1 = sdf.parse(time);
			SimpleDateFormat dateFormat = new SimpleDateFormat(format,
					Locale.getDefault());// "yyyy年MM月dd HH:mm"
			return dateFormat.format(date1);
		} catch (Exception e) {
			return null;
		}
	}
	public static int compare_date(String DATE1, String DATE2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
	/**
	 * 计算两点间的距离
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static Double GetDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	/**
	 * 隐藏用户名
	 * 
	 * @param nickname
	 * @return
	 */
	public static String hideNickname(String nickname) {
		int length = nickname.length();
		String first = nickname.substring(0, 1);
		String last = nickname.substring(length - 1, length);
		String x = "";
		for (int i = 0; i < length - 2; i++) {
			x += "*";
		}
		return first + x + last;
	}

	/**
	 * 退出登录
	 */
	public static void clientLoginout(Context context) {
		XtomSharedPreferencesUtil.save(context, "password", "");
		XtomSharedPreferencesUtil.save(context, "username", "");
		BaseApplication application = BaseApplication.getInstance();
		application.setUser(null);
		XtomActivityManager.finishAll();
		Intent it = new Intent(context, LoginActivity.class);
		context.startActivity(it);
	}

	/**
	 * 转换时间显示形式(与当前系统时间比较),在显示即时聊天的时间时使用
	 * 
	 * @param time
	 *            时间字符串
	 * @return String
	 */
	public static String transTimeChat(String time) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.getDefault());
			String current = XtomTimeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			String dian24 = XtomTimeUtil.TransTime(current, "yyyy-MM-dd")
					+ " 24:00:00";
			String dian00 = XtomTimeUtil.TransTime(current, "yyyy-MM-dd")
					+ " 00:00:00";
			Date now = null;
			Date date = null;
			Date d24 = null;
			Date d00 = null;
			try {
				now = sdf.parse(current); // 将当前时间转化为日期
				date = sdf.parse(time); // 将传入的时间参数转化为日期
				d24 = sdf.parse(dian24);
				d00 = sdf.parse(dian00);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			long diff = now.getTime() - date.getTime(); // 获取二者之间的时间差值
			long min = diff / (60 * 1000);
			if (min <= 5)
				return "刚刚";
			if (min < 60)
				return min + "分钟前";

			if (now.getTime() <= d24.getTime()
					&& date.getTime() >= d00.getTime())
				return "今天" + XtomTimeUtil.TransTime(time, "HH:mm");

			int sendYear = Integer
					.valueOf(XtomTimeUtil.TransTime(time, "yyyy"));
			int nowYear = Integer.valueOf(XtomTimeUtil.TransTime(current,
					"yyyy"));
			if (sendYear < nowYear)
				return XtomTimeUtil.TransTime(time, "yyyy-MM-dd HH:mm");
			else
				return XtomTimeUtil.TransTime(time, "MM-dd HH:mm");
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 获取MD5的加密秘钥
	 * */
	public static String md5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(
					string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}

	/**
	 * 计算百分率 if(x>y) (x-y)/total else (y-x)/total value 是除数 2 total 是被除数 3
	 * */
	public static String getValue(double x, double y, double total) {
		String result = "";// 接受百分比的值
		String flag = "+"; // 数据的正负值
		DecimalFormat df1 = new DecimalFormat("0.00%"); // ##.00%
		// 百分比格式，后面不足2位的用0补齐
		if (x >= y) {
			flag = "+";
			double tempresult = (x - y) / total;
			result = df1.format(tempresult);
		} else {
			flag = "-";
			double tempresult = (y - x) / total;
			result = df1.format(tempresult);
		}
		return flag + result;
	}

	/**
	 * 计算好评率
	 * */
	public static String getRate(int x, int total) {
		String result = "";// 接受百分比的值
		String flag = "+"; // 数据的正负值
		DecimalFormat df1 = new DecimalFormat("0.0%"); // ##.00%
		// 百分比格式，后面不足2位的用0补齐
		double tempresult = x / total;
		result = df1.format(tempresult);
		return flag + result;
	}

	/**
	 * 获利
	 * */
	public static String income(String old, String now, String count) {
		String result = "";
		String flag = "+";
		Double x = Double.parseDouble(old);
		Double y = Double.parseDouble(now);
		int cou = Integer.parseInt(count);
		if (x >= y) {
			double t = x - y;
			double c = t * cou;
			flag = "-";
			result = String.valueOf(c);
		} else {
			double t = y - x;
			double c = t * cou;
			flag = "+";
			result = String.valueOf(c);
		}
		return flag + result;
	}

	// 聊天中的表情
	public static void SetMessageTextView(Context mContext, TextView mtextview,
			String mcontent) {
		if (mcontent == null || "".equals(mcontent)) {
			mtextview.setText("");
			return;
		}

		String unicode = EmojiParser.getInstance(mContext).parseEmoji(mcontent);
		SpannableString spannableString = ParseEmojiMsgUtil
				.getExpressionString(mContext, unicode);
		mtextview.setText(spannableString);
	}

	/**
	 * 计算缓存大小的表现形式
	 * */
	public static String getSize(long size) {

		/** size 如果 小于1024 * 1024,以KB单位返回,反则以MB单位返回 */

		DecimalFormat df = new DecimalFormat("###.##");
		float f;
		if (size < 1024 * 1024) {
			f = (float) ((float) size / (float) 1024);
			return (df.format(new Float(f).doubleValue()) + "KB");
		} else {
			f = (float) ((float) size / (float) (1024 * 1024));
			return (df.format(new Float(f).doubleValue()) + "MB");
		}
	}

	/**
	 * 计算返回的星星数
	 * */
	public static  int getCount(String replyCount, String goodCount){
		if(replyCount == null || "".equals(replyCount) 
				|| goodCount == null || "".equals(goodCount) )
			return 0;
		int total = Integer.parseInt(replyCount);
		int good = Integer.parseInt(goodCount);
		double d = good*100/total;
		if(d<20)
			return 0;
		else if(d>=20 && d<40)
			return 1;
		else if(d>=40 && d<60)
			return 2;
		else if(d>=60 && d<80)
			return 4;
		else if(d>=80 && d<100)
			return 4;
		else {
			return 5;
		}
	}




	public static String transString(Date d){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(d);
		return str;
	}
	//	/**
	//	 * 显示等级
	//	 * */
	//	public static void getLevel(String level, ImageView imageView) {
	//		if(level==null)
	//			level="0";
	//		int i=Integer.parseInt(level);
	//		if(i==0)
	//			imageView.setImageResource(R.drawable.v0);
	//		if(i==1)
	//			imageView.setImageResource(R.drawable.v1);
	//		if(i==2)
	//			imageView.setImageResource(R.drawable.v2);
	//		if(i==3)
	//			imageView.setImageResource(R.drawable.v3);
	//		if(i==4)
	//			imageView.setImageResource(R.drawable.v4);
	//		if(i==5)
	//			imageView.setImageResource(R.drawable.v5);
	//		if(i==6)
	//			imageView.setImageResource(R.drawable.v6);
	//		if(i==7)
	//			imageView.setImageResource(R.drawable.v7);
	//		if(i==8)
	//			imageView.setImageResource(R.drawable.v8);
	//		if(i==9)
	//			imageView.setImageResource(R.drawable.v9);
	//		if(i==10)
	//			imageView.setImageResource(R.drawable.v10);
	//	}
	/**
	 * 显示星星(包含灰色星星)稍小一点的星星
	 * */
	public static void getStar0(String replycountString,String starcountString, ImageView imageView0,ImageView imageView1,ImageView imageView2,
			ImageView imageView3,ImageView imageView4) {
		float replycount=1;
		float starcount=0;
		float ll=0;
		int i=0;
		if(replycountString.equals("0"))
			i=0;
		else{
			try {
				replycount=Float.parseFloat(replycountString);
				starcount=Float.parseFloat(starcountString);
				ll=starcount/replycount;
				i=(new BigDecimal(ll).setScale(0, BigDecimal.ROUND_HALF_UP)).intValue();
			} catch (Exception e) {
				i=0;
			}
		}
//		if(i==0){
//			imageView0.setImageResource(R.drawable.star_n_2);
//			imageView1.setImageResource(R.drawable.star_n_2);
//			imageView2.setImageResource(R.drawable.star_n_2);
//			imageView3.setImageResource(R.drawable.star_n_2);
//			imageView4.setImageResource(R.drawable.star_n_2);
//		}
//		if(i==1){
//			imageView0.setImageResource(R.drawable.star_p_2);
//			imageView1.setImageResource(R.drawable.star_n_2);
//			imageView2.setImageResource(R.drawable.star_n_2);
//			imageView3.setImageResource(R.drawable.star_n_2);
//			imageView4.setImageResource(R.drawable.star_n_2);
//		}
//		if(i==2){
//			imageView0.setImageResource(R.drawable.star_p_2);
//			imageView1.setImageResource(R.drawable.star_p_2);
//			imageView2.setImageResource(R.drawable.star_n_2);
//			imageView3.setImageResource(R.drawable.star_n_2);
//			imageView4.setImageResource(R.drawable.star_n_2);
//		}
//		if(i==3){
//			imageView0.setImageResource(R.drawable.star_p_2);
//			imageView1.setImageResource(R.drawable.star_p_2);
//			imageView2.setImageResource(R.drawable.star_p_2);
//			imageView3.setImageResource(R.drawable.star_n_2);
//			imageView4.setImageResource(R.drawable.star_n_2);
//		}
//		if(i==4){
//			imageView0.setImageResource(R.drawable.star_p_2);
//			imageView1.setImageResource(R.drawable.star_p_2);
//			imageView2.setImageResource(R.drawable.star_p_2);
//			imageView3.setImageResource(R.drawable.star_p_2);
//			imageView4.setImageResource(R.drawable.star_n_2);
//		}
//		if(i==5){
//			imageView0.setImageResource(R.drawable.star_p_2);
//			imageView1.setImageResource(R.drawable.star_p_2);
//			imageView2.setImageResource(R.drawable.star_p_2);
//			imageView3.setImageResource(R.drawable.star_p_2);
//			imageView4.setImageResource(R.drawable.star_p_2);
//		}
	}
	/**
	 * 显示星星(包含灰色星星)稍小一点的星星(评论列表)
	 * */
	public static void getStar1(String starcountString, ImageView imageView0,ImageView imageView1,ImageView imageView2,
			ImageView imageView3,ImageView imageView4) {
		int i=0;
		try {
			i=Integer.parseInt(starcountString);
		} catch (Exception e) {
			i=0;
		}
//		if(i==0){
//			imageView0.setImageResource(R.drawable.star_n_2);
//			imageView1.setImageResource(R.drawable.star_n_2);
//			imageView2.setImageResource(R.drawable.star_n_2);
//			imageView3.setImageResource(R.drawable.star_n_2);
//			imageView4.setImageResource(R.drawable.star_n_2);
//		}
//		if(i==1){
//			imageView0.setImageResource(R.drawable.star_p_2);
//			imageView1.setImageResource(R.drawable.star_n_2);
//			imageView2.setImageResource(R.drawable.star_n_2);
//			imageView3.setImageResource(R.drawable.star_n_2);
//			imageView4.setImageResource(R.drawable.star_n_2);
//		}
//		if(i==2){
//			imageView0.setImageResource(R.drawable.star_p_2);
//			imageView1.setImageResource(R.drawable.star_p_2);
//			imageView2.setImageResource(R.drawable.star_n_2);
//			imageView3.setImageResource(R.drawable.star_n_2);
//			imageView4.setImageResource(R.drawable.star_n_2);
//		}
//		if(i==3){
//			imageView0.setImageResource(R.drawable.star_p_2);
//			imageView1.setImageResource(R.drawable.star_p_2);
//			imageView2.setImageResource(R.drawable.star_p_2);
//			imageView3.setImageResource(R.drawable.star_n_2);
//			imageView4.setImageResource(R.drawable.star_n_2);
//		}
//		if(i==4){
//			imageView0.setImageResource(R.drawable.star_p_2);
//			imageView1.setImageResource(R.drawable.star_p_2);
//			imageView2.setImageResource(R.drawable.star_p_2);
//			imageView3.setImageResource(R.drawable.star_p_2);
//			imageView4.setImageResource(R.drawable.star_n_2);
//		}
//		if(i==5){
//			imageView0.setImageResource(R.drawable.star_p_2);
//			imageView1.setImageResource(R.drawable.star_p_2);
//			imageView2.setImageResource(R.drawable.star_p_2);
//			imageView3.setImageResource(R.drawable.star_p_2);
//			imageView4.setImageResource(R.drawable.star_p_2);
//		}

	}
	/**
	 * 显示星星(包含灰色星星)大的星星
	 * */
	public static void getStar(String replycountString,String starcountString, ImageView imageView0,ImageView imageView1,ImageView imageView2,
			ImageView imageView3,ImageView imageView4) {
		float replycount=1;
		float starcount=0;
		float ll=0;
		int i=0;
		if(replycountString.equals("0"))
			i=0;
		else{
			try {
				replycount=Float.parseFloat(replycountString);
				starcount=Float.parseFloat(starcountString);
				ll=starcount/replycount;
				i=(new BigDecimal(ll).setScale(0, BigDecimal.ROUND_HALF_UP)).intValue();
			} catch (Exception e) {
				i=0;
			}
		}
//		if(i==0){
//			imageView0.setImageResource(R.drawable.star_n);
//			imageView1.setImageResource(R.drawable.star_n);
//			imageView2.setImageResource(R.drawable.star_n);
//			imageView3.setImageResource(R.drawable.star_n);
//			imageView4.setImageResource(R.drawable.star_n);
//		}
//		if(i==1){
//			imageView0.setImageResource(R.drawable.star_p);
//			imageView1.setImageResource(R.drawable.star_n);
//			imageView2.setImageResource(R.drawable.star_n);
//			imageView3.setImageResource(R.drawable.star_n);
//			imageView4.setImageResource(R.drawable.star_n);
//		}
//		if(i==2){
//			imageView0.setImageResource(R.drawable.star_p);
//			imageView1.setImageResource(R.drawable.star_p);
//			imageView2.setImageResource(R.drawable.star_n);
//			imageView3.setImageResource(R.drawable.star_n);
//			imageView4.setImageResource(R.drawable.star_n);
//		}
//		if(i==3){
//			imageView0.setImageResource(R.drawable.star_p);
//			imageView1.setImageResource(R.drawable.star_p);
//			imageView2.setImageResource(R.drawable.star_p);
//			imageView3.setImageResource(R.drawable.star_n);
//			imageView4.setImageResource(R.drawable.star_n);
//		}
//		if(i==4){
//			imageView0.setImageResource(R.drawable.star_p);
//			imageView1.setImageResource(R.drawable.star_p);
//			imageView2.setImageResource(R.drawable.star_p);
//			imageView3.setImageResource(R.drawable.star_p);
//			imageView4.setImageResource(R.drawable.star_n);
//		}
//		if(i==5){
//			imageView0.setImageResource(R.drawable.star_p);
//			imageView1.setImageResource(R.drawable.star_p);
//			imageView2.setImageResource(R.drawable.star_p);
//			imageView3.setImageResource(R.drawable.star_p);
//			imageView4.setImageResource(R.drawable.star_p);
//		}

	}
	/**
	 * 显示星星(不包含灰色星星)
	 * */
	public static void getStarNo(String star, ImageView imageView0,ImageView imageView1,ImageView imageView2,
			ImageView imageView3,ImageView imageView4) {
		if(star==null)
			star="0";
		Double ll=(Double.parseDouble(star));
		int i=(new BigDecimal(ll).setScale(0, BigDecimal.ROUND_HALF_UP)).intValue();

		if(i==0){
			imageView0.setVisibility(View.GONE);
			imageView1.setVisibility(View.GONE);
			imageView2.setVisibility(View.GONE);
			imageView3.setVisibility(View.GONE);
			imageView4.setVisibility(View.GONE);
		}
		if(i==1){
			imageView0.setVisibility(View.VISIBLE);
			imageView1.setVisibility(View.GONE);
			imageView2.setVisibility(View.GONE);
			imageView3.setVisibility(View.GONE);
			imageView4.setVisibility(View.GONE);
		}
		if(i==2){
			imageView0.setVisibility(View.VISIBLE);
			imageView1.setVisibility(View.VISIBLE);
			imageView2.setVisibility(View.GONE);
			imageView3.setVisibility(View.GONE);
			imageView4.setVisibility(View.GONE);
		}
		if(i==3){
			imageView0.setVisibility(View.VISIBLE);
			imageView1.setVisibility(View.VISIBLE);
			imageView2.setVisibility(View.VISIBLE);
			imageView3.setVisibility(View.GONE);
			imageView4.setVisibility(View.GONE);
		}
		if(i==4){
			imageView0.setVisibility(View.VISIBLE);
			imageView1.setVisibility(View.VISIBLE);
			imageView2.setVisibility(View.VISIBLE);
			imageView3.setVisibility(View.VISIBLE);
			imageView4.setVisibility(View.GONE);
		}
		if(i==5){
			imageView0.setVisibility(View.VISIBLE);
			imageView1.setVisibility(View.VISIBLE);
			imageView2.setVisibility(View.VISIBLE);
			imageView3.setVisibility(View.VISIBLE);
			imageView4.setVisibility(View.VISIBLE);
		}

	}
	public static void getDays(ArrayList<String> days,ArrayList<String> days2){
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		calendar.clear();   
		calendar.set(Calendar.YEAR,year);   
		calendar.set(Calendar.MONTH,month-1);//注意,Calendar对象默认一月为0               
		int nowMothDays1 = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数  
		int nowMoth1=month;int nowYear1=year;
		month++;//第二个月
		if(month>12){
			month=month-12;
			year++;
		}
		calendar.set(Calendar.YEAR,year);   
		calendar.set(Calendar.MONTH,month-1);
		int nowMothDays2 = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数  
		int nowMoth2=month;
		int nowYear2=year;
		month++;//第三个月
		if(month>12){
			month=month-12;
			year++;
		}
		calendar.set(Calendar.YEAR,year);   
		calendar.set(Calendar.MONTH,month-1);
		int nowMothDays3 = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数  
		int nowMoth3=month;int nowYear3=year;
		month++;//第四个月
		if(month>12){
			month=month-12;
			year++;
		}
		calendar.set(Calendar.YEAR,year);   
		calendar.set(Calendar.MONTH,month-1);
		int nowMothDays4 = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数  
		int nowMoth4=month;int nowYear4=year;
		month++;//第五个月
		if(month>12){
			month=month-12;
			year++;
		}
		calendar.set(Calendar.YEAR,year);   
		calendar.set(Calendar.MONTH,month-1);
		int nowMothDays5 = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数  
		int nowMoth5=month;int nowYear5=year;
		month++;//第六个月
		if(month>12){
			month=month-12;
			year++;
		}
		calendar.set(Calendar.YEAR,year);   
		calendar.set(Calendar.MONTH,month-1);
		int nowMothDays6 = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数  
		int nowMoth6=month;int nowYear6=year;
		for(int i=0;i<nowMothDays1;i++){
			days.add(i, nowMoth1+"月"+(i+1)+"日");
			if(nowMoth1<10)
				days2.add(i, nowYear1+"-0"+nowMoth1+"-"+(i+1)+" ");
			else
				days2.add(i, nowYear1+"-"+nowMoth1+"-"+(i+1)+" ");
		}
		for(int i=0;i<nowMothDays2;i++){
			days.add(i+nowMothDays1, nowMoth2+"月"+(i+1)+"日");
			if(nowMoth2<10)
				days2.add(i+nowMothDays1, nowYear2+"-0"+nowMoth2+"-"+(i+1)+" ");
			else
				days2.add(i+nowMothDays1, nowYear2+"-"+nowMoth2+"-"+(i+1)+" ");
		}
		for(int i=0;i<nowMothDays3;i++){
			days.add(i+nowMothDays1+nowMothDays2, nowMoth3+"月"+(i+1)+"日");
			if(nowMoth3<10)
				days2.add(i+nowMothDays1+nowMothDays2, nowYear3+"-0"+nowMoth3+"-"+(i+1)+" ");
			else
				days2.add(i+nowMothDays1+nowMothDays2, nowYear3+"-"+nowMoth3+"-"+(i+1)+" ");
		}
		for(int i=0;i<nowMothDays4;i++){
			days.add(i+nowMothDays1+nowMothDays2+nowMothDays3, nowMoth4+"月"+(i+1)+"日");
			if(nowMoth4<10)
				days2.add(i+nowMothDays1+nowMothDays2+nowMothDays3, nowYear4+"-0"+nowMoth4+"-"+(i+1)+" ");
			else
				days2.add(i+nowMothDays1+nowMothDays2+nowMothDays3, nowYear4+"-"+nowMoth4+"-"+(i+1)+" ");
		}
		for(int i=0;i<nowMothDays5;i++){
			days.add(i+nowMothDays1+nowMothDays2+nowMothDays3+nowMothDays4, nowMoth5+"月"+(i+1)+"日");
			if(nowMoth5<10)
				days2.add(i+nowMothDays1+nowMothDays2+nowMothDays3+nowMothDays4, nowYear5+"-0"+nowMoth5+"-"+(i+1)+" ");
			else
				days2.add(i+nowMothDays1+nowMothDays2+nowMothDays3+nowMothDays4, nowYear5+"-"+nowMoth5+"-"+(i+1)+" ");
		}
		for(int i=0;i<nowMothDays6;i++){
			days.add(i+nowMothDays1+nowMothDays2+nowMothDays3+nowMothDays4+nowMothDays5, nowMoth6+"月"+(i+1)+"日");
			if(nowMoth6<10)
				days2.add(i+nowMothDays1+nowMothDays2+nowMothDays3+nowMothDays4+nowMothDays5, nowYear6+"-0"+nowMoth6+"-"+(i+1)+" ");
			else
				days2.add(i+nowMothDays1+nowMothDays2+nowMothDays3+nowMothDays4+nowMothDays5, nowYear6+"-"+nowMoth6+"-"+(i+1)+" ");
		}
	}
	/**
	 * 退出软件
	 * 
	 * @param context
	 */
	public static void exit(Context context) {
		XtomActivityManager.finishAll();
	}
	public static void setColor(Activity activity, int color) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 设置状态栏透明
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 生成一个状态栏大小的矩形
			View statusView = createStatusView(activity, color);
			// 添加 statusView 到布局中
			ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
			decorView.addView(statusView);
			// 设置根布局的参数
			ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
			rootView.setFitsSystemWindows(true);
			rootView.setClipToPadding(true);
		}
	}
	private static View createStatusView(Activity activity, int color) {
		// 获得状态栏高度
		int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
		int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

		// 绘制一个和状态栏一样高的矩形
		View statusView = new View(activity);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				statusBarHeight);
		statusView.setLayoutParams(params);
		statusView.setBackgroundColor(color);
		return statusView;
	}
	/**
	 * 当点击其他View时隐藏软键盘
	 * @param activity
	 * @param ev
	 * @param excludeViews  点击这些View不会触发隐藏软键盘动作
	 */
	public static final void hideInputWhenTouchOtherView(Activity activity, MotionEvent ev, List<View> excludeViews){


		if (ev.getAction() == MotionEvent.ACTION_DOWN){
			if (excludeViews != null && !excludeViews.isEmpty()){
				for (int i = 0; i < excludeViews.size(); i++){
					if (isTouchView(excludeViews.get(i), ev)){
						return;
					}
				}
			}
			View v = activity.getCurrentFocus();
			if (isShouldHideInput(v, ev)){
				InputMethodManager inputMethodManager = (InputMethodManager)
						activity.getSystemService(INPUT_METHOD_SERVICE);
				if (inputMethodManager != null){
					inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
//				((ChatGroupActivity)activity).hideBottom();
			}

		}
	}
	public static final void hideInputWhenTouchOtherViewBase(Activity activity, MotionEvent ev, List<View> excludeViews){

		if (ev.getAction() == MotionEvent.ACTION_DOWN){
			if (excludeViews != null && !excludeViews.isEmpty()){
				for (int i = 0; i < excludeViews.size(); i++){
					if (isTouchView(excludeViews.get(i), ev)){
						return;
					}
				}
			}
			View v = activity.getCurrentFocus();
			if (isShouldHideInput(v, ev)){
				InputMethodManager inputMethodManager = (InputMethodManager)
						activity.getSystemService(INPUT_METHOD_SERVICE);
				if (inputMethodManager != null){
					inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}

		}
	}
	public static final boolean isTouchView(View view, MotionEvent event){
		if (view == null || event == null){
			return false;
		}
		int[] leftTop = {0, 0};
		view.getLocationInWindow(leftTop);
		int left = leftTop[0];
		int top = leftTop[1];
		int bottom = top + view.getHeight();
		int right = left + view.getWidth();
		if (event.getRawX() > left && event.getRawX() < right
				&& event.getRawY() > top && event.getRawY() < bottom){
			return true;
		}
		return false;
	}

	public static final boolean isShouldHideInput(View v, MotionEvent event){
		if (v != null && (v instanceof EditText)){
			return !isTouchView(v, event);
		}
		return false;
	}
	public static final void hideInputWhenTouchOtherView2(Activity activity, MotionEvent ev, List<View> excludeViews){


		if (ev.getAction() == MotionEvent.ACTION_DOWN){
			if (excludeViews != null && !excludeViews.isEmpty()){
				for (int i = 0; i < excludeViews.size(); i++){
					if (isTouchView(excludeViews.get(i), ev)){
						return;
					}
				}
			}
			View v = activity.getCurrentFocus();
			if (isShouldHideInput(v, ev)){
				InputMethodManager inputMethodManager = (InputMethodManager)
						activity.getSystemService(INPUT_METHOD_SERVICE);
				if (inputMethodManager != null){
					inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
//				((ChatPrivateActivity)activity).hideBottom();
			}

		}
	}
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	public static void hideInput(Context context,View v){
		((InputMethodManager)context.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow
				(v.getWindowToken(), 0);
	}
	public static String getAge(Context context ,String birthday) {
		Calendar calendar = Calendar.getInstance();
		if (XtomBaseUtil.isNull(birthday) || "0000-00-00".equals(birthday)) {
			return "";
		}
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(birthday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date == null) {
			return "";
		}

		int yearNow = calendar.get(Calendar.YEAR);
		int monthNow = calendar.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.setTime(date);
		int yearBirth = calendar.get(Calendar.YEAR);
		int monthBirth = calendar.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = calendar.get(Calendar.DAY_OF_MONTH);

		Integer age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}
		if (age < 0) {
			XtomToastUtil.showLongToast(context,"生日不合理");
			return "0";
		}
		return age.toString();
	}
	public static String getTime(Date date) {//可根据需要自行截取数据显示
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

}
