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
import javafx.stage.Window;

import java.io.*;
import java.net.URL;
import java.nio.CharBuffer;
import java.util.ResourceBundle;

/* TODO:
*   Add command to menu bar, CTRL+S, etc..
*   New file: prompt alert (file not saved) (ActionEvent, check with textArea length)
*   Manage other menu
*
* */
public class HelloController implements Initializable {
    @FXML
    private AnchorPane scenePane;
    @FXML
    private TextArea textArea;

    private FileChooser fileChooser;
    private File initialDirectory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileChooser = new FileChooser();
    }

    public void newFile(ActionEvent event) {

    }

    public void openFile(ActionEvent event) throws IOException {
        fileChooser.setTitle("Open file");
        File file = fileChooser.showOpenDialog(scenePane.getScene().getWindow());
        char[] file_content_chars = new char[1000];
        String file_content;

        FileReader file_read = new FileReader(file);

        // read file and put content in file_content_chars
        file_read.read(file_content_chars);
        file_read.close();

        file_content = new String(file_content_chars);

        // update textarea
        updateTextArea(file_content);

        // update file chooser initial directory
        fileChooser.setInitialDirectory(new File(file.getParent()));
    }

    public void saveFile(ActionEvent event) throws IOException {
        fileChooser.setTitle("Save file");
//        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("ddd", ".mp4"));

        File file = fileChooser.showSaveDialog(scenePane.getScene().getWindow());
        String text = textArea.getText();

        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write(text + "\n");
        fileWriter.close();

        // update file chooser initial directory
        fileChooser.setInitialDirectory(new File(file.getParent()));
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