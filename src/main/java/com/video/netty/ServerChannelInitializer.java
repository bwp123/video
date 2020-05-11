package com.video.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**
 * @Author biwenpan
 * @create 2020/4/28 8:36
 * @description
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

      //  socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, 1024 * 1024, 6, 2, 3, 0, true));
      //  socketChannel.pipeline().addLast(new MessageDecoder());
      //  socketChannel.pipeline().addLast(new MessageEncoder());
        //  socketChannel.pipeline().addLast(new NettyServerHandler());
        socketChannel.pipeline().addLast(new DeviceTCPServerHandler());
    }
}