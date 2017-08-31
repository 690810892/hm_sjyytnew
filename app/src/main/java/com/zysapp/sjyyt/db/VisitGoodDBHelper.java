/*
 * Copyright (C) 2014 The Android Client Of Demo Project
 * 
 *     The BeiJing PingChuanJiaHeng Technology Co., Ltd.
 * 
 * Author:Yang ZiTian
 * You Can Contact QQ:646172820 Or Email:mail_yzt@163.com
 */
package com.zysapp.sjyyt.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.zysapp.sjyyt.model.GoogsInfor;

import java.util.ArrayList;


/**
 * 商品信息信息数据库帮助类
 */
public class VisitGoodDBHelper extends DBHelper {
	String tableName = VISIT_GOOD;

	private static VisitGoodDBHelper mClient;

	public VisitGoodDBHelper(Context context) {
		super(context);
	}

	public static VisitGoodDBHelper get(Context context) {
		return mClient == null ? mClient = new VisitGoodDBHelper(context) : mClient;
	}

	/**
	 * 判断信息是插入还是更新
	 * */
	public boolean insertOrUpdate(GoogsInfor good) {
		if (isExist(good.getId()))
			return update(good);
		else
			return insert(good);
	}

	/**
	 * 商品不存在的情况下，将数据插入表中
	 * */
	public boolean insert(GoogsInfor good) {
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			String sql = ("insert into "
					+ tableName
					+ " ( id, imgurl, name, price, starcount) " 
					+ " values(?,?,?,?,?)");
			Object[] bindArgs = new Object[] { good.getId(),
					good.getImgurl(), good.getName(), good.getPrice(),
					good.getStarcount()};
			db.execSQL(sql, bindArgs);
		} catch (SQLException e) {
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * 用户已经存在的情况下，更新用户的信息
	 * */
	public boolean update(GoogsInfor good) {
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try {
			String sql = ("update "
					+ tableName
					+ " set id=?, imgurl=?, name=?, price=?, starcount=? "
					+ " where id = '" + good.getId() + "'");
			Object[] bindArgs = new Object[] {  good.getId(),
					good.getImgurl(), good.getName(), good.getPrice(),
					good.getStarcount()};
			
			db.execSQL(sql, bindArgs);
		} catch (SQLException e) {
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * 根据商品的id,来判断这个商品的信息是否存在表中
	 * */
	public boolean isExist(String id) {
		SQLiteDatabase db = getWritableDatabase();
		String sql = ("select * from " + tableName + " where id = '" + id + "'");
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor != null && cursor.getCount() > 0;
		cursor.close();
		db.close();
		return isExist;
	}

	/**
	 * 删除一条记录
	 * */
	public boolean delete(String id) {
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from " + tableName + " where id ='" + id + "'");
		} catch (SQLException e) {
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * 清空
	 * */
	public void clear() {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from " + tableName);
		db.close();
	}

	/**
	 * 判断商品表是否为空
	 * */
	public boolean isEmpty() {
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from " + tableName, null);
		boolean empty = 0 == cursor.getCount();
		cursor.close();
		db.close();
		return empty;  
	}
	
	/**
	 * 获取全部商品
	 * */
	public ArrayList<GoogsInfor> select() {
		String sql = "select id, imgurl, name, price, starcount from " + tableName;
		SQLiteDatabase db = getWritableDatabase();
		ArrayList<GoogsInfor> list = new ArrayList<GoogsInfor>();
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				GoogsInfor video = new GoogsInfor(cursor.getString(0), cursor.getString(1),
						cursor.getString(2), cursor.getString(3),
						cursor.getString(4));
				list.add(video);
				cursor.moveToNext();
			}
		}
		if (cursor != null)
			cursor.close();
		db.close();
		return list;
	}
	/**
	 * 获取全部商品个数
	 * */
	public int selectNum() {
		int num=0;
		String sql = "select id, imgurl, name, price, starcount from " + tableName;
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null && cursor.getCount() > 0) {
			num=cursor.getCount();
		}
		if (cursor != null)
			cursor.close();
		db.close();
		return num;
	}

	/**
	 * 获取商品信息
	 * 
	 * @param
	 * */
	public GoogsInfor selectById(String id) {
		GoogsInfor user = null;
		String sql = "select id, imgurl, name, price, starcount from "
				+ tableName + " where id = '" + id + "'";
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			user = new GoogsInfor(cursor.getString(0), cursor.getString(1),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4));
		}
		cursor.close();
		db.close();
		return user;
	}
}
