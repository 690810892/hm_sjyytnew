package com.zysapp.sjyyt.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 
 */
public class Draw extends XtomObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;//	主键id
	private String keytype;//	抽奖类型	1：app抽奖 2：节目抽奖
	private String keyid;//	对应id	keytype=1时，keyid=0;keytype=2时，keyid=节目id
	private String name	;//活动名称
	private String score;//	所需积分
	private String startdate;//	开始时间
	private String enddate;//	结束时间
	public Draw(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				id = get(jsonObject, "id");
				keytype = get(jsonObject, "keytype");
				keyid = get(jsonObject, "keyid");
				name = get(jsonObject, "name");
				score = get(jsonObject, "score");
				startdate = get(jsonObject, "startdate");
				enddate = get(jsonObject, "enddate");
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}

	@Override
	public String toString() {
		return "Draw{" +
				"id='" + id + '\'' +
				", keytype='" + keytype + '\'' +
				", keyid='" + keyid + '\'' +
				", name='" + name + '\'' +
				", score='" + score + '\'' +
				", startdate='" + startdate + '\'' +
				", enddate='" + enddate + '\'' +
				'}';
	}

	public String getId() {
		return id;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeytype() {
		return keytype;
	}

	public void setKeytype(String keytype) {
		this.keytype = keytype;
	}

	public String getKeyid() {
		return keyid;
	}

	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
}
