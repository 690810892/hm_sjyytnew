package com.zysapp.sjyyt.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;


public class Notice extends XtomObject implements Serializable {
	/**
	 * 
	 */
	private String id;// 通知主键id
	private String keytype;// 关联模块类型 1：系统通知2：帖子回复（帖子指项目核心业务模型）3：简单聊天(纯文本)5：申请好友
	// keytype=1至5 为系统框架保留取值（已经耦合框架），请勿改动其余依次递增扩展...
	private String keyid;// 关联模块主键id 通过keytype+keyid的组合来跳转不同模块详情页，具体见底部“特别提示”。
	private String content;// 通知内容
	private String nickname;// 昵称
	private String avatar;// 头像
	private String client_id;// 通知所属用户主键id
	private String from_id;// 通知来源用户主键id
	private String looktype;// 标记位 1未读 2已读 3可扩展为已同意状态等
	private String regdate;// 通知日期
	private String operatetype;//	是否有操作按钮	1有 0无
	private String clicktype;//	是否可以点开	1可以 0不可以
	private String imgItems;// 
	private ArrayList<Image> imgs = new ArrayList<Image>();// 帖子图片
	
	public Notice(String content){
		this.content=content;
	}
	public Notice(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				id = get(jsonObject, "id");
				operatetype = get(jsonObject, "operatetype");
				clicktype = get(jsonObject, "clicktype");
				keytype = get(jsonObject, "keytype");
				keyid = get(jsonObject, "keyid");
				content = get(jsonObject, "content");
				nickname = get(jsonObject, "nickname");
				avatar = get(jsonObject, "avatar");

				client_id = get(jsonObject, "client_id");
				from_id = get(jsonObject, "from_id");
				looktype = get(jsonObject, "looktype");
				regdate = get(jsonObject, "regdate");
				
				imgItems = get(jsonObject, "imgItems");
				if (!jsonObject.isNull("imgItems")
						&& !isNull(jsonObject.getString("imgItems"))) {
					JSONArray jsonList = jsonObject.getJSONArray("imgItems");
					int size = jsonList.length();
					for (int i = 0; i < size; i++) {
						imgs.add(new Image(jsonList.getJSONObject(i)));
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
		return "Notice{" +
				"id='" + id + '\'' +
				", keytype='" + keytype + '\'' +
				", keyid='" + keyid + '\'' +
				", content='" + content + '\'' +
				", nickname='" + nickname + '\'' +
				", avatar='" + avatar + '\'' +
				", client_id='" + client_id + '\'' +
				", from_id='" + from_id + '\'' +
				", looktype='" + looktype + '\'' +
				", regdate='" + regdate + '\'' +
				", operatetype='" + operatetype + '\'' +
				", clicktype='" + clicktype + '\'' +
				", imgItems='" + imgItems + '\'' +
				", imgs=" + imgs +
				'}';
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the keytype
	 */
	public String getKeytype() {
		return keytype;
	}

	/**
	 * @return the keyid
	 */
	public String getKeyid() {
		return keyid;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * @return the client_id
	 */
	public String getClient_id() {
		return client_id;
	}

	/**
	 * @return the from_id
	 */
	public String getFrom_id() {
		return from_id;
	}

	/**
	 * @return the looktype
	 */
	public String getLooktype() {
		return looktype;
	}

	/**
	 * @return the regdate
	 */
	public String getRegdate() {
		return regdate;
	}

	/**
	 * @param looktype
	 *            the looktype to set
	 */
	public void setLooktype(String looktype) {
		this.looktype = looktype;
	}
	public ArrayList<Image> getImgs() {
		return imgs;
	}

	public String getOperatetype() {
		return operatetype;
	}

	public String getClicktype() {
		return clicktype;
	}

	public String getImgItems() {
		return imgItems;
	}
}
