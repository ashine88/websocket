package com.zank.websocket.common.util;

public class UriHelper {
	
	public static String[] params(String uri){
		if(null != uri && uri.length() > 0){
			return uri.replaceFirst("/", "").split("/");
		}
		return new String[0];
	}
}
