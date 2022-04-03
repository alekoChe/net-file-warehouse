package ru.gb.net.file.warehouse.transport;

import java.io.Serializable;

public enum MessageType implements Serializable {
    FILE,
    LIST,
    FILE_REQUEST,
    DIRECTORY,
    AUTH,
    MKDIR,
    PATH_GET,
    DELETE,
    RENAME
}