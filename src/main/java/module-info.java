module app {
    requires javafx.controls;
    requires javafx.fxml;


    opens app to javafx.fxml;
    exports app;
    exports app.ui;
    opens app.ui to javafx.fxml;
    exports app.ui.menu;
    opens app.ui.menu to javafx.fxml;
    exports app.ui.styles;
    opens app.ui.styles to javafx.fxml;
    exports app.ui.games.chess;
    opens app.ui.games.chess to javafx.fxml;
    exports app.ui.utils;
    opens app.ui.utils to javafx.fxml;
}