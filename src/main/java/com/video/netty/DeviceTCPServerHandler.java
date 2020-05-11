package com.video.netty;

import com.alibaba.fastjson.JSON;
import com.video.model.Constant;
import com.video.model.Scene;
import com.video.websocket.WebSocketServer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * @Author biwenpan
 * @create 2020/4/28 8:36
 * @description
 */
@Component
@Slf4j
public class DeviceTCPServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 收到客户端消息，自动触发
     * *@param ctx *
     * @param msg *
     * @throws Exception
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] reg = new byte[buf.readableBytes()];
        buf.readBytes(reg);
        String body = new String(reg, "UTF-8");
        Scene scene = Scene.loadFromXML(body);
        System.out.println(scene.toString());
        try{
            WebSocketServer.sendInfo(JSON.toJSONString(scene), Constant.curUserId);
        }catch(IOException e){

        }

        String respMsg = "MSG SEND SUCCESS";
        ByteBuf respByteBuf = Unpooled.copiedBuffer(respMsg.getBytes());
        ctx.write(respByteBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}