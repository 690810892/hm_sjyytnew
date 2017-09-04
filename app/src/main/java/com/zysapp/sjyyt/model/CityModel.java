package com.zysapp.sjyyt.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class CityModel extends XtomObject implements Serializable {
	private List<DistrictModel> districtList=new ArrayList<>();
	private String id;//	地区id
	private String name;//	地区名称
	private String level;//	第几级
	private String children;//
	public CityModel() {
		super();
	}

	public CityModel(String name, List<DistrictModel> districtList) {
		super();
		this.name = name;
		this.districtList = districtList;
	}
	public CityModel(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				id = get(jsonObject, "id");
				name = get(jsonObject, "name");
				level = get(jsonObject, "level");
				children = get(jsonObject, "children");
				if (!jsonObject.isNull("children")
						&& !isNull(jsonObject.getString("children"))) {
					JSONArray jsonList = jsonObject.getJSONArray("children");
					int size = jsonList.length();
					for (int i = 0; i < size; i++) {
						districtList.add(new DistrictModel(jsonList.getJSONObject(i)));
					}
				}
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

	public List<DistrictModel> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<DistrictModel> districtList) {
		this.districtList = districtList;
	}

	@Override
	public String toString() {
		return "CityModel{" +
				"districtList=" + districtList +
				", id='" + id + '\'' +
				", name='" + name + '\'' +
				", level='" + level + '\'' +
				", children='" + children + '\'' +
				'}';
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
