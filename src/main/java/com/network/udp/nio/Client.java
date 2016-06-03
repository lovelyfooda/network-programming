package com.network.udp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class Client {

    public static void main(String[] args) throws IOException {
        DatagramChannel client = DatagramChannel.open();
        client.configureBlocking(false);
        client.connect(new InetSocketAddress("localhost", 13));
        Selector selector = Selector.open();
        client.register(selector, SelectionKey.OP_WRITE);

        while (true) {
            selector.select();

            Iterator<SelectionKey> ite = selector.selectedKeys().iterator();

            while (ite.hasNext()) {
                SelectionKey key = ite.next();
                ite.remove();

                if (key.isReadable()) {
                    ByteBuffer response = ByteBuffer.allocate(1024);
                    client.read(response);
                    System.out.println("服务器返回：" + new String(response.array()));
                    client.register(selector, SelectionKey.OP_WRITE);
                } else if (key.isWritable()) {
                    ByteBuffer req = ByteBuffer.wrap("请求服务器支援".getBytes());
                    client.write(req);
                    client.register(selector, SelectionKey.OP_READ);
                }
            }
        }

    }

}
