package ru.gb.net.file.warehouse.transport;

import java.io.IOException;
import java.nio.file.Path;

public class FileMessage implements CloudMessage {

    private final byte[] bytes;
    private final long size;
    private final String name;

    public FileMessage(Path path, byte[] bytes, long size) throws IOException {
        this.bytes = bytes;
        this.size = size;
        name = path.getFileName().toString();
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.FILE;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public long getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
}
