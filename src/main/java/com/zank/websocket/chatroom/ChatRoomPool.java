package com.zank.websocket.chatroom;

import java.util.concurrent.ConcurrentHashMap;

import com.zank.websocket.chatroom.ChatRoom;

public class ChatRoomPool {
	private ConcurrentHashMap<Long, ChatRoom> rooms = new ConcurrentHashMap<Long, ChatRoom>();
	
	private ChatRoomPool(){}
	
	private static class Holder{
		private static final ChatRoomPool pool = new ChatRoomPool();
	}
	public static ChatRoomPool instance() {
        return Holder.pool;
    }
	public void push(ChatRoom room){
		rooms.put(room.getId(), room);
	}
	
	public ChatRoom get(long id){
		return rooms.get(id);
	}
	
	public ChatRoom getAndRemove(long id){
		ChatRoom chatRoom = rooms.get(id);
		rooms.remove(id);
		return chatRoom;
	}
	
	public void remove(long id){
		rooms.remove(id);
	}
	
	public int size(){
		return rooms.size();
	}
	
	public boolean isExists(long id){
		return rooms.containsKey(id);
	}
	
	
}
