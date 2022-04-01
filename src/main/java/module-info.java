module com.note_app {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.note_app to javafx.fxml;
    exports com.note_app;
}