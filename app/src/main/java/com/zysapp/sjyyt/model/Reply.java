package com.zysapp.sjyyt.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

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
	private String loveflag;//
	private String 	client_id;//	回复作者主键id
	private String 	likeList;//	点赞列表
	private String 	replyList;//	回复评论列表
	private String keytype;//
	private ArrayList<Reply> replies=new ArrayList<>();
	private ArrayList<Reply> dogoods=new ArrayList<>();
	public Reply(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				id = get(jsonObject, "id");
				nickname = get(jsonObject, "nickname");
				loveflag = get(jsonObject, "loveflag");
				keytype = get(jsonObject, "keytype");
				to_nickname = get(jsonObject, "to_nickname");
				avatar = get(jsonObject, "avatar");
				avatarbig = get(jsonObject, "avatarbig");
				content = get(jsonObject, "content");
				regdate = get(jsonObject, "regdate");
				client_id = get(jsonObject, "client_id");
				likeList = get(jsonObject, "likeList");
				replyList = get(jsonObject, "replyList");
				if (!jsonObject.isNull("likeList")
						&& !isNull(jsonObject.getString("likeList"))) {
					JSONArray jsonList = jsonObject.getJSONArray("likeList");
					int size = jsonList.length();
					for (int i = 0; i < size; i++) {
						dogoods.add(new Reply(jsonList.getJSONObject(i)));
					}
				}
				if (!jsonObject.isNull("replyList")
						&& !isNull(jsonObject.getString("replyList"))) {
					JSONArray jsonList = jsonObject.getJSONArray("replyList");
					int size = jsonList.length();
					for (int i = 0; i < size; i++) {
						replies.add(new Reply(jsonList.getJSONObject(i)));
					}
				}
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
				", loveflag='" + loveflag + '\'' +
				", client_id='" + client_id + '\'' +
				", likeList='" + likeList + '\'' +
				", replyList='" + replyList + '\'' +
				", keytype='" + keytype + '\'' +
				", replies=" + replies +
				", dogoods=" + dogoods +
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

	public ArrayList<Reply> getReplies() {
		return replies;
	}

	public ArrayList<Reply> getDogoods() {
		return dogoods;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getAvatarbig() {
		return avatarbig;
	}

	public String getClient_id() {
		return client_id;
	}

	public String getLikeList() {
		return likeList;
	}

	public String getReplyList() {
		return replyList;
	}

	public String getContent() {
		return content;
	}

	public String getLoveflag() {
		return loveflag;
	}

	public String getRegdate() {
		return regdate;
	}
}
