package com.zysapp.sjyyt.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 
 */
public class Type extends XtomObject implements Serializable {

	private String hot;//	热门分类
	private String other;//	其他分类
	private String id;//	主键id
	private String name	;//名称
	private String imgurl;//	展示小图
	private ArrayList<Type> hots=new ArrayList<>();
	private ArrayList<Type> others=new ArrayList<>();
	public Type(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				id = get(jsonObject, "id");
				hot = get(jsonObject, "hot");
				other = get(jsonObject, "other");
				name = get(jsonObject, "name");
				imgurl = get(jsonObject, "imgurl");
				if (!jsonObject.isNull("hot")
						&& !isNull(jsonObject.getString("hot"))) {
					JSONArray jsonList = jsonObject.getJSONArray("hot");
					int size = jsonList.length();
					for (int i = 0; i < size; i++) {
						hots.add(new Type(jsonList.getJSONObject(i)));
					}
				}
				if (!jsonObject.isNull("other")
						&& !isNull(jsonObject.getString("other"))) {
					JSONArray jsonList = jsonObject.getJSONArray("other");
					int size = jsonList.length();
					for (int i = 0; i < size; i++) {
						others.add(new Type(jsonList.getJSONObject(i)));
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
		return "Type{" +
				"hot='" + hot + '\'' +
				", other='" + other + '\'' +
				", id='" + id + '\'' +
				", name='" + name + '\'' +
				", imgurl='" + imgurl + '\'' +
				'}';
	}

	public String getId() {
		return id;
	}

	public String getHot() {
		return hot;
	}

	public ArrayList<Type> getHots() {
		return hots;
	}

	public ArrayList<Type> getOthers() {
		return others;
	}

	public String getOther() {
		return other;
	}

	public String getImgurl() {
		return imgurl;
	}

	public String getName() {
		return name;
	}
}
