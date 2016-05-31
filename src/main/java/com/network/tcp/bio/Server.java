package com.network.tcp.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket server = null;
        try {
            server = new ServerSocket(8080);

            while (true) {
                Socket socket = server.accept();

                InputStream is = socket.getInputStream();
                @SuppressWarnings("resource")
                MyInputStream input = new MyInputStream(is);
                OutputStream os = socket.getOutputStream();

                // 直接使用读取
                // byte[] bufIn = new byte[8024];
                // int limit = is.read(bufIn);
                // System.out.println("客户端输入为：" + new String(bufIn, 0, limit));

                // 使用封装流
                System.out.println("客户端输入为：" + input.readAll());

                os.write("服务器已经收到请求数据".getBytes());
                os.flush();

                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.close();
        }

    }

}
