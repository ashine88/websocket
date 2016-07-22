package com.zank.websocket.server;

import java.util.List;

import com.zank.websocket.common.CTHttpRequest;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.util.CharsetUtil;

public class HttpRequstDecoder extends MessageToMessageDecoder<FullHttpRequest>{
	private static final String AUTH_PREFIX = "Bearer ";
	
	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpRequest msg, List<Object> out) throws Exception {
		HttpMethod httpMethod = msg.method();
		ByteBuf buf = msg.content();
		String authorization = (String) msg.headers().get("Authorization");
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		String body = new String(bytes, CharsetUtil.UTF_8);
		CTHttpRequest req = new CTHttpRequest(httpMethod, authorization.replace(AUTH_PREFIX, ""), body);
		out.add(req);
	}
}
