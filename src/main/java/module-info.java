module app {
    requires javafx.controls;
    requires javafx.fxml;


    opens app to javafx.fxml;
    exports app;
    exports app.ui;
    opens app.ui to javafx.fxml;
    exports app.ui.menu;
    opens app.ui.menu to javafx.fxml;
}