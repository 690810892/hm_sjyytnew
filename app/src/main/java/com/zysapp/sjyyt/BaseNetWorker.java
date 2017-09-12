package com.zysapp.sjyyt;

import android.content.Context;

import com.hemaapp.hm_FrameWork.HemaNetWorker;
import com.hemaapp.hm_FrameWork.HemaUtil;
import com.hemaapp.hm_FrameWork.task.CurrentTask;
import com.hemaapp.hm_FrameWork.task.ExecuteNetTask;
import com.zysapp.sjyyt.model.Channel;
import com.zysapp.sjyyt.model.DistrictInfor;
import com.zysapp.sjyyt.model.FileUploadResult;
import com.zysapp.sjyyt.model.ID;
import com.zysapp.sjyyt.model.Image;
import com.zysapp.sjyyt.model.JsonBean;
import com.zysapp.sjyyt.model.Normal;
import com.zysapp.sjyyt.model.Notice;
import com.zysapp.sjyyt.model.PCD;
import com.zysapp.sjyyt.model.Reply;
import com.zysapp.sjyyt.model.Song;
import com.zysapp.sjyyt.model.SysInitInfo;
import com.zysapp.sjyyt.model.Token;
import com.zysapp.sjyyt.model.User;

import java.util.HashMap;

import xtom.frame.util.XtomDeviceUuidFactory;
import xtom.frame.util.XtomSharedPreferencesUtil;

/**
 * 网络请求工具类
 */
public class BaseNetWorker extends HemaNetWorker {
    /**
     * 实例化网络请求工具类
     *
     * @param mContext
     */
    private Context mContext;

    public BaseNetWorker(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    @Override
    public void clientLogin() {
        BaseHttpInformation information = BaseHttpInformation.CLIENT_LOGIN;
        HashMap<String, String> params = new HashMap<String, String>();
        String username = XtomSharedPreferencesUtil.get(mContext, "username");
        params.put("username", username);// 用户登录名 手机号或邮箱
        String password = XtomSharedPreferencesUtil.get(mContext, "password");
        params.put("password", password); // 登陆密码 服务器端存储的是32位的MD5加密串
        params.put("devicetype", "2"); // 用户登录所用手机类型 1：苹果 2：安卓（方便服务器运维统计）
        String version = HemaUtil.getAppVersionForSever(mContext);
        params.put("lastloginversion", version);// 登陆所用的系统版本号,记录用户的登录版本，方便服务器运维统计
        params.put("clienttype", "1");
        ExecuteNetTask<User> task = new ExecuteNetTask<>(information, params, User.class);
        executeTask(task);
    }

    @Override
    public boolean thirdSave() {
        if (HemaUtil.isThirdSave(mContext)) {
            BaseHttpInformation information = BaseHttpInformation.THIRD_SAVE;
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("devicetype", "2"); // 用户登录所用手机类型 1：苹果 2：安卓（方便服务器运维统计）
            String version = HemaUtil.getAppVersionForSever(mContext);
            params.put("lastloginversion", version);// 登陆所用的系统版本号,记录用户的登录版本，方便服务器运维统计
            String thirdtype = XtomSharedPreferencesUtil.get(mContext,
                    "thirdtype");
            params.put("thirdtype", thirdtype);// 平台类型 1：微信 2：QQ 3：微博
            String thirduid = XtomSharedPreferencesUtil.get(mContext,
                    "thirduid");
            params.put("thirduid", thirduid);// 平台用户id 该平台唯一的id
            String avatar = XtomSharedPreferencesUtil.get(mContext, "avatar");
            params.put("avatar", avatar);// 平台用户头像 图片地址
            String nickname = XtomSharedPreferencesUtil.get(mContext,
                    "nickname");
            params.put("nickname", nickname);// 平台用户昵称
            String sex = XtomSharedPreferencesUtil.get(mContext, "sex");
            params.put("sex", sex);// 姓名 "男"或"女"
            String age = XtomSharedPreferencesUtil.get(mContext, "age");
            params.put("age", age);// 年龄

            ExecuteNetTask<User> task = new ExecuteNetTask<>(information, params, User.class);
            executeTask(task);

            return true;
        } else {
            return false;
        }
    }

    /**
     * 系统初始化
     */
    public void init() {
        BaseHttpInformation information = BaseHttpInformation.INIT;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("lastloginversion", HemaUtil.getAppVersionForSever(mContext));// 版本号码(默认：1.0.0)
        params.put("device_sn", XtomDeviceUuidFactory.get(mContext));// 客户端硬件串号
        params.put("devicetype", "2"); //
        ExecuteNetTask<SysInitInfo> task = new ExecuteNetTask<>(information, params, SysInitInfo.class);
        executeTask(task);
    }

    /**
     * 登录
     *
     * @param
     */
    public void clientLogin(String username, String password) {
        BaseHttpInformation information = BaseHttpInformation.CLIENT_LOGIN;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);// 用户登录名 手机号或邮箱
        params.put("password", password); // 登陆密码 服务器端存储的是32位的MD5加密串
        params.put("devicetype", "2"); // 用户登录所用手机类型 1：苹果 2：安卓（方便服务器运维统计）
        String version = HemaUtil.getAppVersionForSever(mContext);
        params.put("lastloginversion", version);// 登陆所用的系统版本号
        // 记录用户的登录版本，方便服务器运维统计
        params.put("clienttype", "1");
        ExecuteNetTask<User> task = new ExecuteNetTask<>(information, params, User.class);
        executeTask(task);
    }

