package ru.gb.net.file.warehouse.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IoServer {

    public static final String PATH_TO_SERVER_DIR = "/Users/bchervoniy/IdeaProjects/net-file-warehouse/src/main/resources/serverDir";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(45001, 500);
        while (true) {
            Socket connection = serverSocket.accept(); // block
            System.out.println("Connected: " + connection.getRemoteSocketAddress());
            new Thread(() -> processConnection(connection)).start();
        }
    }

    private static void processConnection(Socket connectionSocket) {
        try (connectionSocket;
             InputStream inputStream = connectionSocket.getInputStream()) {
            FileOutputStream fileOutputStream = new FileOutputStream(PATH_TO_SERVER_DIR + "somecat.png");
            inputStream.transferTo(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
