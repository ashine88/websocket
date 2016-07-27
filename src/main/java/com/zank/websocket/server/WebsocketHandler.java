package com.zank.websocket.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zank.websocket.chatroom.ChatRoom;
import com.zank.websocket.chatroom.ChatRoomPool;
import com.zank.websocket.chatroom.User;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class WebsocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

	private static ChatRoomPool pool = ChatRoomPool.instance();

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
		System.out.println("-----------websocket request handler-------------");
		// Check for closing frame
		if (frame instanceof CloseWebSocketFrame) {
			WebsocketHelper.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
			return;
		}

		if (frame instanceof PingWebSocketFrame) {
			ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
			return;
		}

		if (!(frame instanceof TextWebSocketFrame)) {
			throw new UnsupportedOperationException(
					String.format("%s frame types not supported", frame.getClass().getName()));
		}
		String request = ((TextWebSocketFrame) frame).text();
		System.err.printf("%s received %s%n", ctx.channel(), request);
		handleWebsocketRequest(ctx, new TextWebSocketFrame(request));
		
	}

	private void handleWebsocketRequest(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
		JSONObject json = JSON.parseObject(msg.text(), JSONObject.class);
		String method = json.getString("method");
		JSONObject body = json.getJSONObject("body");
		if ("join".equals(method)) {
			join(ctx, body);
		} else if ("sendMessage".equals(method)) {
			sendMessage(ctx, body);
		} else if ("exit".equals(method)) {
			exit(ctx, body);
		}
	}

	private void join(ChannelHandlerContext ctx, JSONObject data) {
		long roomId = data.getLongValue("roomId");
		long userId = data.getLongValue("userId");
		ChatRoom room = pool.get(roomId);
		if (null != room) {
			room.addUser(new User(userId, ctx.channel()));
			sendWebsocketResponse(ctx, "join success success");
		}
	}

	private void exit(ChannelHandlerContext ctx, JSONObject data) {
		System.out.println(ctx.channel().id());
		long roomId = data.getLongValue("roomId");
		long userId = data.getLongValue("userId");
		ChatRoom room = pool.get(roomId);
		if (null != room) {
			sendWebsocketResponse(ctx, "exit room success");
			room.removeUser(userId);
		}
	}

	private void sendMessage(ChannelHandlerContext ctx, JSONObject data) {
		long roomId = data.getLongValue("roomId");
		long from = data.getLongValue("userId");
		String msg = data.getString("message");
		ChatRoom room = pool.get(roomId);
		if (null != room) {
			if (room.isExists(from)) {
				System.out.println(String.format("消息发送from %d to %d", from, roomId));
				room.sendMessage(new TextWebSocketFrame(msg), from);
			}
		}
	}
	private static void sendWebsocketResponse(ChannelHandlerContext ctx, String content) {
		ctx.channel().write(new TextWebSocketFrame(content));
	}
}
