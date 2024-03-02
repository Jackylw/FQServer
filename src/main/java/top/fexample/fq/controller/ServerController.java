package top.fexample.fq.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import top.fexample.fq.model.ServerCommunication;

public class ServerController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void startServer() {
        try{
            ServerCommunication serverCommunication = new ServerCommunication();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}