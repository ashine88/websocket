package com.zank.websocket.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ChatRoomRequest {
	
	private String ak; //access key
	private String sk; //secret key
	private String method;// 
	private JsonObject content;
	
	public ChatRoomRequest() {
	}
	
	public ChatRoomRequest(String content){
		JsonObject d = new Gson().fromJson(content, JsonObject.class);
		this.ak = d.get("ak").getAsString();
		this.sk = d.get("sk").getAsString();
		this.method = d.get("method").getAsString();
		this.content = d.get("content").getAsJsonObject();
	}
	
	public String getAk() {
		return ak;
	}


	public void setAk(String ak) {
		this.ak = ak;
	}


	public String getSk() {
		return sk;
	}


	public void setSk(String sk) {
		this.sk = sk;
	}


	public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}


	public JsonObject getContent() {
		return content;
	}


	public void setContent(JsonObject content) {
		this.content = content;
	}


	@Override
	public String toString() {
		return "Request [ak=" + ak + ", sk=" + sk + ", method=" + method + ", content=" + content + "]";
	}
	
	
	
}
