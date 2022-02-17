package ru.gb.net.file.warehouse;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class ServerTest {

    private static final String FILE_PARAMS_TPL = "FILE_NAME=%s&USERNAME=Bogdan&PASS=qwerty ";
    public static final String PATH_TO_CLIENT_DIR = "/Users/bchervoniy/IdeaProjects/net-file-warehouse/src/test/resources/clientDir/";

    @Test
    void testServer() throws IOException {
        List<Socket> sockets = new ArrayList<>();
        for (int i = 0; i < 1500; i++) {
            Socket clientSocket = new Socket("localhost", 45001);
            sockets.add(clientSocket);
        }
        System.out.println(sockets);
    }

    @Test
    void sendFileToServerWithIo() throws IOException {
        Socket clientSocket = new Socket("localhost", 45001);
        File file = new File(PATH_TO_CLIENT_DIR + "cat2.png");
        try (FileInputStream fileInputStream = new FileInputStream(file);
             OutputStream outputStream = clientSocket.getOutputStream()) {
            String metadata = String.format(FILE_PARAMS_TPL, file.getName());
            outputStream.write(metadata.getBytes(StandardCharsets.UTF_8));
            fileInputStream.transferTo(outputStream);
        }
    }

    @Test
    void sendFileToServerWithNio() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 45001));
        socketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_WRITE);

        selector.select();

        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        RandomAccessFile fileForSend = new RandomAccessFile(PATH_TO_CLIENT_DIR + "cat1.png", "rw");
        FileChannel fileChannel = fileForSend.getChannel();

        for (SelectionKey selectionKey : selector.selectedKeys()) {
            if (selectionKey.isValid() && selectionKey.isWritable()) {
                while (fileChannel.read(byteBuffer) != -1) {
                    byteBuffer.flip();
                    socketChannel.write(byteBuffer);
                    byteBuffer.clear();
                }
            }
        }

    }
}