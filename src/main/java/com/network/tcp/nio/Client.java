package com.network.tcp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Client {

    public static void main(String[] args) throws IOException {
        // SocketChannel类只有读写ByteBuffer的方法，它无法读写任何其它类型的缓冲区
        SocketChannel client = SocketChannel.open();
        client.configureBlocking(false);

        Selector sel = Selector.open();
        // 对于一个channel对象来说，只能够注册一个事件，比如说socketchannel我同时注册读还有写事件，
        // 那么写事件会覆盖读事件，应该是根据socket的特性来设计的，先写出再读取，不可能同时写和同时读
        client.register(sel, SelectionKey.OP_CONNECT);

        // 必须要在注册后面，因为connect时间的触发是客户端自己
        client.connect(new InetSocketAddress(8888));

        while (true) {
            sel.select();// 阻塞直到有事件被返回
            Iterator<SelectionKey> ite = sel.selectedKeys().iterator();

            while (ite.hasNext()) {
                SelectionKey sk = ite.next();
                ite.remove();// 移除事件防止事件被重复处理

                SocketChannel sc = (SocketChannel) sk.channel();
                if (sk.isConnectable()) {
                    if (sc.isConnectionPending()) {
                        sc.finishConnect();
                        sc.register(sel, SelectionKey.OP_WRITE);
                    }
                } else if (sk.isReadable()) {
                    ByteBuffer bb = ByteBuffer.allocate(1024);
                    int count = sc.read(bb);
                    System.out.println("客户端接收：" + new String(bb.array(), 0, count));
                    sc.register(sel, SelectionKey.OP_WRITE);
                } else if (sk.isWritable()) {
                    sc.write(ByteBuffer.wrap("客户端写出内容".getBytes()));
                    sc.register(sel, SelectionKey.OP_READ);
                }
            }
        }
    }

}
