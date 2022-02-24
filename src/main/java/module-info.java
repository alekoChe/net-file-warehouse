module io.gb.main {
    requires javafx.controls;
    requires javafx.fxml;

    opens ru.gb.net.file.warehouse.contorller to javafx.fxml;
    exports ru.gb.net.file.warehouse;
    exports ru.gb.net.file.warehouse.io;
    exports ru.gb.net.file.warehouse.streamapi;
}