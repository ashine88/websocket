package com.zank.websocket.common.entity;

import com.google.gson.JsonObject;

public class CTResponseBody {
	private String action;
	private String uri;
	private JsonObject data;
	private long timestamp;
	private int code;
	
	public CTResponseBody() {

	}
	
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public JsonObject getData() {
		return data;
	}


	public void setData(JsonObject data) {
		this.data = data;
	}


	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}
	
	
}
