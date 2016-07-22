package com.zank.websocket.server;

import java.util.List;

import com.zank.websocket.common.CTWebScoketRequest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class WebSocketRequstDecoder extends MessageToMessageDecoder<TextWebSocketFrame>{

	@Override
	protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out) throws Exception {
		System.out.println(String.format("msg from websocket : ", msg.text()));
		CTWebScoketRequest req = new CTWebScoketRequest( msg.text());
		out.add(req);
	}
}
