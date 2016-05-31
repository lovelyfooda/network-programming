package com.network.udp.bio;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: Server
 * @Description: TODO
 * @author Administrator
 * @date 2016年5月27日 下午5:35:44
 */
public class Server {

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(13)) {

            while (true) {
                try {
                    byte[] req = new byte[18];
                    DatagramPacket request = new DatagramPacket(req, 18);
                    socket.receive(request);
                    System.out.println("服务器接收：" + new String(req));

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
                    String time = sdf.format(new Date());
                    DatagramPacket response = new DatagramPacket(time.getBytes(), time.getBytes().length, request.getAddress(), request.getPort());
                    socket.send(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
