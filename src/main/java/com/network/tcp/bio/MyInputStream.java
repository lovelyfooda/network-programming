package com.network.tcp.bio;

import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyInputStream extends FilterInputStream {

    private byte[] buf;
    private int SIZE = 12;

    public MyInputStream(InputStream is) {
        super(is);
        buf = new byte[SIZE];
    }

    public String readAll() throws IOException {
        int limit = in.read(buf);
        if (limit < SIZE) {
            // 一次就已经读完了
            return new String(buf, 0, limit);
        } else {
            if (buf[limit - 2] == '\r' && buf[limit - 1] == '\n') {
                return new String(buf, 0, limit);
            } else {
                ByteArrayOutputStream bis = new ByteArrayOutputStream();
                bis.write(buf);

                while (true) {
                    limit = in.read(buf);
                    if (limit == SIZE) {
                        bis.write(buf);
                    } else {
                        byte[] array = new byte[limit];
                        System.arraycopy(buf, 0, array, 0, limit);
                        bis.write(array);
                    }
                    if (buf[limit - 2] == '\r' && buf[limit - 1] == '\n') {
                        break;
                    }
                }
                return new String(bis.toByteArray());
            }
        }
    }

}
