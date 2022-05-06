package app.ui;

import app.ui.views.MainMenu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
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
        container.setBackground(Background.EMPTY);
        container.setFillWidth(false);
        container.setAlignment(Pos.CENTER);

        var mainMenu = new MainMenu(new GameContainer() {
            @Override
            public void changeView(Node node) {
                container.getChildren().clear();
                container.getChildren().add(node);
            }

            @Override
            public void exit() {
                Platform.exit();
            }

            @Override
            public Style getStyle() {
                return style;
            }
        });
        container.getChildren().add(mainMenu);

        Scene scene = new Scene(container, 1024, 800);
        scene.setFill(style.whiteField);
        scene.setOnMouseMoved(e -> {
            mainMenu.updateMousePosition(e.getSceneX(), e.getSceneY());
        });
        stage.setScene(scene);
        stage.setTitle("Epic chess");
        stage.show();
    }
}