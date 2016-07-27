package com.zank.websocket.server;

import static io.netty.handler.codec.http.HttpHeaderNames.HOST;

import com.zank.rest.Config;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;

public class WebsocketHelper {
	
	private static WebSocketServerHandshaker handshaker;

	
	public static boolean isHandshake(HttpMethod method, String uri){
		return method.compareTo(HttpMethod.GET) == 0 && uri.endsWith("handshake");
	} 
	
	public static void handshake(ChannelHandlerContext ctx, FullHttpRequest req) {
		// Handshake
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(req),null, true);
		handshaker = wsFactory.newHandshaker(req);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
		} else {
			handshaker.handshake(ctx.channel(), req);
		}
	}

	private static String getWebSocketLocation(FullHttpRequest req) {
		String location = req.headers().get(HOST) + Config.getString("handshaker.path");
		if (WebSocketServer.SSL) {
			return "wss://" + location;
		} else {
			return "ws://" + location;
		}
	}
	
	
	public static void close(Channel channel, CloseWebSocketFrame msg){
		handshaker.close(channel, msg);
	}
}	
