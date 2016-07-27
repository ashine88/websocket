package com.zank.websocket.server;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zank.websocket.chatroom.ChatRoom;
import com.zank.websocket.chatroom.ChatRoomPool;
import com.zank.websocket.chatroom.User;
import com.zank.websocket.common.CTHttpRequest;
import com.zank.websocket.common.CTRequest;
import com.zank.websocket.common.CTWebScoketRequest;
import com.zank.websocket.common.Code;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class ChatRoomHandler extends SimpleChannelInboundHandler<CTRequest>{
	private ChatRoomPool chatRoomPool;
	private static AtomicLong intAtom = new AtomicLong(); 
	private ChannelHandlerContext ctx;
	private CTRequest ctreq;
	public ChatRoomHandler(ChatRoomPool pool) {
		System.out.println("pool  " + pool.toString());
		this.chatRoomPool = pool;
	}
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, CTRequest req) throws Exception {
		System.out.println(ctx.channel().id());
		this.ctx = ctx;
		this.ctreq = req;
		if(null != req){
			if(req instanceof CTHttpRequest){
				System.out.println("msg from http request!");
				CTHttpRequest httpReq = (CTHttpRequest)req;
				//handleHttpRequest(httpReq);
				
			}else if(req instanceof CTWebScoketRequest ){
				System.out.println("msg from websocket request");
				CTWebScoketRequest websocketReq = (CTWebScoketRequest)req;
				//handleWebsocketRequest(websocketReq);
			}
		}
	}
	
