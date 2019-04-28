package com.yc.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

public class MyClass {
    public static void main(String[] args){

//        String host = "220.181.152.112";
//        int port = 35502;

        String host = "127.0.0.1";
        int port = 60010;
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host,port),3000);
            Sink sink = Okio.sink(socket);
            BufferedSink bufferedSink = Okio.buffer(sink);

            Source source = Okio.source(socket);
            final BufferedSource bufferedSource = Okio.buffer(source);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try {
                            String a = bufferedSource.readUtf8Line();
                            System.out.print(a);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).start();

//            for (int i = 0; i < 10; i++) {
//                bufferedSink.writeUtf8(i + "");
//                bufferedSink.flush();
//                Thread.sleep(1000);
//            }

//            bufferedSink.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
