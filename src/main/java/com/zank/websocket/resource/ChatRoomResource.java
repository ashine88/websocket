package com.zank.websocket.resource;

import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zank.rest.ApiProtocol;
import com.zank.rest.BaseResource;
import com.zank.rest.response.Result;
import com.zank.websocket.chatroom.ChatRoom;
import com.zank.websocket.chatroom.ChatRoomPool;
import com.zank.websocket.common.Code;

public class ChatRoomResource extends BaseResource {
	
	private static final ChatRoomPool pool = ChatRoomPool.instance();
	
	private static AtomicLong intAtom = new AtomicLong();
	
	public ChatRoomResource(ApiProtocol apiProtocol) {
		super(apiProtocol);
	}
	
	public Result post() {
		JSONObject data = JSON.parseObject(apiProtocol.getPostBody(), JSONObject.class);
		String title = data.getString("name");
		long owner = data.getLongValue("owner");
		int maxusers = data.getIntValue("maxusers");
		long id = intAtom.incrementAndGet();
		System.out.println("id is " + id);
		ChatRoom room = new ChatRoom(id, title, owner, maxusers);
		if(data.containsKey("description")){
			String description = data.getString("description");
			room.setDescription(description);
		}
		pool.push(room);	
		Result result = new Result();
		result.setCode(Code.SUCCESS);
		return result;
	}

    public Result patch() {
        return success(202);
    }

    public Result delete() {
        return success(203);
    }

	
}