//	private void handleHttpRequest(CTHttpRequest req){
//		HttpMethod method = req.getHttpMethod();
//		if( HttpMethod.POST.equals(method) ){
//			System.out.println("create chat room!");
//			create(req);
//		}else if( HttpMethod.PUT.equals(method)){
//			System.out.println("modify chat room");
//		}else if( HttpMethod.DELETE.equals(method) ){
//			System.out.println("delete chat room");
//			delete(req);
//		}else if( HttpMethod.GET.equals(method)){
//			System.out.println("get chat room");
//			getChatRoom(req);
//		}
//		
// 
//	}
//	
//	private void handleWebsocketRequest(CTWebScoketRequest req){
//		JSONObject json = JSON.parseObject(req.getBody(), JSONObject.class);
//		String method = json.getString("method");
//		JSONObject body = json.getJSONObject("body");
//		if("join".equals(method)){
//			join(body);
//		}else if("sendMessage".equals(method)){
//			sendMessage(body);
//		}else if("exit".equals(method)){
//			exit(body);
//		}
//	}
//	private void create(CTHttpRequest req){
//		JSONObject json = JSON.parseObject(req.getBody(), JSONObject.class);
//		String title = data.get("name").getAsString();
//		long owner = data.get("owner").getAsLong();
//		int maxusers = data.get("maxusers").getAsInt();
//		
//		long id = intAtom.incrementAndGet();
//		System.out.println("id is " + id);
//		ChatRoom room = new ChatRoom(id, title, owner, maxusers);
//		if(data.has("description")){
//			String description = data.get("description").getAsString();
//			room.setDescription(description);
//		}
//		chatRoomPool.push(room);	
//		
//		CTResponseBody body = new CTResponseBody();
//		body.setAction("POST");
//		body.setUri(req.getReq().uri());
//		body.setTimestamp(System.currentTimeMillis());
//		JsonObject json = new JsonObject();
//		json.addProperty("id", id+"");
//		body.setData(json);
//		
//		sendHttpResponse(ctx, HttpResponseStatus.OK, new Gson().toJson(body));
//	}
//	
//	
//	private void delete(CTHttpRequest req){
//		String uri = req.getReq().uri();
//		//get param from uri
//		String[] params = UriHelper.params(uri);
//		int code = 0;
//		if(params.length == 3){
//			long roomId = Long.valueOf(params[2]);
//			if(chatRoomPool.isExists(roomId)){
//				ChatRoom chatRoom = chatRoomPool.getAndRemove(roomId);
//				chatRoom.release();
//				code = Code.SUCCESS;
//			}else{
//				System.out.println(String.format("chat room %d couldn't be found", roomId));
//				code = Code.CHATROOM_NOTEXISTS;
//			}
//		}else{
//			System.out.println("illegal parameter");
//			code = Code.ILLEGAL_PARAMS;
//		}
//		
//		
//		CTResponseBody body = new CTResponseBody();
//		body.setAction("DELETE");
//		body.setUri(req.getReq().uri());
//		body.setTimestamp(System.currentTimeMillis());
//		body.setCode(code);
//		sendHttpResponse(ctx, HttpResponseStatus.OK, new Gson().toJson(body));
//	}
//	
//	
//	private void getChatRoom(CTHttpRequest req){
//		String uri = req.getReq().uri();
//		//get param from uri
//		String[] params = UriHelper.params(uri);
//		int code = 0;
//		CTResponseBody body = new CTResponseBody();
//		ChatRoom cr = null;
//		if(params.length == 3){
//			long roomId = Long.valueOf(params[2]);
//			if(chatRoomPool.isExists(roomId)){
//				cr = chatRoomPool.get(roomId);
//				JsonObject data = new JsonObject();
//				data.addProperty("id", cr.getId());
//				data.addProperty("name", cr.getName());
//				data.addProperty("owner", cr.getOwner());
//				data.addProperty("affiliations_count", cr.usersSize());
//				body.setData(data);
//				code = Code.SUCCESS;
//			}else{
//				System.out.println(String.format("chat room %d couldn't be found", roomId));
//				code = Code.CHATROOM_NOTEXISTS;
//			}
//			
//		}else{
//			System.out.println("illegal parameter");
//			code = Code.ILLEGAL_PARAMS;
//		}
//		
//		body.setAction("GET");
//		body.setUri(req.getReq().uri());
//		body.setTimestamp(System.currentTimeMillis());
//		body.setCode(code);
//		
//		sendHttpResponse(ctx, HttpResponseStatus.OK, new Gson().toJson(body));
//	}
//	
//	private void join(JsonObject data){
//		System.out.println(ctx.channel().id());
//		long roomId = data.get("roomId").getAsLong();
//		long userId = data.get("userId").getAsLong();
//		ChatRoom room = chatRoomPool.get(roomId);
//		if(null != room){
//			room.addUser(new User(userId, ctx.channel()));
//			sendWebsocketResponse(ctx, "join success success");
//		}
//	}
//	
//	private void exit(JsonObject data){
//		System.out.println(ctx.channel().id());
//		long roomId = data.get("roomId").getAsLong();
//		long userId = data.get("userId").getAsLong();
//		ChatRoom room = chatRoomPool.get(roomId);
//		if(null != room){
//			sendWebsocketResponse(ctx, "exit room success");
//			room.removeUser(userId);
//		}
//	}
//	
//	
//	private void sendMessage(JsonObject data){
//		long roomId = data.get("roomId").getAsLong();
//		long from = data.get("from").getAsLong();
//		String msg = data.get("message").getAsString();
//		ChatRoom room = chatRoomPool.get(roomId);
//		if(null !=  room){
//			if(room.isExists(from)){
//				System.out.println(String.format("�û���%d�������������ҡ�%d��", from, roomId));
//				room.sendMessage(new TextWebSocketFrame(msg), from);
//			}
//		}
//	}
//	
//	
//	private void banned(JsonObject data){
//		long roomId = data.get("roomId").getAsLong();
//		long userId = data.get("userId").getAsLong();
//		long from = data.get("from").getAsLong();
//		int minutes = data.get("minutes").getAsInt();
//		ChatRoom room = chatRoomPool.get(roomId);
//		int code = 0;
//		CTResponseBody body = new CTResponseBody();
//		if(null !=  room){
//			if(room.isExists(from) && room.isExists(userId)){
//				room.banned(userId, minutes);
//				code = Code.SUCCESS;
//				JsonObject json = new JsonObject();
//				json.addProperty("isBanned", true);
//				body.setData(json);
//			}
//		}else{
//			code = Code.CHATROOM_NOTEXISTS;
//		}
//		body.setAction("GET");
//		body.setCode(code);
//		sendWebsocketResponse(ctx, new Gson().toJson(body));
//	}
//	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}
	
	
    private static void sendHttpResponse(ChannelHandlerContext ctx, HttpResponseStatus status, String content) {
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status, Unpooled.wrappedBuffer(content.getBytes()));
        response.headers().set(CONTENT_TYPE, "application/json");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
        ctx.write(response).addListener(ChannelFutureListener.CLOSE);
    }
    
    private static void sendWebsocketResponse(ChannelHandlerContext ctx, String content){
    	 // Send the uppercase string back.
        ctx.channel().write(new TextWebSocketFrame(content));
    }
}
