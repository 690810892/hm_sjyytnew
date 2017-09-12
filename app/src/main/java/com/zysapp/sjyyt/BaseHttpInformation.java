package com.zysapp.sjyyt;

import com.hemaapp.HemaConfig;
import com.hemaapp.hm_FrameWork.HemaHttpInfomation;
import com.zysapp.config.BaseConfig;
import com.zysapp.sjyyt.model.SysInitInfo;

/**
 * 网络请求信息枚举类
 */
public enum BaseHttpInformation implements HemaHttpInfomation {
	/**
	 * 登录
	 */
	CLIENT_LOGIN(HemaConfig.ID_LOGIN, "client_login", "登录", false),
	// 注意登录接口id必须为HemaConfig.ID_LOGIN
	/**
	 * 第三方登录
	 */
	THIRD_SAVE(HemaConfig.ID_THIRDSAVE, "third_save", "第三方登录", false),
	// 注意第三方登录接口id必须为HemaConfig.ID_THIRDSAVE
	/**
	 * 第三方登录检查
	 */
	THIRD_LOGIN_VERIFY(22, "third_login_verify", "第三方登录检查", false),
	/**
	 * 后台服务接口根路径
	 */
	SYS_ROOT(0, BaseConfig.SYS_ROOT, "后台服务接口根路径", true),
	/**
	 * 系统初始化
	 */
	INIT(1, "index.php/webservice/index/init", "系统初始化", false),
	/**http://192.168.2.146:8008/group7/hm_quanzi/index.php/webservice/index/init
	 * 验证用户名是否合法
	 */
	CLIENT_VERIFY(2, "client_verify", "验证用户名是否合法", false),
	/**
	 * 申请随机验证码
	 */
	CODE_GET(3, "code_get", "申请随机验证码", false),
	/**
	 * 验证随机码
	 */
	CODE_VERIFY(4, "code_verify", "验证随机码", false),
	/**
	 * 验证随机码
	 */
	INVITE_CODE_VERIFY(4, "invite_code_verify", "验证邀请码接口 ", false),
	/**
	 * 用户注册
	 */
	CLIENT_ADD(5, "client_add", "用户注册", false),
	/**
	 * 上传文件（图片，音频，视频）
	 */
	FILE_UPLOAD(6, "file_upload", "上传文件（图片，音频，视频）", false),
	/**
	 * 重设密码
	 */
	PASSWORD_RESET(7, "password_reset", "重设密码", false),
	/**
	 * 退出登录
	 */
	CLIENT_LOGINOUT(8, "client_loginout", "退出登录", false),
	/**
	 * 获取用户个人资料
	 */
	CLIENT_GET(13, "client_get", "获取用户个人资料", false),
	/**
	 * 保存用户资料
	 */
	CLIENT_SAVE(14, "client_save", "保存用户资料", false),
	/**
	 * 修改并保存密码
	 */
	PASSWORD_SAVE(15, "password_save", "修改并保存密码", false),
	/**
	 * 获取用户通知列表
	 */
	NOTICE_LIST(16, "notice_list", "获取用户通知列表", false),
	/**
	 * 保存用户通知操作
	 */
	NOTICE_SAVEOPERATE(17, "notice_saveoperate", "保存用户通知操作", false),
	/**
	 * 硬件注册保存
	 */
	DEVICE_SAVE(18, "device_save", "硬件注册保存", false),
	/**
	 * 获取成员列表
	 */
	CLIENT_LIST(19, "client_list", "获取成员列表", false),
	/**
	 * 好友删除
	 */
	FRIEND_REMOVE(20, "friend_remove", "好友删除", false),
	/**
	 * 好友添加
	 */
	FRIEND_ADD(21, "friend_add", "好友添加", false),
	/**
	 * 获取相册列表信息
	 */
	IMG_LIST(22, "img_list", "获取相册列表信息", false),
	/**
	 * 获取帖子回复列表
	 */
	REPLY_LIST(23, "comment_list", "获取评论列表信息", false),
	/**
	 * 添加评论
	 */
	REPLY_ADD(24, "comment_add", "添加评论", false),
	/**
	 * 订单评论
	 */
	REPLY_ADD2(24, "article_comment_reply", "评论回复", false),
	/**
	 * 放入购物车
	 */
	CART_ADD(25, "cart_add", "放入购物车", false),
	/**
	 * 购物车列表
	 */
	CART_LIST(26, "cart_list", "购物车列表", false),
	/**
	 * 购物车操作
	 */
	CART_OPERATE(27, "cart_operate", "购物车操作", false),
	/**
	 * 获取支付宝交易签名串
	 */
	ALIPAY(28, "OnlinePay/Alipay/alipaysign_get.php", "获取支付宝交易签名串", false),
	/**
	 * 获取银联交易签名串
	 */
	UNIONPAY(29, "OnlinePay/Unionpay/unionpay_get.php", "获取银联交易签名串", false),
	/**
	 * 用户账户余额付款
	 */
	CLIENT_ACCOUNTPAY(30, "client_accountpay", "用户账户余额付款", false),
	/**
	 * 意见反馈
	 */
	ADVICE_ADD(31, "advice_add", "意见反馈", false),
	/**
	 * 发送聊天消息
	 */
	MSG_ADD(32, "msg_add", "发送聊天消息", false),
	/**
	 * 获取聊天纪录
	 */
	MSG_LIST(33, "msg_list", "获取聊天纪录", false),
	/**
	 * 获取推荐应用列表
	 */
	APPS_LIST(34, "apps_list", "获取推荐应用列表", false),
	/**
	 * 邀请通讯录号码安装软件
	 */
	MOBILE_LIST(35, "mobile_list", "邀请通讯录号码安装软件", false),
	/**
	 * 真聊天消息推送
	 */
	CHATPUSH_ADD(36, "chatpush_add", "真聊天消息推送", false),
	/**
	 * 获取微信预支付交易会话标识
	 */
	WEIXINPAY(37, "OnlinePay/Weixinpay/weixinpay_get.php", "获取微信预支付交易会话标识",false),
	/**
	 * 设置接口
	 */
	SHANGCHANG_SET(38, "blog_set", "设置接口", false),
	/**
	 * 设置信息获取接口
	 */
	SHANGCHANG_SET_GET(39, "blog_set_get", "设置信息获取接口", false),
	/**
	 * 商品添加\修改接口
	 */
	GOODS_ADD(40, "blog_save", "商品添加、修改接口", false),
	/**
	 * 获取商品列表
	 */
	GOODS_LIST(41, "product_list", "获取商品列表", false),
	/**
	 * 获取商品详情
	 */
	GOODS_GET(42, "product_get", "获取商品详情", false),
	/**
	 * 商品删除
	 */
	GOODS_REMOVE(43, "remove", "商品删除", false),
	/**
	 * 收藏
	 */
	LOVE_ADD(44, "love_add", "收藏添加接口", false),
	/**
	 * 取消收藏
	 */
	LOVE_REMOVE(45, "love_remove", "取消收藏", false),
	/**
	 * 获取地区（城市）列表信息
	 * */
	DISTRICT_LIST(46, "district_all_get", "获取地区（城市）列表信息", false),
	/**
	 *首页广告列表
	 * */
	INDEX_LIST(47, "ad_list", "广告列表", false),
	HOTCITY_LIST(48, "popular_city_list", "热门城市", false),
	COUNT_GET(49, "count_get", "统计计数", false),
	SCORE_LIST(50, "score_record_list", "积分列表", false),
	/**
	 *获取银行列表
	 * */
	BANK_LIST(51, "bank_list", "获取银行列表", false),
	/**
	 *提现申请
	 * */
	CASH_ADD(52, "cash_add", "提现申请", false),
	/**
	 *保存银行卡、支付宝信息
	 * */
	ALIPAY_SAVE(53, "alipay_save", "保存支付宝信息", false),
	/**
	 *保存银行卡、支付宝信息
	 * */
	BANK_SAVE(54, "bank_save", "保存银行卡信息", false),
	/**
	 *推荐码验证接口
	 * */
	RECOMCODE_VERIFY(55, "recomcode_verify", "推荐码验证接口", false),
	/**
	 *地点路线列表接口
	 * */
	SITE_LIST(56, "site_list", "地点路线列表接口", false),

