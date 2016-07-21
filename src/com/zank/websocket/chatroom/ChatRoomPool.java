package com.zank.websocket.chatroom;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoomPool {
	private Map<Long, ChatRoom> rooms = new ConcurrentHashMap<Long, ChatRoom>();
	public void push(ChatRoom room){
		rooms.put(room.getId(), room);
	}
	
	public ChatRoom get(long id){
		return rooms.get(id);
	}
	
	public void remove(long id){
		rooms.remove(id);
	}
	
	public int size(){
		return rooms.size();
	}
	
	private boolean isExists(long id){
		return rooms.containsKey(id);
	}
	
	
}
