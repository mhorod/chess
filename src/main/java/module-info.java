module app {
    requires javafx.controls;
    requires javafx.fxml;


    opens app to javafx.fxml;
    exports app;
}