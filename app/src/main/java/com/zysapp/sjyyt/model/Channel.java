package com.zysapp.sjyyt.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 
 */
public class Channel extends XtomObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;//	主键id
	private String name;
	private String imgurl;
	private String dyflag;
	private String lastdate;
	private String regdate;
	private boolean check=false;//
	public Channel(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				id = get(jsonObject, "id");
				name = get(jsonObject, "name");
				imgurl = get(jsonObject, "imgurl");
				dyflag = get(jsonObject, "dyflag");
				lastdate = get(jsonObject, "lastdate");
				regdate = get(jsonObject, "regdate");
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}

	@Override
	public String toString() {
		return "Channel{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", imgurl='" + imgurl + '\'' +
				", dyflag='" + dyflag + '\'' +
				", lastdate='" + lastdate + '\'' +
				", regdate='" + regdate + '\'' +
				'}';
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getImgurl() {
		return imgurl;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public String getDyflag() {
		return dyflag;
	}

	public String getLastdate() {
		return lastdate;
	}

	public String getRegdate() {
		return regdate;
	}
}
