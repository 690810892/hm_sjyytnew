package com.zysapp.sjyyt.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * 
 */
public class DBHelper extends SQLiteOpenHelper {
	private static final String DBNAME = "hm_sjyyt01.db";
	/**
	 * 系统初始化信息
	 */
	protected static final String SYSINITINFO = "sysinfor";
	/**
	 * 当前登录用户信息
	 */
	protected static final String USER = "user";
	/**
	 * 访问城市缓存信息
	 */
	protected static final String VISIT_CITYS = "visit_citys";
	/**
	 * 搜索词缓存
	 */
	protected static final String SYS_CASCADE_SEARCH = "sys_cascade_search";
	/**
	 * 搜索词缓存2
	 */
	protected static final String SYS_CASCADE_SEARCH2 = "sys_cascade_search2";
	/**
	 * 访问的商家详情
	 * */
	protected static final String VISIT_SHOP = "visit_shop";
	/**
	 * 访问的商品详情
	 * */
	protected static final String VISIT_GOOD = "visit_good";
	/**
	 * 加入购物车
	 * */
	protected static final String VISIT_CARGOOD = "visit_cargood";

	public DBHelper(Context context) {
		super(context, DBNAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sys = "sys_web_service text,sys_plugins text,sys_show_iospay text, android_must_update text,"
				+ "android_last_version text, iphone_must_update text, iphone_last_version text,"
				+ "sys_chat_ip text, sys_chat_port text,sys_pagesize text,"
				+ "sys_service_phone text,android_update_url text,"
				+ "iphone_update_url text,apad_update_url text,ipad_update_url text,"
				+ "iphone_comment_url text,msg_invite text, start_url text, sys_tousu_phone text, start_img text";
		String sysSQL = "create table " + SYSINITINFO
				+ " (id integer primary key," + sys + ")";
		// 创建系统初始化信息缓存表
		db.execSQL(sysSQL);

		// 创建访问城市缓存表
		String citys = "id text primary key,name text,parentid text,nodepath text,"
				+ "namepath text,charindex text,level text,orderby text";
		String citysSQL = "create table " + VISIT_CITYS + " (" + citys + ")";
		db.execSQL(citysSQL);

		// 创建搜索词缓存表
		String search = "searchname text";
		String searchSQL = "create table " + SYS_CASCADE_SEARCH + " (" + search
				+ ")";
		db.execSQL(searchSQL);
		// 创建搜索词缓存表
				String search2 = "searchname text";
				String searchSQL2 = "create table " + SYS_CASCADE_SEARCH2 + " (" + search2
						+ ")";
				db.execSQL(searchSQL2);

		// 创建访问商家详情缓存表
		String shop = "id text primary key, name text, phone text, address text, businesshour text,"
				+ " lng text, lat text, imgurlbig text, imgurl text, replycount text, goodcount text,"
				+ "loveflag text, type_imgurl text, price text, sendfee text, sendfee_free text, "
				+ "goodrate text, goodlevel text, buy_tips text, content text, type_id text ";
		String shopSQL = "create table " + VISIT_SHOP + " (" + shop + ")";
		db.execSQL(shopSQL);

		// 创建访问商家详情缓存表
		String good = "id text primary key, imgurl text, name text, price text, starcount text "
				;
		String goodSQL = "create table " + VISIT_GOOD + " (" + good + ")";
		db.execSQL(goodSQL);
		
		// 创建购物车缓存表
				String cgood = "id text primary key, merchant_id text, merchant_name text, merchant_imgurl text, "
						+ "phone text, address text, businesshour text, lng text, lat text, name text, content text,"
						+ "detail text, oldprice text, price text, last_count text, visitcount text, replycount text,"
						+ "goodcount text, imgurlbig text, imgurl text, regdate text, loveflag text, "
						+ "buy_tips text, content_url text, blog_type text, sendfee text, sendfee_free text,"
						+ "avatar text, level text, contactname text, othercontact text, sec_hand_district text,"
						+ "sec_hand_client_id text ";
				String cgoodSQL = "create table " + VISIT_CARGOOD + " (" + cgood + ")";
				db.execSQL(cgoodSQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j) {

	}

}
