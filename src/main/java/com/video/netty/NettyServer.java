package com.video.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author biwenpan
 * @create 2020/4/28 8:36
 * @description
 */

@Component
@Order(1)
@Slf4j
public class NettyServer implements CommandLineRunner {
    /**
     * 充电客户端
     */
    public static Map<String, ChannelId> clientMap = new ConcurrentHashMap<>();
    public static final ChannelGroup channel_group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static NettyServer nettyServer;
    @Value("${socket.charge-server.port}")
    private Integer port;
    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap server;
    private ChannelFuture future;

    public ChannelFuture getFuture() {
        return future;
    }

    public NettyServer() {
        mainGroup = new NioEventLoopGroup(1);
        subGroup = new NioEventLoopGroup(200);
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ServerChannelInitializer())

                //设置队列大小
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 50000)
                .childOption(ChannelOption.TCP_NODELAY, true)
                // 两小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
                .childOption(ChannelOption.SO_KEEPALIVE, false);
    }

    @PostConstruct
    public void init() {
        nettyServer = this;
    }

    private void start() {
        try {
            this.future = server.bind(nettyServer.port).sync();
            log.info("netty server 启动完毕,启动端口为：" + nettyServer.port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭主线程组
            mainGroup.shutdownGracefully();
            //关闭工作线程组
            subGroup.shutdownGracefully();
        }
    }

    @Override
    @Async
    public void run(String... args) throws Exception {
        this.start();
    }
}
