package com.note_app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.Desktop;

public class HelloController implements Initializable {
    @FXML
    private AnchorPane scenePane;
    @FXML
    private TextArea textArea;

    private Stage stage;
    private FileChooser fileChooser;
    private File initialDirectory;
    private File currentFile;
    private File file;
    private boolean alreadySaved;
    private String currentText;
    private String previousText;
    private String text;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileChooser = new FileChooser();
        initialData();
    }

    public void initialData() {
        textArea.clear();
        alreadySaved = true;
        currentFile = null;
        currentText = "";
        previousText = "";
    }

    public void newFile(ActionEvent event) {
        if (alreadySaved) {
            initialData();
        }else if (discardCurFileChanges()) {
            // create new file is confirmed
            initialData();
        }
    }

    public void openFile(ActionEvent event) throws IOException {
        fileChooser.setTitle("Open file");
        File file = fileChooser.showOpenDialog(scenePane.getScene().getWindow());

        if (file != null) {
            currentFile = file;
            char[] file_content_chars = new char[1000];
            String file_content;

            FileReader file_read = new FileReader(currentFile);

            // read file and put content in file_content_chars
            file_read.read(file_content_chars);
            file_read.close();

            // put all chars from file_content_chars to file_content String
            file_content = new String(file_content_chars);

            // update textarea
            updateTextArea(file_content);

            // update file chooser initial directory
            fileChooser.setInitialDirectory(new File(currentFile.getParent()));

            // update stage title
            updateStageTitle(currentFile.getPath());
        }
    }

    public void saveFile(ActionEvent event) throws IOException {
        if (!alreadySaved) {
            text = textArea.getText();

            if (currentFile == null) {
                fileChooser.setTitle("Save file");
                fileChooser.setInitialFileName("untitled.txt");

                file = fileChooser.showSaveDialog(scenePane.getScene().getWindow());

                if (file != null) {
                    currentFile = file;
                    // write in file
                    writeInFile(currentFile.getPath(), text + "\n");

                    // update file chooser initial directory
                    fileChooser.setInitialDirectory(new File(currentFile.getParent()));

                    // update alreadySaved value
                    alreadySaved = true;
                    // update stage title
                    updateStageTitle(currentFile.getPath());
                }
            }else {
                writeInFile(currentFile.getPath(), text + "\n");

                // update alreadySaved value
                alreadySaved = true;
                // update stage title
                updateStageTitle(currentFile.getPath());
            }
        }
    }

    public boolean discardCurFileChanges() {
        boolean res = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Unsaved changes");
        alert.setHeaderText("The current file contains unsaved changes.");
        alert.setContentText("Do you want to discard changes ?");

        res = alert.showAndWait().get() == ButtonType.OK;

        return res;
    }

    public void updateTextArea(String text) {
        textArea.setText(text);
    }

    public void writeInFile(String file_path, String content) throws IOException {
        FileWriter file = new FileWriter(file_path);

        file.write(content);
        file.close();
    }

    public void clear(ActionEvent event) {
        textArea.clear();
    }

    public void about(ActionEvent event) throws URISyntaxException, IOException {
        /* For about, just open my github page (ridoineel)
        *
        */

        String url = "https://github.com/ridoineel";
        openLink(url);
    }

    public void openLink(String url) throws IOException {
        /* Open page in web browser
        *
        * */

        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();
        String[] browsers = {"firefox", "mozilla", "google-chrome", "epiphany",
                "konqueror", "netscape", "opera", "lynx"};

        if (os.indexOf("win") >= 0) {
            // Windows
            rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
        }else if (os.indexOf("mac") >= 0) {
            // Mac
            rt.exec("open " + url);
        }else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
            // Linux
            StringBuffer cmd = new StringBuffer();
            String one_command = new String();

            for (int i = 0; i < browsers.length; i++) {
                one_command = (i != 0) ? " || ": "";
                one_command += "%s \"%s\"";

                cmd.append(String.format(one_command, browsers[i], url));
            }

            rt.exec(new String[] {"sh", "-c", cmd.toString()});
        }
    }

    public void updateSaveStatus(KeyEvent event) {
        currentText = textArea.getText();

        if (!currentText.equals(previousText)) {
            alreadySaved = false;
            previousText = currentText;
        }

        if (currentFile != null) {
            // update stage title
            updateStageTitle(currentFile.getPath());
        }
    }

    public void updateStageTitle(String curFilePath) {
        stage = (Stage) scenePane.getScene().getWindow();
        String title = "Note App";

        if (!alreadySaved) {
            title = "* " + title;
        }

        if (!curFilePath.isEmpty()) {
//            if (curFilePath.length() > 30) {
//                title += " | " + curFilePath.substring(0, 22);
//            }else {
//                title += " | " + curFilePath;
//            }

            title += " | " + curFilePath;
            stage.setTitle(title);
        }
    }

    public void logout() {
        stage = (Stage) scenePane.getScene().getWindow();

        if (alreadySaved) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText("You are about logout");
            alert.setContentText("Do you want to logout ?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                stage.close();
            }
        }else if (discardCurFileChanges()) {
            stage.close();
        }
    }

    public void logout(ActionEvent e) {
        logout();
    }
}