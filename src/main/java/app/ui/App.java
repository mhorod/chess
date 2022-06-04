package app.ui;

import app.ui.menu.MenuContainer;
import app.ui.styles.Style;
import app.ui.styles.Styles;
import app.ui.views.MainMenu;
import app.ui.views.ViewContainer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Style style = Styles.styles.get("Cute Pink");

        var container = new ViewContainer(style);
        var menu = new MenuContainer(container, style);

        var mainMenu = new MainMenu(menu);
        Platform.runLater(() -> {
            menu.changeMenu(mainMenu);
        });

        container.changeView(menu);
        menu.appear();
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            var scale = Math.min(Math.min(stage.getWidth() / 1024, stage.getHeight() / 800), 1);
            container.setScaleX(scale);
            container.setScaleY(scale);
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            var scale = Math.min(Math.min(stage.getWidth() / 1024, stage.getHeight() / 800), 1);
            container.setScaleX(scale);
            container.setScaleY(scale);
        });

        Scene scene = new Scene(container, 1024, 800);
        scene.setFill(style.whiteField);
        stage.setScene(scene);
        stage.setTitle("Epic chess");
        stage.show();
    }
}