package com.hms;

/**
 * Hello world!
 *
 */
public class ServerMain
{
    public static void main( String[] args ) throws InterruptedException {
        ServerFlow.OpenServerPort(8020).channel().closeFuture().sync();
    }
}
