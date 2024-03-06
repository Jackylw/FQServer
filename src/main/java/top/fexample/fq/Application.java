package top.fexample.fq;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import top.fexample.fq.model.ServerCommunication;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        ServerCommunication serverCommunication = new ServerCommunication();
    }

    public static void main(String[] args) {
        launch();
    }
}