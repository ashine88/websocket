package com.zank.websocket.chatroom;

import io.netty.channel.Channel;

public class User {
	private long id;
	private Channel channel = null;
	
	public User(long id, Channel channel) {
		this.channel = channel;
		this.id = id;
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
	
	
}
