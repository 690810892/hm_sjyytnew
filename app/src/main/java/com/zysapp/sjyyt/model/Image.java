package com.zysapp.sjyyt.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 图片
 */
public class Image extends XtomObject implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String id;// 主键id
	private String client_id;// 作者主键id
	private String keytype;// 关联类型 2:帖子 其余待扩展
	private String keyid;// 关联模块类型主键id
	private String imgurlbig;// 大图地址
	private String imgurl;// 缩略图地址
	private String regdate;// 上传时间
	private String content;// 
	private String typename;// 
	private String content_url;
	private String second;
	private String orderby;
	private String name;
	private String jump_type;// 	跳转类型	1:web链接;2:体检套餐;3:图文详情;4:常识【用一个webview打开，地址详解webview接口】;5:小贴士
	private String jump;// 对应内容（ID/链接）
	private String avatar;
	public Image(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				jump_type = get(jsonObject, "jump_type");
				jump = get(jsonObject, "jump");
				avatar = get(jsonObject, "avatar");
				id = get(jsonObject, "id");
				client_id = get(jsonObject, "client_id");
				keytype = get(jsonObject, "keytype");
				keyid = get(jsonObject, "keyid");
				imgurlbig = get(jsonObject, "imgurlbig");
				imgurl = get(jsonObject, "imgurl");
				regdate = get(jsonObject, "regdate");
				content= get(jsonObject, "content");
				typename= get(jsonObject, "typename");
				content_url= get(jsonObject, "imgurl_detail");
				second= get(jsonObject, "second");
				orderby= get(jsonObject, "orderby");
				name= get(jsonObject, "name");

				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	public Image(String imgurl,String keytype){
		this.imgurl=imgurl;
		this.keytype=keytype;
	}
	public Image(String id,String imgurl,String imgurlbig){
		this.imgurl=imgurl;
		this.imgurlbig=imgurlbig;
	}

	@Override
	public String toString() {
		return "Image{" +
				"id='" + id + '\'' +
				", client_id='" + client_id + '\'' +
				", keytype='" + keytype + '\'' +
				", keyid='" + keyid + '\'' +
				", imgurlbig='" + imgurlbig + '\'' +
				", imgurl='" + imgurl + '\'' +
				", regdate='" + regdate + '\'' +
				", content='" + content + '\'' +
				", typename='" + typename + '\'' +
				", content_url='" + content_url + '\'' +
				", second='" + second + '\'' +
				", orderby='" + orderby + '\'' +
				", name='" + name + '\'' +
				", jump_type='" + jump_type + '\'' +
				", jump='" + jump + '\'' +
				", avatar='" + avatar + '\'' +
				'}';
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the client_id
	 */
	public String getClient_id() {
		return client_id;
	}

	/**
	 * @return the keytype
	 */
	public String getKeytype() {
		return keytype;
	}

	public void setKeytype(String keytype) {
		this.keytype = keytype;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public void setImgurlbig(String imgurlbig) {
		this.imgurlbig = imgurlbig;
	}

	/**

	 * @return the keyid
	 */
	public String getKeyid() {
		return keyid;
	}

	/**
	 * @return the imgurlbig
	 */
	public String getImgurlbig() {
		if (isNull(imgurlbig)){
			return "";
		}else
			return imgurlbig;
	}

	/**
	 * @return the imgurl
	 */
	public String getImgurl() {
		if (isNull(imgurl))
			return "";
		else
			return imgurl;
	}

	/**
	 * @return the regdate
	 */
	public String getRegdate() {
		return regdate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getContent() {
		return content;
	}

	public String getTypename() {
		return typename;
	}

	public String getContent_url() {
		return content_url;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public String getName() {
		return name;
	}

	public String getJump_type() {
		return jump_type;
	}

	public String getJump() {
		return jump;
	}

	public String getAvatar() {
		return avatar;
	}
}
