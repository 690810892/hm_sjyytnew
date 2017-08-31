package com.zysapp.sjyyt.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

/**
 * 搜索历史DB
 */
public class Search1DBClient extends DBHelper {
	private static Search1DBClient mClient;
	@SuppressWarnings("unused")
	private static Context mContext;

	private String tableName = SYS_CASCADE_SEARCH2;

	private Search1DBClient(Context context) {
		super(context);
	}

	public static Search1DBClient get(Context context) {
		mContext = context;
		return mClient == null ? mClient = new Search1DBClient(context)
				: mClient;
	}

	/**
	 * 插入一条记录
	 * 
	 * @param
	 * @return
	 */
	public boolean insert(String search) {
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try {
			db.execSQL(
					("insert into " + tableName + "(searchname) " + "values (?)"),
					new Object[] { search });

		} catch (SQLException e) {
			success = false;
			Log.w("insert", "insert e=" + e);
		}
		db.close();
		return success;
	}

	/**
	 * 清空
	 */
	public void clear() {
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from " + tableName);
			//db.execSQL("drop table " + tableName);
		} catch (SQLiteException e) {
		} catch (SQLException e) {
		}
		db.close();
	}
	/**
	 * 删除
	 */
	public boolean delete(String name) {
		boolean success;
		String conditions = " where searchname='" + name + "'";
		String sql = "delete from " + tableName + conditions;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL(sql);
			success = true;
		} catch (Exception e) {
			success = false;
		}
		db.close();
		return success;
	}
	/**
	 * 判断表是否为空
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from " + tableName, null);
		int num = cursor.getCount();
		cursor.close();
		db.close();
		return 0 == num;
	}

	/**
	 * 获取
	 * 
	 * @return
	 */
	public ArrayList<String> select() {
		ArrayList<String> counts = null;
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = null;
		try {
			cursor = db.rawQuery("select * from " + tableName, null);
			if (cursor != null && cursor.getCount() > 0) {
				counts = new ArrayList<String>();
				cursor.moveToFirst();
				String count;
				for (int i = 0; i < cursor.getCount(); i++) {
					count = cursor.getString(0);
					counts.add(0, count);
					cursor.moveToNext();
				}
			}

		} catch (Exception e) {
			Log.w("select", "select e=" + e);
		}
		if (cursor != null)
			cursor.close();
		db.close();
		return counts;
	}
}