    /**
     * 第三方登录
     */
    public void thirdSave(String thirdtype, String thirduid, String avatar,
                          String nickname, String sex, String age) {
        BaseHttpInformation information = BaseHttpInformation.THIRD_SAVE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("devicetype", "2"); // 用户登录所用手机类型 1：苹果 2：安卓（方便服务器运维统计）
        String version = HemaUtil.getAppVersionForSever(mContext);
        params.put("lastloginversion", version);// 登陆所用的系统版本号,记录用户的登录版本，方便服务器运维统计
        params.put("thirdtype", thirdtype);// 平台类型 1：微信 2：QQ 3：微博
        params.put("thirduid", thirduid);// 平台用户id 该平台唯一的id
        params.put("avatar", avatar);// 平台用户头像 图片地址
        params.put("nickname", nickname);// 平台用户昵称
        params.put("sex", sex);// 姓名 "男"或"女"
        params.put("age", age);// 年龄

        ExecuteNetTask<User> task = new ExecuteNetTask<>(information, params, User.class);
        executeTask(task);
    }


    /**
     * 验证用户名是否合法
     */
    public void clientVerify(String username) {
        BaseHttpInformation information = BaseHttpInformation.CLIENT_VERIFY;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);// 用户登录名 手机号或邮箱

        CurrentTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     * 验证用户名是否合法
     */
    public void recomcodeVerify(String recomcode) {
        BaseHttpInformation information = BaseHttpInformation.RECOMCODE_VERIFY;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("recomcode", recomcode);// 推荐码

        CurrentTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     * 申请随机验证码
     */
    public void codeGet(String username) {
        BaseHttpInformation information = BaseHttpInformation.CODE_GET;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);// 用户登录名 手机号或邮箱

        CurrentTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     * 验证随机码
     */
    public void codeVerify(String username, String code) {
        BaseHttpInformation information = BaseHttpInformation.CODE_VERIFY;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);// 用户登录名 手机号或邮箱
        params.put("code", code);// 6位随机号码 测试阶段固定向服务器提交“123456”

        ExecuteNetTask<Token> task = new ExecuteNetTask<>(information, params, Token.class);
        executeTask(task);
    }

    /**
     * 用户注册
     */
    public void clientAdd(String temp_token, String username, String password, String nickname,String sex, String district_name,String avatar) {
        BaseHttpInformation information = BaseHttpInformation.CLIENT_ADD;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("temp_token", temp_token);// 临时令牌 可以有效防止机器人恶意注册（该值从验证随机码接口获取）
        params.put("username", username);// 用户注册名 本项目只允许邮箱注册 (客户端判断所填文本是否符合邮件格式)
        params.put("password", password);// 登陆密码
        params.put("nickname", nickname);// 用户昵称
        params.put("sex", sex);// 用户性别
        params.put("district_name", district_name);//
        params.put("avatar", avatar);//
        ExecuteNetTask<Token> task = new ExecuteNetTask<>(information, params, Token.class);
        executeTask(task);
    }

