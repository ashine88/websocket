package com.zank.websocket.common;

import io.netty.handler.codec.http.HttpMethod;

public class CTHttpRequest extends CTRequest{
	
	private String token;
	
	private HttpMethod httpMethod;
	
	
	public CTHttpRequest() {
	
	}
	
	public CTHttpRequest(HttpMethod httpMethod, String token, String body){
		this.httpMethod = httpMethod;
		this.token = token;
		this.body = body;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
 
	public HttpMethod getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	
	
}
