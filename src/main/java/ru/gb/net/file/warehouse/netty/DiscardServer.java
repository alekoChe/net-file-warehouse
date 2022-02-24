package ru.gb.net.file.warehouse.netty;

import java.nio.channels.SocketChannel;

public class DiscardServer {

    private final int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
    }
}
