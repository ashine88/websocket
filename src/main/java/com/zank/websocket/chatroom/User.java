package com.zank.websocket.chatroom;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

public class User {
	private long id;
	private Channel channel = null;
	
	public User(long id, Channel channel) {
		this.channel = channel;
		this.id = id;
		//add close linstener
		this.channel.closeFuture().addListener(ChannelFutureListener.CLOSE);
	}
	
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	/**
	 * �ر�����
	 */
	public void closeConnection(){
		System.out.println(String.format("connection[%d] is close ", id));
		channel.writeAndFlush("connection is close");
		channel.close();
	}
	
}
