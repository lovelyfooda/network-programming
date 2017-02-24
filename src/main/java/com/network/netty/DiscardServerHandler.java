package com.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    Object obj = null;

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(obj == msg);
        obj = msg;

        System.out.println(msg.toString());
        ByteBuf bb = (ByteBuf) msg;
        byte[] bytes = new byte[bb.readableBytes()];
        bb.readBytes(bytes);
        System.out.println(new String(bytes));

        final ByteBuf time = ctx.alloc().buffer(4); // (2)
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
    }

    // @Override
    // public void channelActive(final ChannelHandlerContext ctx) throws
    // Exception {
    // final ByteBuf time = ctx.alloc().buffer(4); // (2)
    // time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
    //
    // final ChannelFuture f = ctx.writeAndFlush(time); // (3)
    // f.addListener(new ChannelFutureListener() {
    // @Override
    // public void operationComplete(ChannelFuture future) {
    // System.out.println("进入到这里了");
    // assert f == future;
    // ctx.close();
    // }
    // }); // (4)
    // }

    int index = 1;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
