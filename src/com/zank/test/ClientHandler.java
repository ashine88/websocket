package com.zank.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.example.discard.DiscardClient;

public class ClientHandler extends SimpleChannelInboundHandler<Object> {

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		 ByteBuf content = ctx.alloc().directBuffer(10).writeZero(10);
		 ctx.writeAndFlush(content);
		 
	}
}
