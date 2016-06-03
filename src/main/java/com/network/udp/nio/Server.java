package com.network.udp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Server {

    public static void main(String[] args) throws IOException {
        /**
         * udp nio服务器为什么不用Selector，因为选择器注册的必须是read和write等方法，而在UDP中
         * 只有通过connect方法指定远程主机和指定远程端口，然后才能够调用read和write方法，而udp服务器并不知道
         * 哪个客户端地址会发数据包过来，所以无法建立connect
         */
        DatagramChannel server = DatagramChannel.open();
        server.bind(new InetSocketAddress(13));

        while (true) {
            ByteBuffer request = ByteBuffer.allocate(1024);
            SocketAddress address = server.receive(request);
            request.flip();
            System.out.println("客户端输入：" + new String(request.slice().array()));

            ByteBuffer response = ByteBuffer.wrap("支援马上就到".getBytes());
            server.send(response, address);
        }
    }
}
