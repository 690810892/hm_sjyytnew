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
	public static final String SYS_ROOT = "http://124.128.23.74:8008/group16/hm_cfjlr/";
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
	//车服经理人
//	MD5: 6A:AC:36:58:0B:1F:B4:AE:24:E9:9C:2B:73:C0:49:53
//	SHA1: 05:65:20:70:EB:71:F2:AE:2E:FC:32:AC:77:FB:B8:34:02:2C:FA:33
//	SHA256: E5:73:7B:43:D2:7D:B6:97:C0:B5:68:61:5A:4B:A4:FC:16:1B:5B:29:DD:C0:90:0B:A1:C2:9B:2B:4E:11:32:CF
//	签名算法名称: SHA256withRSA
//	版本: 3

}
