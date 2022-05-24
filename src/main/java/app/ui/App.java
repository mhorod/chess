package app.ui;

import app.ui.menu.MenuContainer;
import app.ui.views.MainMenu;
import app.ui.views.ViewContainer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Style style = new Style() {
            {
                whitePiece = Color.color(0.9, 0.9, 0.9);
                blackPiece = Color.web("#FF8CE1");

                whiteField = Color.web("#FFC8E5");
                blackField = Color.web("#EA5CB1");

                borderWhite = Color.web("#75234f");
                borderBlack = Color.web("#75234f");

                borderText = Color.color(0.9, 0.9, 0.9);
                whiteFieldCircle = Color.web("#00bbfa");
                blackFieldCircle = Color.web("#47e0ff");
                font = Font.loadFont(App.class.getResource("/fonts/regular.otf").toExternalForm(), 20);
            }
        };
        var container = new ViewContainer(style);
        var menu = new MenuContainer(container, style);

        var mainMenu = new MainMenu(menu);
        Platform.runLater(() -> {
            menu.changeMenu(mainMenu);
        });


        container.changeView(menu);
        menu.appear();
        
        Scene scene = new Scene(container, 1024, 800);
        scene.setFill(style.whiteField);
        stage.setScene(scene);
        stage.setTitle("Epic chess");
        stage.show();
    }
}