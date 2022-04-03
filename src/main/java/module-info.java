module com.note_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.note_app to javafx.fxml;
    exports com.note_app;
}