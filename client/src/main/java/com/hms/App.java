package com.hms;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            NettyClient nettyClient = new NettyClient(8020);
            ChannelFuture channelFuture = nettyClient.connectLoop();
            Thread.sleep(5000);
            if (channelFuture.isSuccess()) {
                channelFuture.channel().writeAndFlush(Unpooled.wrappedBuffer("Hello\r\n".getBytes()));
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Try Starting Server First !!");
        }
        finally {
        }

    }
}
