package com.network.tcp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8888));

        Selector sel = Selector.open();

        ssc.register(sel, SelectionKey.OP_ACCEPT);

        while (true) {
            sel.select();
            Iterator<SelectionKey> ite = sel.selectedKeys().iterator();

            while (ite.hasNext()) {
                SelectionKey sk = ite.next();
                ite.remove();

                if (sk.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) sk.channel();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(sel, SelectionKey.OP_READ);
                } else if (sk.isReadable()) {
                    ByteBuffer bb = ByteBuffer.allocate(1024);
                    SocketChannel client = (SocketChannel) sk.channel();
                    int count = client.read(bb);
                    System.out.println("服务器接收：" + new String(bb.array(), 0, count));
                    client.register(sel, SelectionKey.OP_WRITE);
                } else if (sk.isWritable()) {
                    SocketChannel client = (SocketChannel) sk.channel();
                    client.write(ByteBuffer.wrap("服务器写出内容".getBytes()));
                    client.register(sel, SelectionKey.OP_READ);
                }
            }

        }

    }

}
