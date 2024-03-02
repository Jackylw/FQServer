module top.fexample.fqserver {
    requires javafx.controls;
    requires javafx.fxml;


    opens top.fexample.fq to javafx.fxml;
    exports top.fexample.fq;
    exports top.fexample.fq.controller;
    opens top.fexample.fq.controller to javafx.fxml;
    exports top.fexample.fq.model;
    opens top.fexample.fq.model to javafx.fxml;
}