package app.ui;

import app.ui.menu.MenuContainer;
import app.ui.views.MainMenu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
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
        var container = new VBox();
        var menu = new MenuContainer(style);

        var mainMenu = new MainMenu(menu);
        Platform.runLater(() -> {
            menu.changeView(mainMenu);
        });


        container.getChildren().add(menu);
        container.setAlignment(Pos.CENTER);
        container.setOnMouseMoved(e -> menu.updateMousePosition(e.getSceneX(), e.getSceneY()));
        Scene scene = new Scene(container, 1024, 800);
        scene.setFill(style.whiteField);
        stage.setScene(scene);
        stage.setTitle("Epic chess");
        stage.show();
    }
}