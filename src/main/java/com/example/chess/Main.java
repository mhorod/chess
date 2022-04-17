package com.example.chess;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Pane board = new Chessboard(60);
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(board);
        pane.setFillWidth(true);
        Scene scene = new Scene(pane, 1024, 800, true);
        scene.setFill(Color.color(0.2,0.2,0.2));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Epic chess");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}