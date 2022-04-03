package ru.gb.net.file.warehouse.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client {

    public static final int MB_8 = 8_000_000;

    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.remoteAddress("localhost", 45001);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) {
                    socketChannel.pipeline().addLast(
                            new ObjectDecoder(1400000 * 100, ClassResolvers.cacheDisabled(null)),
                            new ObjectEncoder(),
                            new ClientHandler()
                    );
                }
            });
            bootstrap.option(ChannelOption.SO_BACKLOG, 128);// (6);
            ChannelFuture channelFuture = bootstrap.connect().sync();
            Channel channel = channelFuture.channel();
            upload1(channel,
                    Paths.get("/Users/bchervoniy/IdeaProjects/net-file-warehouse/client-dir/LWScreenShot 2021-09-22 at 10.39.54.png"));
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static void upload1(Channel channel, Path uploadedFile) {
        try {
            System.out.println("File transfer start");
            RandomAccessFile randomAccessFile = new RandomAccessFile(uploadedFile.toFile(), "r");
            FileChannel fileChannel = randomAccessFile.getChannel();
            long size = uploadedFile.toFile().length();
            ByteBuffer byteBuffer = ByteBuffer.allocate(MB_8);
            while (fileChannel.read(byteBuffer) != -1) {
                if (byteBuffer.position() > 0 && byteBuffer.hasRemaining()) {
                    byteBuffer = byteBuffer.slice(0, byteBuffer.position());
                }
                channel.writeAndFlush(new FileMessage(uploadedFile, byteBuffer.array(), size))
                        .addListener(new ChannelFutureListener() {
                            @Override
                            public void operationComplete(ChannelFuture future) throws Exception {
                                boolean success = future.isSuccess();
                            }
                        });
                byteBuffer.clear();
            }
            System.out.println("File transfer finish");
            randomAccessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
