package com.hms;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class NettyServer {
    private int port;

    private Channel channel;

    public ChannelFuture channelFuture;

    /* creating event loop group*/
    private EventLoopGroup bossGroup = new NioEventLoopGroup(); // accepts an incoming connection
    private EventLoopGroup workerGroup = new NioEventLoopGroup();//handles the traffic of the accepted connection

    public NettyServer(int port){
        this.port = port;
    }

    public void connectLoop() {
        try {
            ServerBootstrap b = new ServerBootstrap(); // sets up a server
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) //  instantiate a new Channel to accept incoming connections.
                    .childHandler(new ChannelInitializer<SocketChannel>() { // configure new channel
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ServerHandler());//configure pipeline with new handler
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          //
                    .childOption(ChannelOption.SO_KEEPALIVE, true); //

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync();


            this.channel = f.channel();
            this.channelFuture = f;
            //f.channel().closeFuture().sync();
        }
        catch(Exception e) {
            System.out.println("Exception in server thread");
        }finally {

        }
    }
    public void shutdown(){
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }
}
