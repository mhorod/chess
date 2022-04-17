package com.example.chess;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongToIntFunction;


public class Chessboard extends Pane {
    public final double fieldSize;
    Field[][] fields = new Field[8][8];
    List<Field> legalFields = new ArrayList<>();
    Field highlightedField;

    private class Piece extends Circle {
        LogicalPiece piece;

        private Piece(LogicalPiece piece, Color fill, Color stroke) {
            this.piece = piece;
            setCursor(Cursor.CLOSED_HAND);
            Field currentField = fieldAt(piece.x, piece.y);
            setCenterX(currentField.getCenter().getX());
            setCenterY(currentField.getCenter().getY());
            setStroke(stroke);
            setFill(fill);
            setStrokeWidth(5);

            putDown();

            setOnMousePressed(e -> pickUp());

            setOnMouseDragged(e -> {
                setCenterX(e.getX());
                setCenterY(e.getY());
                Field current = nearestLegal(e.getX(), e.getY());
                if(current != null) current.highlight();
            });

            setOnMouseReleased(e -> {
                piece.makeMove(highlightedField.x, highlightedField.y);
                setCenterX(fields[piece.x][piece.y].getCenter().getX());
                setCenterY(fields[piece.x][piece.y].getCenter().getY());
                putDown();
                dehighlightAll();
            });
        }

        void pickUp() {
            toFront();
            Field current = fields[piece.x][piece.y];
            current.highlight();
            setLegalFields(piece.getLegalMoves());
            setRadius(fieldSize*0.5);
        }

        void putDown() {
            setRadius(fieldSize*0.4);
            setLegalFields(List.of());
        }
    }

    private class Field extends Rectangle {
        int x, y;
        boolean dark;
        private Field(boolean dark, int x, int y) {
            super(fieldSize, fieldSize, fieldSize, fieldSize);
            this.dark = dark;
            this.x = x;
            this.y = y;
            toNormal();
            setStrokeType(StrokeType.INSIDE);
            setStroke(Color.color(1, 0, 1));
            setStrokeWidth(0);
        }

        Point2D getCenter() {
            return new Point2D(x * fieldSize + fieldSize / 2, y * fieldSize + fieldSize / 2);
        }

        void highlight() {
            dehighlightAll();
            highlightedField = this;
            setStrokeWidth(4);
        }

        void dehighlight() {
            setStrokeWidth(0);
        }

        void markAsLegal() {
            legalFields.add(this);
            setFill(dark ? Color.color(0.2, 0, 0.2) : Color.color(0.8, 0.3, 0.8));
        }

        void toNormal() {
            setFill(dark ? Color.color(0.1, 0.1, 0.1) : Color.color(0.7, 0.7, 0.7));
        }
    }

    Field fieldAt(double realX, double realY) {
        int x = (int) Math.floor(realX/fieldSize);
        int y = (int) Math.floor(realY/fieldSize);
        if(x >= 0 && x < 8 && y >= 0 && y < 8) return fields[x][y];
        else return null;
    }

    Field nearestLegal(double realX, double realY) {
        return legalFields.stream().min((a, b) -> (int) Math.signum(distance2(a, realX, realY) - distance2(b, realX, realY))).orElse(null);
    }

    static double distance2(Field a, double x, double y) {
        double deltaX = a.getCenter().getX() - x;
        double deltaY = a.getCenter().getY() - y;
        return deltaX * deltaX + deltaY * deltaY;
    }

    void dehighlightAll() {
        if(highlightedField != null) highlightedField.dehighlight();
        highlightedField = null;
    }

    void setLegalFields(List<LogicalField> fields) {
        for(Field f : legalFields)
            f.toNormal();
        legalFields.clear();
        for(LogicalField f : fields)
            this.fields[f.x()][f.y()].markAsLegal();
    }


    public Chessboard(double fieldSize) {
        this.fieldSize = fieldSize;
        setMaxWidth(8*fieldSize);
        setMaxHeight(8*fieldSize);
        GridPane grid = new GridPane();


        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                fields[x][y] = new Field((x + y) % 2 == 1, x, y);
                grid.add(fields[x][y], x, y);
            }
        }

        getChildren().add(grid);

        for(int x = 0; x < 14; x++) {
            Piece circle = new Piece(new LogicalPiece(0, 0), Color.color(1, .5, 1), Color.color(1, 0, 1));
            getChildren().add(circle);
        }
    }
}