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
	private String draw_score;//	抽奖消耗积分
	private String draw_tips;//	抽奖提示
	private String winflag;//	中奖标志	1：是 0：否
	private String draw_goods_id;//	对应奖品商品id
	private String draw_record_id;//	对应中奖记录id
	private String imgurl;//	奖品图片
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
				draw_score = get(jsonObject, "draw_score");
				draw_tips = get(jsonObject, "draw_tips");
				winflag = get(jsonObject, "winflag");
				draw_goods_id = get(jsonObject, "draw_goods_id");
				draw_record_id = get(jsonObject, "draw_record_id");
				imgurl = get(jsonObject, "imgurl");
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
				", draw_score='" + draw_score + '\'' +
				", draw_tips='" + draw_tips + '\'' +
				", winflag='" + winflag + '\'' +
				", draw_goods_id='" + draw_goods_id + '\'' +
				", draw_record_id='" + draw_record_id + '\'' +
				", imgurl='" + imgurl + '\'' +
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

	public String getDraw_score() {
		return draw_score;
	}

	public String getDraw_tips() {
		return draw_tips;
	}

	public String getWinflag() {
		return winflag;
	}

	public String getDraw_goods_id() {
		return draw_goods_id;
	}

	public String getDraw_record_id() {
		return draw_record_id;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
}
