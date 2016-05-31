package com.network.udp.bio;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    public static void main(String[] args) throws IOException {
        try (DatagramSocket socket = new DatagramSocket(0);) {

            InetAddress address = InetAddress.getByName("10.0.101.70");
            DatagramPacket request = new DatagramPacket("请求数据来了".getBytes(), "请求数据来了".getBytes().length, address, 13);
            socket.send(request);

            byte[] read = new byte[19];
            DatagramPacket response = new DatagramPacket(read, 19);
            socket.receive(response);
            System.out.println("服务器返回为：" + new String(read));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
