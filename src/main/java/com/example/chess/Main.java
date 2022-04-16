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
        Pane board = new Pane();
        GridPane chessboard = new GridPane();
        double s = 60; // side of rectangle
        board.setMaxWidth(8*s);
        board.setMaxHeight(8*s);
        Rectangle[][] fields = new Rectangle[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle r = new Rectangle(s, s, s, s);
                r.setPickOnBounds(true);
                if ((i+j) % 2 == 0) r.setFill(Color.color(0.7, 0.7, 0.7));
                else r.setFill(Color.color(0.1, 0.1, 0.1));
                r.setStrokeType(StrokeType.INSIDE);
                r.setStroke(Color.color(1.0, 0.0, 1.0));
                r.setStrokeWidth(0);
                fields[j][i] = r;
                chessboard.add(r, j, i);
            }
        }

        Circle circle = createCircle("#ff00ff", "#ff88ff",100);
        circle.setCursor(Cursor.CLOSED_HAND);
        circle.setRadius(s*0.4);
        circle.setCenterX(s/2);
        circle.setCenterY(s/2);
        circle.setOnMousePressed(e -> {
            circle.setRadius(s*0.5);
        });


        circle.setOnMouseDragged(e -> {
            circle.setCenterX(e.getX());
            circle.setCenterY(e.getY());
            int x = (int) Math.floor(e.getX()/s);
            int y = (int) Math.floor(e.getY()/s);
            if(x >= 0 && x < 8 && y >= 0 && y < 8) {
                for(int i = 0; i < 8; i++)
                    for(int j = 0; j < 8; j++)
                        fields[i][j].setStrokeWidth(0);
                fields[x][y].setStrokeWidth(4);
            }
        });

        circle.setOnMouseReleased(e -> {
            circle.setRadius(s*0.4);
            circle.setCenterX(Math.floor(e.getX() / s) * s + s/2);
            circle.setCenterY(Math.floor(e.getY() / s) * s + s/2);
            for(int i = 0; i < 8; i++)
                for(int j = 0; j < 8; j++)
                    fields[i][j].setStrokeWidth(0);
        });

        board.getChildren().add(chessboard);
        board.getChildren().add(circle);
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(board);
        pane.setFillWidth(true);
        Scene scene = new Scene(pane, 1024, 800, true);
        scene.setFill(Color.color(0.2,0.2,0.2));
        primaryStage.setScene(scene);
        primaryStage.setTitle("2D Example");

        primaryStage.show();
    }

    private Circle createCircle(String strokeColor, String fillColor, double x) {
        Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(200);
        circle.setRadius(50);
        circle.setStroke(Color.valueOf(strokeColor));
        circle.setStrokeWidth(5);
        circle.setFill(Color.valueOf(fillColor));
        return circle;
    }

    public static void main(String[] args) {
        launch();
    }
}