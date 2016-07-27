package com.zank.websocket.chatroom;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.zank.websocket.chatroom.User;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class ChatRoom {
	private long id;
	private String name;
	private String description;
	private long owner;
	private int maxusers;
	
	private Map<Long, User> users = new ConcurrentHashMap<Long, User>();
	
	private Map<Long, Long> banneds = new ConcurrentHashMap<Long, Long>();
	
	public ChatRoom() {
	}
	
	public ChatRoom(long id, String name, long owner, int maxUser){
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.maxusers = maxUser;
	}
	public boolean isExists(long userId){
		return users.containsKey(userId);
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

	public void removeUser(long cid){
		User user = users.get(cid);
		users.remove(cid);
		user.closeConnection();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	 
	public long getOwner() {
		return owner;
	}
	public void setOwner(long owner) {
		this.owner = owner;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMaxusers() {
		return maxusers;
	}

	public void setMaxusers(int maxusers) {
		this.maxusers = maxusers;
	}

	public int usersSize(){
		return users.size();
	}
	
	/**
	 * release all connection
	 */
	public void release(){
		if(null != users && users.size() > 0){
			Iterator<Entry<Long, User>> it = users.entrySet().iterator();
			while(it.hasNext()){
				User user = it.next().getValue();
				user.closeConnection();
			}
		}
	}
	
	public void sendMessage(TextWebSocketFrame frame, long from){
		Iterator<Entry<Long, User>> it = users.entrySet().iterator();
		while(it.hasNext()){
			Entry<Long, User> entry = it.next();
			long uid = entry.getKey();
			if(uid != from){
				User user = entry.getValue();
				user.getChannel().writeAndFlush(frame);
			}
		}
	}
	
	/**
	 * banned user 
	 * @param userId
	 * @param time	minute
	 */
	public void banned(long userId, int time){
		Calendar cal = Calendar.getInstance();
		if(time == -1){
			System.out.println(String.format("banned user[%d] forever!", userId));
			cal.set(Calendar.YEAR, 3000);
		}else{
			cal.add(Calendar.MINUTE, time);
		}
		banneds.put(userId, cal.getTimeInMillis());
	}
	/**
	 * �ж��û��Ƿ񱻽���
	 * @param userId
	 * @return
	 */
	public boolean isBanned(long userId){
		long time = System.currentTimeMillis();
		if(banneds.containsKey(userId)){
			long btime = banneds.get(userId);
			if(time - btime <= 0){
				System.out.println(String.format("user[%d] banned time is over", userId));
				banneds.remove(userId);
				return false;
			}
			return true;
		}
		return false;
		
	}
}
