package com.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf m = (ByteBuf) msg; // (1)
        // try {
        long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
        System.out.println(new Date(currentTimeMillis));
        // ctx.close();
        // } finally {
        // m.release();
        // }

        String req = "客户端请求当前时间";
        byte[] reqs = req.getBytes();
        ByteBuf bf = Unpooled.buffer(reqs.length);
        bf.writeBytes(reqs);
        ctx.writeAndFlush(bf);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String req = "客户端请求当前时间";
        byte[] reqs = req.getBytes();
        ByteBuf bf = Unpooled.buffer(reqs.length);
        bf.writeBytes(reqs);
        ctx.writeAndFlush(bf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
