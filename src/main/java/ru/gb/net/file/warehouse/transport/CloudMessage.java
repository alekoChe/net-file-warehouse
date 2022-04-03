package ru.gb.net.file.warehouse.transport;

import java.io.Serializable;

public interface CloudMessage extends Serializable {

    MessageType getMessageType();
}