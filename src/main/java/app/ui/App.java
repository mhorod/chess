package app.ui;

import app.ui.board.Board;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Style style = new Style() {
            {
                whitePiece = Color.color(0.8, 0.8, 0.8);
                blackPiece = Color.color(0.3, 0.3, 0.3);
                whiteField = Color.color(0.7, 0.7, 0.7);
                blackField = Color.color(0.4, 0.4, 0.4);
                borderWhite = Color.color(0.5, 0.5, 0.5);
                borderBlack = Color.color(0.4, 0.4, 0.4);
                borderText = Color.color(0.9, 0.9, 0.9);
                whiteFieldCircle = Color.color(0.5, 0.5, 0.5, 0.7);
                blackFieldCircle = Color.color(0.6, 0.6, 0.6, 0.7);
                font = Font.loadFont(App.class.getResource("/fonts/AzeretMono-Bold.ttf").toExternalForm(), 20);
            }
        };
        Pane board = new Board(60, style);
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(board);
        pane.setFillWidth(true);
        Scene scene = new Scene(pane, 1024, 800, true);
        scene.setFill(Color.color(0.2, 0.2, 0.2));
        stage.setScene(scene);
        stage.setTitle("Epic chess");
        stage.show();
    }
}