package com.zank.websocket.chatroom;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoom {
	//房间id
	private long id;
	//标题
	private String title;
	//拥有者
	private long owner;
	//最大观众数量
	private int maxUser;
	
	private Map<Long, User> users = new ConcurrentHashMap<Long, User>();
	
	public ChatRoom() {
	}
	
	public ChatRoom(long id, String title, long owner, int maxUser){
		this.id = id;
		this.title = title;
		this.owner = owner;
		this.maxUser = maxUser;
	}
	
	public void addUser(User user){
		users.put(user.getId(), user);
	}
	
	public Map<Long, User> getUsers() {
		return users;
	}
	
	public void setUsers(Map<Long, User> users) {
		this.users = users;
	}

	public void removeConnection(long cid){
		users.remove(cid);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getOwner() {
		return owner;
	}
	public void setOwner(long owner) {
		this.owner = owner;
	}
	public int getMaxUser() {
		return maxUser;
	}
	public void setMaxUser(int maxUser) {
		this.maxUser = maxUser;
	}
	
	public int usersSize(){
		return users.size();
	}
	
	
}
