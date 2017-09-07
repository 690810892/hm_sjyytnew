package com.zysapp.sjyyt.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 
 */
public class Reply extends XtomObject implements Serializable {

	private String id;//	评论主键ID
	private String 	nickname;//	回复用户昵称
	private String 	to_nickname;//	被回复用户昵称
	private String avatar;//	回复用户头像
	private String avatarbig;//	回复用户头像大图
	private String content;//	回复内容
	private String regdate;//	回复时间
	private String keytype;//
	public Reply(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				id = get(jsonObject, "id");
				nickname = get(jsonObject, "nickname");
				keytype = get(jsonObject, "keytype");
				to_nickname = get(jsonObject, "to_nickname");
				avatar = get(jsonObject, "avatar");
				avatarbig = get(jsonObject, "avatarbig");
				content = get(jsonObject, "content");
				regdate = get(jsonObject, "regdate");
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}

	@Override
	public String toString() {
		return "Reply{" +
				"id='" + id + '\'' +
				", nickname='" + nickname + '\'' +
				", to_nickname='" + to_nickname + '\'' +
				", avatar='" + avatar + '\'' +
				", avatarbig='" + avatarbig + '\'' +
				", content='" + content + '\'' +
				", regdate='" + regdate + '\'' +
				'}';
	}

	public String getId() {
		return id;
	}

	public String getNickname() {
		return nickname;
	}

	public String getTo_nickname() {
		return to_nickname;
	}

	public String getKeytype() {
		return keytype;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getAvatarbig() {
		return avatarbig;
	}

	public String getContent() {
		return content;
	}

	public String getRegdate() {
		return regdate;
	}
}
