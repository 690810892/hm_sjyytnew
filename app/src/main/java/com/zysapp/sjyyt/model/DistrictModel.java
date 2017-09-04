package com.zysapp.sjyyt.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class DistrictModel extends XtomObject implements Serializable {
	private String zipcode;
	private String id;//	地区id
	private String name;//	地区名称
	private String level;//	第几级
	private String children;//
	public DistrictModel() {
		super();
	}

	public DistrictModel(String name, String zipcode) {
		super();
		this.name = name;
		this.zipcode = zipcode;
	}
	public DistrictModel(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				id = get(jsonObject, "id");
				name = get(jsonObject, "name");
				level = get(jsonObject, "level");
				children = get(jsonObject, "children");
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public String toString() {
		return "DistrictModel [name=" + name + ", zipcode=" + zipcode + "]";
	}

	public String getId() {
		return id;
	}

	public String getLevel() {
		return level;
	}

	public String getChildren() {
		return children;
	}
}