	/**
	 *保存当前用户坐标接口
	 * */
	POSITION_SAVE(58, "position_save", "保存当前用户坐标接口", false),
	/**
	 *未读消息数接口
	 * */
	UNREAD_GET(59, "unread_get", "未读消息数接口", false),
	/**
	 *修改并保存手机号
	 * */
	USERNAME_SAVE(60, "username_save", "修改并保存手机号", false),
	/**
	 *余额明细列表信息接口
	 * */
	ACCOUNT_LIST(61, "account_record_list", "余额明细列表信息接口", false),
	/**
	 *优惠券列表信息接口
	 * */
	COUPON_LIST(62, "coupon_list", "优惠券列表信息接口", false),
	/**
	 *账号信息接口
	 * */
	ACCOUNT_GET(63, "account_get", "账号信息接口", false),
	/**
	 *保存订单接口
	 * */
	ORDER_SAVE(64, "order_save", "保存订单接口", false),
	/**
	 *余额购买接口
	 * */
	FEEACCOUNT_REMOVE(65, "feeaccount_remove", "余额购买接口", false),
	/**
	 *关注接口
	 * */
	DATA_SAVEOPERATE(66, "data_saveoperate", "关注接口", false),

	/**
	 *地址列表接口
	 * */
	ADDRESS_LIST(75, "address_list", "地址列表接口", false),
	/**
	 *地址添加(修改)接口
	 * */
	ADDRESS_ADD(76, "address_add", "地址添加(修改)接口", false),
	/**
	 *设置默认地址接口
	 * */
	ADDRESS_SAVE(77, "address_save", "设置默认地址接口", false),
	/**
	 *地址删除接口
	 * */
	ADDRESS_REMOVE(78, "address_remove", "地址删除接口", false),
	/**
	 *支付或续费(余额支付)接口
	 * */
	MERCHANT_PAY(85, "merchant_pay", "支付或续费(余额支付)接口", false),

