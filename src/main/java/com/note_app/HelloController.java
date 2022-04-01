package com.note_app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private AnchorPane scenePane;
    @FXML
    private TextArea textArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("ridoine choose");

        //scenePane.getChildren().add((Node) fileChooser);
    }

    public void newFile(ActionEvent event) {

    }

    public void openFile(ActionEvent event) {

    }

    public void saveFile(ActionEvent event) {

    }

    public void updateTextArea(String text) {
        textArea.setText(text);
    }

    public void writeInFile(String file_path, String content) throws IOException {
        FileWriter file = new FileWriter(file_path);

        file.write(content);
    }

    public void logout(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about logout");
        alert.setContentText("Do you want to logout ?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) scenePane.getScene().getWindow();
            stage.close();
        }
    }
}