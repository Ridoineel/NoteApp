package com.note_app;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("render.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloController ctrl = fxmlLoader.getController();

        stage.setTitle("Note App");
        stage.setScene(scene);
        stage.setMinWidth(300);
        stage.setMinHeight(300);
        stage.show();

        stage.setOnCloseRequest((event) -> {
            event.consume();
            ctrl.logout();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}