package com.zank.websocket.connection;

import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;

public class ConnectionPool {
	private ConcurrentHashMap<Long, ChannelHandlerContext> ctxs = new ConcurrentHashMap<Long, ChannelHandlerContext>();
	
	public void put(ChannelHandlerContext ctx){
		long id = Long.valueOf(ctx.channel().id().asLongText());
		ctxs.put(id, ctx);
	}
}
