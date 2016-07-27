package com.zank.websocket.server;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderNames.TRANSFER_ENCODING;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zank.rest.ApiHandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.AsciiString;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpHeaderValues;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	private static final Logger logger = LoggerFactory.getLogger(HttpRequestHandler.class);
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
		String ip = ip(ctx);
		if(WebsocketHelper.isHandshake(msg.method(), msg.uri())){
			logger.info(String.format("handshake from ip %s timestamp %d", ip, System.currentTimeMillis()));
			WebsocketHelper.handshake(ctx, msg);
			return ;
		}
		
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(ApiHandler.transfer(ctx, msg)));
        
        response.headers().set(CONTENT_TYPE,new AsciiString("application/json; charset=utf-8"));
        response.headers().set(TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
        boolean keepAlive = HttpHeaderUtil.isKeepAlive(msg);
        if (!keepAlive) {
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            ctx.write(response);
        }
	}
	
	private String ip(ChannelHandlerContext  ctx){
		InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
	    InetAddress inetaddress = socketAddress.getAddress();
	    String ipAddress = inetaddress.getHostAddress(); // IP address of client
	    return ipAddress;
	}
}
