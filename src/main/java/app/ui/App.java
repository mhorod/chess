package app.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {

        Pane board = new Chessboard(60);
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