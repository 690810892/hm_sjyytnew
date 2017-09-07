package com.zysapp.sjyyt.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 
 */
public class ID extends XtomObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;//	主键id
	private String comment_id;
	private String custom_id;
	private String count;
	private String about_id;
	private String phone;
	private String num;
	public ID(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				id = get(jsonObject, "id");
				custom_id = get(jsonObject, "custom_id");
				comment_id = get(jsonObject, "comment_id");
				about_id = get(jsonObject, "about_id");
				count = get(jsonObject, "count");
				phone = get(jsonObject, "phone");
				num = get(jsonObject, "num");
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}

	@Override
	public String toString() {
		return "ID{" +
				"id='" + id + '\'' +
				", comment_id='" + comment_id + '\'' +
				", custom_id='" + custom_id + '\'' +
				", count='" + count + '\'' +
				", about_id='" + about_id + '\'' +
				", phone='" + phone + '\'' +
				", num='" + num + '\'' +
				'}';
	}

	public String getId() {
		return id;
	}

	public String getComment_id() {
		return comment_id;
	}

	public String getPhone() {
		return phone;
	}

	public String getAbout_id() {
		return about_id;
	}

	public String getCustom_id() {
		return custom_id;
	}

	public String getCount() {
		return count;
	}

	public String getNum() {
		return num;
	}

	public void setCount(String count) {
		this.count = count;
	}
}
