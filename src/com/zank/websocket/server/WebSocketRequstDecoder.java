package com.zank.websocket.server;

import java.util.List;

import com.google.gson.JsonObject;
import com.zank.websocket.common.ChatRoomRequest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class WebSocketRequstDecoder extends MessageToMessageDecoder<TextWebSocketFrame>{

	@Override
	protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out) throws Exception {
		String content = msg.text();
		ChatRoomRequest req = new ChatRoomRequest(content);
		out.add(req);
	}
}
