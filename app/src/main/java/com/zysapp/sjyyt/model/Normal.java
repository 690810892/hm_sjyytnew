package com.zysapp.sjyyt.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 *
 */
public class Normal extends XtomObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;//	主键id
	private String name;//	名称
	private String min_price="0";
	private String max_price="0";
	private String regdate;
	private String lastdate;
	private boolean check=false;//
	public Normal(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				id = get(jsonObject, "id");
				name = get(jsonObject, "name");
				regdate= get(jsonObject, "regdate");
				lastdate= get(jsonObject, "lastdate");
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	public Normal(String id, String name){
		this.name=name;
		this.id=id;
	}
	public Normal(String id, String name, boolean check){
		this.name=name;
		this.id=id;
		this.check=check;
	}
	public Normal(String name, String min_price, String max_price, boolean check){
		this.name=name;
		this.check=check;
		this.min_price=min_price;
		this.max_price=max_price;
	}
	public Normal(String name, String min_price, String max_price){
		this.name=name;
		this.min_price=min_price;
		this.max_price=max_price;
	}

	@Override
	public String toString() {
		return "Normal{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", min_price='" + min_price + '\'' +
				", max_price='" + max_price + '\'' +
				", regdate='" + regdate + '\'' +
				", lastdate='" + lastdate + '\'' +
				", check=" + check +
				'}';
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	public String getRegdate() {
		return regdate;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public String getMin_price() {
		return min_price;
	}

	public String getLastdate() {
		return lastdate;
	}

	public String getMax_price() {
		return max_price;
	}
}
