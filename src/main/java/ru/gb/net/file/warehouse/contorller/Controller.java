package ru.gb.net.file.warehouse.contorller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextArea textFieldFx;

    @FXML
    public void sendText(ActionEvent actionEvent) {
        System.out.println(textFieldFx.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
