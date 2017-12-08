package com.zysapp.sjyyt.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 
 */
public class Author extends XtomObject implements Serializable {


	private String id;//	主播id
	private String name;//	作者头像
	private String content;//	作者简介
	private String imgurl;
	public Author(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				id = get(jsonObject, "id");
				name = get(jsonObject, "name");
				content = get(jsonObject, "content");
				imgurl = get(jsonObject, "imgurl");
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}

	@Override
	public String toString() {
		return "Author{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", content='" + content + '\'' +
				'}';
	}

	public String getId() {
		return id;
	}

	public String getImgurl() {
		return imgurl;
	}

	public String getName() {
		return name;
	}

	public String getContent() {
		return content;
	}
}
