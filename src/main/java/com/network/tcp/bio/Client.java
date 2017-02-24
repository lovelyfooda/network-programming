package com.network.tcp.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080)) {
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            os.write((byte)13);
//            os.write("我只是测试一下而已额\r\n".getBytes());
            os.flush();

            byte[] bufIn = new byte[1024];
            int limit = is.read(bufIn);

            System.out.println("服务器返回：" + new String(bufIn, 0, limit));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
