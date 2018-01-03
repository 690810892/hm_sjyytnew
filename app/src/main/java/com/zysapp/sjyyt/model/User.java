package com.zysapp.sjyyt.model;

import com.hemaapp.hm_FrameWork.HemaUser;
import com.hemaapp.hm_FrameWork.orm.annotation.PrimaryKey;
import com.hemaapp.hm_FrameWork.orm.annotation.TableName;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.exception.DataParseException;

/**
 * 用户信息(注意User信息必须继承HemaUser,并且User中不用再包含token字段)
 */
@TableName(table = "User")
public class User extends HemaUser {
    @PrimaryKey(column = "id")
    private String id;// 用户主键
    private String username; //登录名
    private String email; //用户邮箱
    private String nickname; //用户昵称()
    private String mobile; //手机号码
    private String password; //登陆密码
    private String paypassword; //登陆密码
    private String charindex; //用户昵称的汉语拼音首字母索引
    private String sex; //用户性别
    private String telephone; //联系电话
    private String avatar; //个人主页头像图片（小）
    private String avatarbig; //个人主页头像图片（大）
    private String backimg; //个人主页背景图片
    private String onlineflag; //用户在线标记
    private String validflag; //用户状态标记
    private String devicetype; //用户客户端类型
    private String deviceid; //用户客户端硬件标识码
    private String lastlogintime; //最后一次登录的时间
    private String lastloginversion; //lastloginversion
    private String regdate; //用户注册时间
    private String city; //	所在城市
    private String a_username; //	绑定用户账号
    private String a_nickname; //	绑定用户昵称
    private String district_1_id; //
    private String district_2_id; //
    private String out_trade; //
    private String age; //	年龄
    private String birthday; //	出生日期		格式【1990-11-11】
    private String country; //	地区
    private String district_3_id; //区ID
    private String district_name;
    public User() {
    }

    public User(JSONObject jsonObject) throws DataParseException {
        super(jsonObject);
        if (jsonObject != null) {
            try {
                district_name = get(jsonObject, "district_name");
                age = get(jsonObject, "age");
                birthday = get(jsonObject, "birthday");
                country = get(jsonObject, "country");
                district_3_id = get(jsonObject, "district_3_id");
                out_trade = get(jsonObject, "out_trade");
                paypassword = get(jsonObject, "paypassword");
                id = get(jsonObject, "id");
                username = get(jsonObject, "username");
                email = get(jsonObject, "email");
                nickname = get(jsonObject, "nickname");
                mobile = get(jsonObject, "mobile");
                telephone = get(jsonObject, "tel");
                password = get(jsonObject, "password");
                charindex = get(jsonObject, "charindex");
                sex = get(jsonObject, "sex");
                avatar = get(jsonObject, "avatar");
                avatarbig = get(jsonObject, "avatarbig");
                backimg = get(jsonObject, "backimg");
                onlineflag = get(jsonObject, "onlineflag");
                validflag = get(jsonObject, "validflag");
                devicetype = get(jsonObject, "devicetype");
                deviceid = get(jsonObject, "deviceid");
                lastlogintime = get(jsonObject, "lastlogintime");
                lastloginversion = get(jsonObject, "lastloginversion");
                regdate = get(jsonObject, "regdate");
                city = get(jsonObject, "city");
                a_username = get(jsonObject, "a_username");
                a_nickname = get(jsonObject, "a_nickname");
                district_1_id = get(jsonObject, "district_1_id");
                district_2_id = get(jsonObject, "district_2_id");

                log_i(toString());
            } catch (JSONException e) {
                throw new DataParseException(e);
            }
        }
    }

    public User(String token, String nickname, String mobile, String friendflag) {
        super(token);
        this.nickname = nickname;
        this.mobile = mobile;
    }


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", paypassword='" + paypassword + '\'' +
                ", charindex='" + charindex + '\'' +
                ", sex='" + sex + '\'' +
                ", telephone='" + telephone + '\'' +
                ", avatar='" + avatar + '\'' +
                ", avatarbig='" + avatarbig + '\'' +
                ", backimg='" + backimg + '\'' +
                ", onlineflag='" + onlineflag + '\'' +
                ", validflag='" + validflag + '\'' +
                ", devicetype='" + devicetype + '\'' +
                ", deviceid='" + deviceid + '\'' +
                ", lastlogintime='" + lastlogintime + '\'' +
                ", lastloginversion='" + lastloginversion + '\'' +
                ", regdate='" + regdate + '\'' +
                ", token='" +  + '\'' +
                ", city='" + city + '\'' +
                ", a_username='" + a_username + '\'' +
                ", a_nickname='" + a_nickname + '\'' +
                ", district_1_id='" + district_1_id + '\'' +
                ", district_2_id='" + district_2_id + '\'' +
                ", out_trade='" + out_trade + '\'' +
                ", age='" + age + '\'' +
                ", birthday='" + birthday + '\'' +
                ", country='" + country + '\'' +
                ", district_3_id='" + district_3_id + '\'' +
                ", district_name='" + district_name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAge() {
        return age;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getCountry() {
        return country;
    }

    public String getDistrict_3_id() {
        return district_3_id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public String getCharindex() {
        return charindex;
    }

    public String getSex() {
        if (isNull(sex))
            sex = "男";
        return sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getAvatarbig() {
        return avatarbig;
    }

    public String getBackimg() {
        return backimg;
    }

    public String getOnlineflag() {
        return onlineflag;
    }

    public String getValidflag() {
        return validflag;
    }

    public String getDevicetype() {
        return devicetype;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public String getLastlogintime() {
        return lastlogintime;
    }

    public String getLastloginversion() {
        return lastloginversion;
    }

    public String getRegdate() {
        return regdate;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPaypassword() {
        return paypassword;
    }

    public void setPaypassword(String paypassword) {
        this.paypassword = paypassword;
    }

    public void setCharindex(String charindex) {
        this.charindex = charindex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setAvatarbig(String avatarbig) {
        this.avatarbig = avatarbig;
    }

    public void setBackimg(String backimg) {
        this.backimg = backimg;
    }

    public void setOnlineflag(String onlineflag) {
        this.onlineflag = onlineflag;
    }

    public void setValidflag(String validflag) {
        this.validflag = validflag;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public void setLastlogintime(String lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public void setLastloginversion(String lastloginversion) {
        this.lastloginversion = lastloginversion;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getCity() {
        return city;
    }

    public String getA_username() {
        return a_username;
    }

    public String getA_nickname() {
        return a_nickname;
    }

    public String getDistrict_1_id() {
        return district_1_id;
    }

    public String getDistrict_2_id() {
        return district_2_id;
    }

    public String getOut_trade() {
        return out_trade;
    }
}