    /**
     * 上传文件（图片，音频，视频）
     */
    public void fileUpload(String token, String keytype, String keyid,
                           String temp_file) {
        BaseHttpInformation information = BaseHttpInformation.FILE_UPLOAD;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);//
        params.put("keytype", keytype); //
        params.put("keyid", keyid); //
        params.put("duration", "0"); //
        params.put("orderby", "1"); //
        params.put("content", "无");// 内容描述 有的项目中，展示性图片需要附属一段文字说明信息。默认传"无"
        HashMap<String, String> files = new HashMap<String, String>();
        files.put("temp_file", temp_file); //FileUploadResult

        ExecuteNetTask<FileUploadResult> task = new ExecuteNetTask<>(information, params,files, FileUploadResult.class);
        executeTask(task);
    }

    /**
     * 重设密码
     */
    public void passwordReset(String temp_token, String keytype,
                              String new_password) {
        BaseHttpInformation information = BaseHttpInformation.PASSWORD_RESET;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("temp_token", temp_token);// 临时令牌
        params.put("new_password", new_password);// 新密码
        params.put("keytype", keytype);// 密码类型 1：登陆密码 2：支付密码

        CurrentTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     * 修改并保存密码接口
     */
    public void passwordSave(String token, String keytype, String old_password,
                             String new_password) {
        BaseHttpInformation information = BaseHttpInformation.PASSWORD_SAVE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);// 临时令牌
        params.put("keytype", keytype);// 密码类型 1：登陆密码 2：支付密码
        params.put("old_password", old_password);// 旧密码
        params.put("new_password", new_password);// 新密码

        CurrentTask task = new CurrentTask(information, params);
        executeTask(task);
    }


    /**
     * 退出登录
     */
    public void clientLoginout(String token) {
        BaseHttpInformation information = BaseHttpInformation.CLIENT_LOGINOUT;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);// 登陆令牌

        CurrentTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     * 硬件注册保存接口
     */
    public void deviceSave(String token, String deviceid, String type,
                           String channelid) {
        BaseHttpInformation information = BaseHttpInformation.DEVICE_SAVE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);// 登陆令牌
        params.put("deviceid", deviceid);// 登陆手机硬件码 对应百度推送userid
        params.put("devicetype", "2");// 登陆手机类型 1:苹果 2:安卓
        params.put("channelid", channelid);// 百度推送渠道id 方便直接从百度后台进行推送测试

        CurrentTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     * 获取用户个人资料接口
     */
    public void clientGet(String token, String id) {
        BaseHttpInformation information = BaseHttpInformation.CLIENT_GET;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);// 登陆令牌
        params.put("id", id);// 用户Id

        ExecuteNetTask<User> task = new ExecuteNetTask<>(information, params, User.class);
        executeTask(task);
    }

    /**
     * 保存用户资料接口
     */
    public void clientSave(String token, String nickname,String sex, String avatar,String district_name) {
        BaseHttpInformation information = BaseHttpInformation.CLIENT_SAVE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);// 登陆令牌
        params.put("nickname", nickname);// 用户昵称
        params.put("avatar", avatar);
        params.put("sex", sex);// 用户性别
        params.put("district_name", district_name);//

        CurrentTask task = new CurrentTask(information, params);
        executeTask(task);

    }

    /**
     * 首页广告
     */
    public void indexList(String adtype) {
        BaseHttpInformation information = BaseHttpInformation.INDEX_LIST;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("keytype", adtype);//
        ExecuteNetTask<Image> task = new ExecuteNetTask<>(information, params, Image.class);
        executeTask(task);
    }

    public void hotCity() {
        BaseHttpInformation information = BaseHttpInformation.HOTCITY_LIST;
        HashMap<String, String> params = new HashMap<String, String>();
        ExecuteNetTask<DistrictInfor> task = new ExecuteNetTask<>(information, params, DistrictInfor.class);
        executeTask(task);
    }

    /**
     * 提现申请
     */
    public void cashAdd(String token, String keytype, String password, String fee) {
        BaseHttpInformation information = BaseHttpInformation.CASH_ADD;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("keytype", keytype);//1-支付宝提现 2-银行卡提现
        params.put("paypassword", password);
        params.put("applyfee", fee);

        CurrentTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     * 保存银行卡、支付宝信息
     */
    public void bankSave(String token, String bankname, String bankuser, String bankcard, String bankaddr
    ) {
        BaseHttpInformation information = BaseHttpInformation.BANK_SAVE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("bankname", bankname);//keytype=2时传0
        params.put("bankuser", bankuser);//keytype=2时传0
        params.put("bankcard", bankcard);//keytype=2时传0
        params.put("bankaddress", bankaddr);//keytype=2时传0

        CurrentTask task = new CurrentTask(information, params);
        executeTask(task);
    }

    /**
     * 保存银行卡、支付宝信息
     */
    public void alipaySave(String token, String alipay
    ) {
        BaseHttpInformation information = BaseHttpInformation.ALIPAY_SAVE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("alipay_no", alipay);//

        CurrentTask task = new CurrentTask(information, params);
        executeTask(task);
    }



    /**
     * 获取图片列表（或相册列表）信息
     */
    public void imgList(String keytype, String keyid) {
        BaseHttpInformation information = BaseHttpInformation.IMG_LIST;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("keytype", keytype);
        params.put("keyid", keyid);
        ExecuteNetTask<Image> task = new ExecuteNetTask<>(information, params, Image.class);
        executeTask(task);
    }

    /**
     * 意见反馈
     */
    public void adviceAdd(String token, String content, String loginversion, String mobile_brand, String system_type) {
        BaseHttpInformation information = BaseHttpInformation.ADVICE_ADD;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);// 登陆令牌
        params.put("content", content);// 意见内容
        params.put("device", "Android Phone");//
        params.put("version", loginversion);
        params.put("brand", mobile_brand);
        params.put("system", system_type);
        CurrentTask task = new CurrentTask(information, params);
        executeTask(task);
    }
    public void cityList() {
        BaseHttpInformation information = BaseHttpInformation.CITY_LIST;
        HashMap<String, String> params = new HashMap<String, String>();
        ExecuteNetTask<DistrictInfor> task = new ExecuteNetTask<>(information, params, DistrictInfor.class);
        executeTask(task);
    }
    public void districtList() {
        BaseHttpInformation information = BaseHttpInformation.DISTRICT_LIST;
        HashMap<String, String> params = new HashMap<String, String>();
        ExecuteNetTask<PCD> task = new ExecuteNetTask<>(information, params, PCD.class);
        executeTask(task);
    }
    public void unreadGet(String token) {
        BaseHttpInformation information = BaseHttpInformation.UNREAD_GET;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);// 登陆令牌
        ExecuteNetTask<ID> task = new ExecuteNetTask<>(information, params, ID.class);
        executeTask(task);
    }
    /**
     * 获取用户通知列表接口
     */
    public void noticeList(String token,  int page) {
        BaseHttpInformation information = BaseHttpInformation.NOTICE_LIST;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);// 登陆令牌
        params.put("noticetype", "0");
        params.put("page", String.valueOf(page));
        ExecuteNetTask<Notice> task = new ExecuteNetTask<>(information, params, Notice.class);
        executeTask(task);
    }
    public void noticeOperate(String token, String id,String keytype,String keyid,String operatetype) {
        BaseHttpInformation information = BaseHttpInformation.NOTICE_SAVEOPERATE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);// 登陆令牌
        params.put("id", id);
        params.put("keytype", keytype);
        params.put("keyid", keyid);
        params.put("operatetype", operatetype);
        ExecuteNetTask<Notice> task = new ExecuteNetTask<>(information, params, Notice.class);
        executeTask(task);
    }
    public void channelList(int page) {
        BaseHttpInformation information = BaseHttpInformation.CHANNEL_LIST;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("page", String.valueOf(page));
        ExecuteNetTask<Channel> task = new ExecuteNetTask<>(information, params, Channel.class);
        executeTask(task);
    }
    public void liveList(String keytype,String keyid,int page) {
        BaseHttpInformation information = BaseHttpInformation.LIVE_LIST;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("keytype", keytype);
        params.put("keyid", keyid);
        params.put("page", String.valueOf(page));
        ExecuteNetTask<Song> task = new ExecuteNetTask<>(information, params, Song.class);
        executeTask(task);
    }
    public void replyAdd(String token,String keytype,String keyid, String comment_id,String content) {
        BaseHttpInformation information = BaseHttpInformation.REPLY_ADD;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", token);// 登陆令牌
        params.put("comment_id", comment_id);
        params.put("keytype", keytype);
        params.put("keyid", keyid);
        params.put("content", content);
        ExecuteNetTask<Reply> task = new ExecuteNetTask<>(information, params, Reply.class);
        executeTask(task);
    }
}