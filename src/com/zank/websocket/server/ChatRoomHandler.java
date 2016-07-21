package com.zank.websocket.server;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;

import com.google.gson.JsonObject;
import com.zank.websocket.chatroom.ChatRoom;
import com.zank.websocket.chatroom.ChatRoomPool;
import com.zank.websocket.chatroom.User;
import com.zank.websocket.common.ChatRoomRequest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class ChatRoomHandler extends SimpleChannelInboundHandler<ChatRoomRequest>{
	private ChatRoomPool chatRoomPool;
	private static AtomicLong intAtom = new AtomicLong(); 
	private ChannelHandlerContext ctx;
	
	public ChatRoomHandler(ChatRoomPool pool) {
		System.out.println("pool  " + pool.toString());
		this.chatRoomPool = pool;
	}
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, ChatRoomRequest req) throws Exception {
		this.ctx = ctx;
		if(null != req){
			String method = req.getMethod();
			JsonObject content = req.getContent();
			System.out.println(String.format("method【%s】 content【%s】", method, content.toString()));
			if("create".equals(method)){
				createChatRoom(content);
			}else if("enter".equals(method)){
				enter(content);
			}else if("sendMessage".equals(method)){
				sendMessage(content);
			}
		}
	}
	private void createChatRoom(JsonObject data){
		String title = data.get("title").getAsString();
		long owner = data.get("owner").getAsLong();
		int maxUser = data.get("maxUser").getAsInt();
		long id = intAtom.incrementAndGet();
		System.out.println("id is " + id);
		ChatRoom room = new ChatRoom(id, title, owner, maxUser);
		System.out.println(room.toString());
		chatRoomPool.push(room);	
		System.out.println(chatRoomPool.size());
	}
	
	private void enter(JsonObject data){
		System.out.println(ctx.channel().id());
		long roomId = data.get("roomId").getAsLong();
		long userId = data.get("userId").getAsLong();
		ChatRoom room = chatRoomPool.get(roomId);
		if(null != room){
			room.addUser(new User(userId, ctx.channel()));
			System.out.println(room.usersSize());
		}
	}
	
	public void sendMessage(JsonObject data){
		long roomId = data.get("roomId").getAsLong();
		long userId = data.get("userId").getAsLong();
		String msg = data.get("message").getAsString();
		ChatRoom room = chatRoomPool.get(roomId);
		if(null !=  room){
			if(room.getUsers().containsKey(userId)){
				System.out.println(String.format("用户【%d】存在于聊天室【%d】", userId, roomId));
				Iterator<Entry<Long, User>> it = room.getUsers().entrySet().iterator();
				while(it.hasNext()){
					Entry<Long, User> entry = it.next();
					long uid = entry.getKey();
					if(uid != userId){
						User user = entry.getValue();
						TextWebSocketFrame frame = new TextWebSocketFrame(msg);
						user.getChannel().writeAndFlush(frame);
					}
				}
			}
		}
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}
}
