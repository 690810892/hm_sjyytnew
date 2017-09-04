/*
 * Copyright (C) 2014 The Android Client Of Demo Project
 * 
 *     The BeiJing PingChuanJiaHeng Technology Co., Ltd.
 * 
 * Author:Yang ZiTian
 * You Can Contact QQ:646172820 Or Email:mail_yzt@163.com
 */
package com.zysapp.config;

/**
 *该项目配置信息
 */
public class BaseConfig {

	/**
	 * 是否打印信息开关
	 */
	public static final boolean DEBUG = true;
	/**
	 * 后台服务接口根路径
	 */
	public static final String SYS_ROOT = "http://139.196.72.199:8008/";
	/**
	 * 图片压缩的最大宽度
	 */
	public static final int IMAGE_WIDTH = 640;
	/**
	 * 图片压缩的最大高度
	 */
	public static final int IMAGE_HEIGHT = 3000;
	/**
	 * 图片压缩的失真率
	 */
	public static final int IMAGE_QUALITY = 100;
	/**
	 * 银联支付环境--"00"生产环境,"01"测试环境
	 */
	public static final String UNIONPAY_TESTMODE = "00";
	/**
	 * 录音时长限制(秒)
	 */
	public static final int TIME_RECORD = 90;
	/**
	 * 微信appid
	 */
	public static final String APPID_WEIXIN = "wxb949793dca492f36";//
	/**
	 * 数据库版本号
	 */
	public static final int DATA_BASE_VERSION = 1;
//	证书指纹:    手机音悦台
//	MD5: 61:A2:93:E5:74:C9:1B:A5:8C:4C:9C:03:57:8C:A3:D0
//	SHA1: B0:BF:96:BB:BB:DE:FB:48:65:C5:79:73:C8:85:5F:D7:05:AC:17:07
//	SHA256: 56:55:77:F6:18:AA:A7:FE:22:41:BA:1C:61:6E:37:A1:53:A7:89:AA:ED:89:39:0A:A5:F0:52:96:21:D0:4D:6A
//	签名算法名称: SHA256withRSA
//	版本: 3

}
