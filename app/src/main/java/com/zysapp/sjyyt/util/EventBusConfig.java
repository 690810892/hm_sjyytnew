package com.zysapp.sjyyt.util;

/**
 * EventBus的配置类型类
 * Created by CoderHu on 2017-03-10.
 */

public enum EventBusConfig {

    LOGIN_SUCCESS(0, "登录成功"),
    LOGOUT_SUCCESS(1, "退出成功"),
    CLIENT_ID(2, "个推注册成功"),
    NEW_MESSAGE(3, "收到新消息"),
    LOAN_ADD_SUCCESS(4, "添加贷款成功"),
    REFRESH_FIRST_SONG(5, "刷新首页播放内容"),
    REFRESH_MAIN_DATA(6, "刷新首页数据"),
    NOTICE_SAVE(7, "通知操作"),
    REFRESH_FIRST_CHANNEL(8, "刷新首页频道"),
    REFRESH_BLOG_LIST(9, "刷新生意圈"),
    MAIN_SEEK(10, "SEEKBAR"),
    REFRESH_USER(11, "刷新我的详情"),
    REFRESH_TYPELIVE_LIST(12, "刷新资讯列表"),
    REFRESH_CARTYPE(13, "刷新接车时间列表"),
    PAY_WECHAT(14, "微信支付"),
    REFRESH_Custom_LIST(15, "刷新客户列表"),
    REFRESH_CAR_LIST(16, "刷新接车列表"),
    REFRESH_REPLY(17, "刷新评论"),
    REFRESH_CAR_INFOR(18, "刷新接车详情"),
    REFRESH_SONG(19, "刷新歌曲"),
    onPublish(20, "刷新备忘录"),
    PLAY(21, "播放"),
    NEXT(22, "下一曲"),
    PRE(22, "上一曲"),
    REFRESH_RECORDE(22, "刷新纪录正在播放"),
    STATE_PAUSE(22, "暂停"),
    SEEKBAR_VISIBLE(22, "显示进度条"),
    SEEKBAR_INVISIBLE(22, "隐藏进度条"),
    STATE_PLAY(22, "播放"),;


    private int id;
    private String description;

    EventBusConfig(int id, String description) {
        this.id = id;
        this.description = description;
    }
}
