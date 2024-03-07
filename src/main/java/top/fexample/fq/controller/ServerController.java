package top.fexample.fq.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import top.fexample.fq.model.ServerCommunication;

public class ServerController {
    @FXML
    protected void startServer() {
        try {
            ServerCommunication serverCommunication = new ServerCommunication();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}