package com.zysapp.sjyyt.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 
 */
public class Count extends XtomObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;//	主键id
	private String sharecount;//	分享数
	private String replycount;//	回复数
	public Count(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				id = get(jsonObject, "id");
				sharecount = get(jsonObject, "sharecount");
				replycount = get(jsonObject, "replycount");
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}

	@Override
	public String toString() {
		return "Count{" +
				"id='" + id + '\'' +
				", sharecount='" + sharecount + '\'' +
				", replycount='" + replycount + '\'' +
				'}';
	}

	public String getId() {
		return id;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getSharecount() {
		return sharecount;
	}

	public String getReplycount() {
		return replycount;
	}
}
