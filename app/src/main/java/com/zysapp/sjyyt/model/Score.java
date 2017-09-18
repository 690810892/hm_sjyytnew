package com.zysapp.sjyyt.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 
 */
public class Score extends XtomObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String score;
	private String 	id;//	主键id
	private String client_id;//	用户id
	private String keytype;//	类型	1:获得积分
	private String 	amount;//	积分变动数
	private String 	balance;//	账户剩余积分
	private String 	content;//	备注
	private String regdate;//	时间
	public Score(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				id = get(jsonObject, "id");
				score = get(jsonObject, "score");
				client_id = get(jsonObject, "client_id");
				keytype = get(jsonObject, "keytype");
				amount = get(jsonObject, "amount");
				balance = get(jsonObject, "balance");
				content = get(jsonObject, "content");
				regdate = get(jsonObject, "regdate");
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}

	@Override
	public String toString() {
		return "Score{" +
				"score='" + score + '\'' +
				", id='" + id + '\'' +
				", client_id='" + client_id + '\'' +
				", keytype='" + keytype + '\'' +
				", amount='" + amount + '\'' +
				", balance='" + balance + '\'' +
				", content='" + content + '\'' +
				", regdate='" + regdate + '\'' +
				'}';
	}

	public String getId() {
		return id;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getKeytype() {
		return keytype;
	}

	public void setKeytype(String keytype) {
		this.keytype = keytype;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
}
