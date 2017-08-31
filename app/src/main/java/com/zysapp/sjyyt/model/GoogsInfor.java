package com.zysapp.sjyyt.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 *
 */
public class GoogsInfor extends XtomObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;// 	服务主键id	 
	private String name;// 	服务名称	
	private String price;// 	服务价格	
	private String imgurl;// 	服务封面
	private String eidit="0";// 	是否显示checkbox
	private String checkflag="0";// 	是否选中
	
	private String merchant_id;// 	所属工匠id	转入工匠详情使用
	private String loveflag;// 	是否已收藏	0：否、1：是
	private String typename;// 	服务类型	 
	private String refusedates;// 	无服务日期	多条使用逗号连接，如2015-06-30,2015-07-03，不排除任何日期则为“无”
	private String regdate;// 	上次更新时间	用户下单选择时间，从该时间开始向后60天（包含更新当天）内可选择
	private String servicesex;// 	服务对象	男、女、不限
	private String servicemodel;// 	服务模式	上门、到店、不限
	private String content;// 	文字描述	 
	private String address;// 	服务地址	 
	private String lng;// 	服务经度	 
	private String lat;// 	服务纬度	 
	private String district_list;// 	服务区域	不同区用逗号连接
	private String distance;// 	服务范围;// 	不限、N km
	private String imgurlbig;// 	服务封面图（大）	 
	private String readcount;// 	点击量	 
	private String starcount;// 	获得总评星	 
	private String lovecount;// 	收藏数	 
	private String buycount;// 	售出数	 
	private String replycount;// 	评论数	 
	private String avatar;// 	工匠头像	 
	private String m_nickname;// 	工匠名	 
	private String m_level;// 	工匠等级	显示等级图标
	private String m_sex;// 	工匠性别	 
	private String m_typename;// 	工匠服务类型	多条使用逗号连接，如美发、美容
	private String m_mobile	;// 工匠电话	 
	private String m_address;// 	工匠地址	 
	private String m_lng;// 	工匠经度	 
	private String m_lat;// 	工匠纬度

	public GoogsInfor(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				id = get(jsonObject, "id");
				name = get(jsonObject, "name");
				price = get(jsonObject, "price");
				imgurl = get(jsonObject, "imgurl");
				
				merchant_id = get(jsonObject, "merchant_id");
				loveflag = get(jsonObject, "loveflag");
				typename = get(jsonObject, "typename");
				refusedates = get(jsonObject, "refusedates");
				regdate = get(jsonObject, "regdate");
				servicesex = get(jsonObject, "servicesex");
				servicemodel = get(jsonObject, "servicemodel");
				content = get(jsonObject, "content");
				address = get(jsonObject, "address");
				lng = get(jsonObject, "lng");
				lat = get(jsonObject, "lat");
				district_list = get(jsonObject, "district_list");
				distance = get(jsonObject, "distance");
				imgurlbig = get(jsonObject, "imgurlbig");
				readcount = get(jsonObject, "readcount");
				starcount = get(jsonObject, "star");
				lovecount = get(jsonObject, "lovecount");
				buycount = get(jsonObject, "buycount");
				replycount = get(jsonObject, "replycount");
				avatar = get(jsonObject, "avatar");
				m_nickname = get(jsonObject, "m_nickname");
				m_level = get(jsonObject, "m_level");
				m_sex = get(jsonObject, "m_sex");
				m_typename = get(jsonObject, "m_typename");
				m_mobile = get(jsonObject, "m_mobile");
				m_address = get(jsonObject, "m_address");
				m_lng = get(jsonObject, "m_lng");
				m_lat = get(jsonObject, "m_lat");
				

				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	public GoogsInfor(String id, String imgurl, String name, String price, String starcount) {
		this.id = id;
		this.imgurl = imgurl;
		this.name = name;
		this.price = price;
		this.starcount = starcount;
		
	}
	@Override
	public String toString() {
		return "GoogsInfor [id=" + id + ", name=" + name + ", price=" + price
				+ ", imgurl=" + imgurl + ", eidit=" + eidit + ", checkflag="
				+ checkflag + ", merchant_id=" + merchant_id + ", loveflag="
				+ loveflag + ", typename=" + typename + ", refusedates="
				+ refusedates + ", regdate=" + regdate + ", servicesex="
				+ servicesex + ", servicemodel=" + servicemodel + ", content="
				+ content + ", address=" + address + ", lng=" + lng + ", lat="
				+ lat + ", district_list=" + district_list + ", distance="
				+ distance + ", imgurlbig=" + imgurlbig + ", readcount="
				+ readcount + ", starcount=" + starcount + ", lovecount="
				+ lovecount + ", buycount=" + buycount + ", replycount="
				+ replycount + ", avatar=" + avatar + ", m_nickname="
				+ m_nickname + ", m_level=" + m_level + ", m_sex=" + m_sex
				+ ", m_typename=" + m_typename + ", m_mobile=" + m_mobile
				+ ", m_address=" + m_address + ", m_lng=" + m_lng + ", m_lat="
				+ m_lat + "]";
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPrice() {
		return price;
	}

	public String getImgurl() {
		return imgurl;
	}

	public String getEidit() {
		return eidit;
	}

	public String getCheckflag() {
		return checkflag;
	}

	public void setEidit(String eidit) {
		this.eidit = eidit;
	}

	public void setCheckflag(String checkflag) {
		this.checkflag = checkflag;
	}

	public String getMerchant_id() {
		return merchant_id;
	}

	public String getLoveflag() {
		return loveflag;
	}

	public String getTypename() {
		return typename;
	}

	public String getRefusedates() {
		return refusedates;
	}

	public String getRegdate() {
		return regdate;
	}

	public String getServicesex() {
		return servicesex;
	}

	public String getServicemodel() {
		return servicemodel;
	}

	public String getContent() {
		return content;
	}

	public String getAddress() {
		return address;
	}

	public String getLng() {
		return lng;
	}

	public String getLat() {
		return lat;
	}

	public String getDistrict_list() {
		return district_list;
	}

	public String getDistance() {
		return distance;
	}

	public String getImgurlbig() {
		return imgurlbig;
	}

	public String getReadcount() {
		return readcount;
	}

	public String getStarcount() {
		return starcount;
	}

	public String getLovecount() {
		return lovecount;
	}

	public String getBuycount() {
		return buycount;
	}

	public String getReplycount() {
		return replycount;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getM_nickname() {
		return m_nickname;
	}

	public String getM_level() {
		return m_level;
	}

	public String getM_sex() {
		return m_sex;
	}

	public String getM_typename() {
		return m_typename;
	}

	public String getM_mobile() {
		return m_mobile;
	}

	public String getM_address() {
		return m_address;
	}

	public String getM_lng() {
		return m_lng;
	}

	public String getM_lat() {
		return m_lat;
	}
	public void setLoveflag(String loveflag) {
		this.loveflag = loveflag;
	}


}