	/**
	 *商家列表接口
	 * */
	MERCHANT_LIST(91, "merchant_list", "商家列表接口", false),
	/**
	 *点赞接口
	 * */
	GOOD_OPERATE(106, "good_operate", "点赞接口", false),
	/**
	 *点赞列表接口
	 * */
	GOOD_LIST(107, "good_list", "点赞列表接口", false),
	CHANNEL_LIST(107, "channel_list", "频道列表", false),
	LIVE_LIST(107, "live_list", "节目列表", false),
	CUSTOM_GET(107, "custom_get", "客户详情", false),
	CUSTOM_REMOVE(107, "custom_remove", "客户删除", false),
	REPAIR_ADD(107, "repair_add", "新建维修", false),
	CAR_LIST(107, "car_list", "接车列表", false),
	NEW_LIST(107, "new_list", "提醒列表", false),

	BLOG_LIST(107, "repair_circle_list", "动态列表", false),
	BROADCAST_LIST(107, "broadcast_list", "公告列表", false),
	SEARCH_LIST(107, "search_get", "搜索", false),
	CITY_LIST(107, "city_all_get", "所有城市", false),
	;


	private int id;// 对应NetTask的id
	private String urlPath;// 请求地址
	private String description;// 请求描述
	private boolean isRootPath;// 是否是根路径

	private BaseHttpInformation(int id, String urlPath, String description,
                                boolean isRootPath) {
		this.id = id;
		this.urlPath = urlPath;
		this.description = description;
		this.isRootPath = isRootPath;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getUrlPath() {
		if (isRootPath)
			return urlPath;

		String path = SYS_ROOT.urlPath + urlPath;

		if (this.equals(INIT))
			return path;

		BaseApplication application = BaseApplication.getInstance();
		SysInitInfo info = application.getSysInitInfo();
		path = info.getSys_web_service() + urlPath;

		if (this.equals(ALIPAY))
			path = info.getSys_plugins() + urlPath;

		if (this.equals(UNIONPAY))
			path = info.getSys_plugins() + urlPath;

		if (this.equals(WEIXINPAY))
			path = info.getSys_plugins() + urlPath;

		return path;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public boolean isRootPath() {
		return isRootPath;
	}

}
