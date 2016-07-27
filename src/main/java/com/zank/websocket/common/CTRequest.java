package com.zank.websocket.common;

import io.netty.handler.codec.http.FullHttpRequest;

public class CTRequest {
	FullHttpRequest req;
	String body;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public FullHttpRequest getReq() {
		return req;
	}

	public void setReq(FullHttpRequest req) {
		this.req = req;
	}
	
	
}
