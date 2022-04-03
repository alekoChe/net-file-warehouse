package ru.gb.net.file.warehouse.transport;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ServerHandler extends SimpleChannelInboundHandler<CloudMessage> {

    private Path serverDir;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CloudMessage cloudMessage) throws Exception {
        FileMessage fm = (FileMessage) cloudMessage;
        if (this.getServerDir().resolve(fm.getName()).toFile().exists()) {
            Files.write(this.getServerDir().resolve(fm.getName()), fm.getBytes(), StandardOpenOption.APPEND);

        } else {
            Files.write(this.getServerDir().resolve(fm.getName()), fm.getBytes());
        }
        if (fm.getSize() == this.getServerDir().resolve(fm.getName()).toFile().length()) {
            System.out.println("Finish file transfer");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Client connected");
        serverDir = Paths.get("/Users/bchervoniy/IdeaProjects/net-file-warehouse/server-dir");
    }

    public Path getServerDir() {
        return serverDir;
    }

    public void setServerDir(Path serverDir) {
        this.serverDir = serverDir;
    }
}
