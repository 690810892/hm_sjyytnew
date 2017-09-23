package com.zysapp.sjyyt.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 *
 */
public class Clock extends XtomObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;//	主键id
	private String client_id;//	用户id
	private String keytype	;//类型	1：单次提醒 2：每日提醒
	private String status;//	状态	0：关闭 1：开启
	private String content	;//提醒内容
	private String calldate	;//提醒时间	格式：2017-9-10 10:41:01
	private String 	regdate;//	时间
	public Clock(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				id = get(jsonObject, "id");
				client_id = get(jsonObject, "client_id");
				regdate= get(jsonObject, "regdate");
				keytype= get(jsonObject, "keytype");
				status= get(jsonObject, "status");
				content= get(jsonObject, "content");
				calldate= get(jsonObject, "calldate");
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}

	public Clock(String id, String content, String calldate) {
		this.id = id;
		this.content = content;
		this.calldate = calldate;
	}

	@Override
	public String toString() {
		return "Clock{" +
				"id='" + id + '\'' +
				", client_id='" + client_id + '\'' +
				", keytype='" + keytype + '\'' +
				", status='" + status + '\'' +
				", content='" + content + '\'' +
				", calldate='" + calldate + '\'' +
				", regdate='" + regdate + '\'' +
				'}';
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getKeytype() {
		return keytype;
	}

	public void setKeytype(String keytype) {
		this.keytype = keytype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCalldate() {
		return calldate;
	}

	public void setCalldate(String calldate) {
		this.calldate = calldate;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
}
