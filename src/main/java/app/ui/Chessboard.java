package app.ui;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Chessboard extends Pane {
    public final double fieldSize;
    Field[][] fields = new Field[8][8];
    BoardState state = new NormalState();

    public Chessboard(double fieldSize) {
        this.fieldSize = fieldSize;
        setMaxWidth(8 * fieldSize);
        setMaxHeight(8 * fieldSize);
        setEffect(new DropShadow());
        GridPane grid = new GridPane();


        for (int x = 0; x < 8; x++) {

            for (int y = 0; y < 8; y++) {
                fields[x][y] = new Field((x + y) % 2 == 1, x, y);
                grid.add(fields[x][y], x + 1, y + 1);
            }
        }
        for (int y = 0; y < 8; y++) {
            grid.add(new Label(String.valueOf(y + 1), y % 2 == 0), 0, y + 1);
        }

        for (int x = 0; x < 8; x++) {
            grid.add(new Label(String.valueOf((char) (7 - x + 65)), x % 2 == 1), x + 1, 9);
        }


        getChildren().add(grid);
        List<Piece> pieces = new ArrayList<Piece>();
        for (int x = 0; x < 8; x++) {
            pieces.add(new Piece(new LogicalPiece(x, 1), ImageManager.PAWN, false));
        }
        pieces.add(new Piece(new LogicalPiece(0, 0), ImageManager.ROOK, false));
        pieces.add(new Piece(new LogicalPiece(1, 0), ImageManager.KNIGHT, false));
        pieces.add(new Piece(new LogicalPiece(2, 0), ImageManager.BISHOP, false));
        pieces.add(new Piece(new LogicalPiece(3, 0), ImageManager.KING, false));
        pieces.add(new Piece(new LogicalPiece(4, 0), ImageManager.QUEEN, false));
        pieces.add(new Piece(new LogicalPiece(5, 0), ImageManager.BISHOP, false));
        pieces.add(new Piece(new LogicalPiece(6, 0), ImageManager.KNIGHT, false));
        pieces.add(new Piece(new LogicalPiece(7, 0), ImageManager.ROOK, false));

        for (int x = 0; x < 8; x++) {
            pieces.add(new Piece(new LogicalPiece(x, 6), ImageManager.PAWN, true));
        }
        pieces.add(new Piece(new LogicalPiece(0, 7), ImageManager.ROOK, true));
        pieces.add(new Piece(new LogicalPiece(1, 7), ImageManager.KNIGHT, true));
        pieces.add(new Piece(new LogicalPiece(2, 7), ImageManager.BISHOP, true));
        pieces.add(new Piece(new LogicalPiece(3, 7), ImageManager.KING, true));
        pieces.add(new Piece(new LogicalPiece(4, 7), ImageManager.QUEEN, true));
        pieces.add(new Piece(new LogicalPiece(5, 7), ImageManager.BISHOP, true));
        pieces.add(new Piece(new LogicalPiece(6, 7), ImageManager.KNIGHT, true));
        pieces.add(new Piece(new LogicalPiece(7, 7), ImageManager.ROOK, true));


        for (Piece p : pieces)
            getChildren().add(p);
    }

    private static class Label extends VBox {
        static Font font = Font.loadFont(Label.class.getResource("/fonts/AzeretMono-Bold.ttf").toExternalForm(), 20);

        Label(String content, boolean dark) {
            super();
            var text = new Text(content);
            text.setFont(font);
            text.setFill(Color.color(0.9, 0.9, 0.9));
            text.setEffect(new DropShadow());
            setMinWidth(25);
            setMinHeight(25);
            setBackground(new Background(new BackgroundFill(dark ? Color.color(0.3, 0.3, 0.3) : Color.color(0.5, 0.5, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
            getChildren().add(text);
            setAlignment(Pos.CENTER);
        }
    }

    abstract class BoardState {
        void onPieceClick(Piece p) {
        }

        void onFieldClick(Field f) {
        }

        void onFieldMouseEntered(Field f) {
        }

        void onPieceDrag(Piece p, MouseEvent e) {
        }

        void onPieceDrop(Piece p) {
        }

        void init() {
        }

        void cleanUp() {
        }

        final void changeState(BoardState state) {
            cleanUp();
            Chessboard.this.state = state;
            state.init();
        }
    }

    class NormalState extends BoardState {
        @Override
        void onPieceClick(Piece p) {
            changeState(new PieceSelectedState(p));
        }

        @Override
        void onPieceDrag(Piece p, MouseEvent e) {
            changeState(new PieceDraggedState(p));
        }
    }

    class PieceSelectedState extends BoardState {
        Piece selectedPiece;
        Field highlightedField;
        List<Field> legalFields;

        PieceSelectedState(Piece p) {
            this.selectedPiece = p;
            legalFields = p.piece.getLegalMoves().stream().map(f -> fields[f.x()][f.y()]).collect(Collectors.toList());
        }

        @Override
        void onPieceClick(Piece p) {
            if (p != selectedPiece)
                changeState(new PieceSelectedState(p));
            else
                changeState(new NormalState());
        }

        @Override
        void onFieldClick(Field f) {
            if (f.legal)
                selectedPiece.piece.makeMove(f.x, f.y);
            changeState(new NormalState());
        }

        @Override
        void onFieldMouseEntered(Field f) {
            if (highlightedField != null)
                highlightedField.dehighlight();
            if (f.legal) {
                f.highlight();
                highlightedField = f;
            }
        }

        @Override
        void onPieceDrag(Piece p, MouseEvent e) {
            changeState(new PieceDraggedState(p));
        }

        @Override
        void init() {
            selectedPiece.pickUp();
            for (Field f : legalFields)
                f.markAsLegal();
        }

        @Override
        void cleanUp() {
            selectedPiece.putDown();
            if (highlightedField != null)
                highlightedField.dehighlight();
            for (Field f : legalFields)
                f.toNormal();
        }
    }

    class PieceDraggedState extends BoardState {
        Piece selectedPiece;
        Field highlightedField;
        List<Field> legalFields;

        PieceDraggedState(Piece p) {
            this.selectedPiece = p;
            legalFields = p.piece.getLegalMoves().stream().map(f -> fields[f.x()][f.y()]).collect(Collectors.toList());
        }

        static double distance2(Field a, double x, double y) {
            double deltaX = a.getCenter().getX() - x;
            double deltaY = a.getCenter().getY() - y;
            return deltaX * deltaX + deltaY * deltaY;
        }

        @Override
        void onPieceDrag(Piece p, MouseEvent e) {
            selectedPiece.setCenterX(e.getX());
            selectedPiece.setCenterY(e.getY());
            if (highlightedField != null)
                highlightedField.dehighlight();
            highlightedField = nearestLegal(e.getX(), e.getY());
            highlightedField.highlight();
        }

        @Override
        void onPieceDrop(Piece p) {
            if (highlightedField != null)
                selectedPiece.piece.makeMove(highlightedField.x, highlightedField.y);
            changeState(new NormalState());
        }

        @Override
        void init() {
            selectedPiece.pickUp();
            for (Field f : legalFields)
                f.markAsLegal();
        }

        @Override
        void cleanUp() {
            selectedPiece.putDown();
            if (highlightedField != null)
                highlightedField.dehighlight();
            for (Field f : legalFields)
                f.toNormal();
        }

        Field nearestLegal(double realX, double realY) {
            return legalFields.stream()
                    .min((a, b) -> (int) Math.signum(distance2(a, realX, realY) - distance2(b, realX, realY)))
                    .orElse(null);
        }
    }

    private class Piece extends ImageView {
        LogicalPiece piece;

        private Piece(LogicalPiece piece, Image img, boolean black) {
            super(img);
            this.piece = piece;
            setFitWidth(fieldSize);
            setFitHeight(fieldSize);

            var effect = new ColorAdjust();
            effect.setBrightness(black ? -0.7 : -0.1);
            effect.setInput(new DropShadow());
            setEffect(effect);
            setCursor(Cursor.CLOSED_HAND);

            putDown();
            setOnMousePressed(e -> state.onPieceClick(this));
            setOnMouseDragged(e -> state.onPieceDrag(this, e));
            setOnMouseReleased(e -> state.onPieceDrop(this));
        }

        void pickUp() {
            toFront();
            setFitWidth(fieldSize * 1.2);
            setFitHeight(fieldSize * 1.2);
            Field currentField = fields[piece.x][piece.y];
            setCenterX(currentField.getCenter().getX());
            setCenterY(currentField.getCenter().getY());
        }

        void setCenterX(double x) {
            setX(x - getFitWidth() / 2);
        }

        void setCenterY(double y) {
            setY(y - getFitHeight() / 2);
        }

        void putDown() {
            setFitWidth(fieldSize);
            setFitHeight(fieldSize);
            Field currentField = fields[piece.x][piece.y];
            setCenterX(currentField.getCenter().getX());
            setCenterY(currentField.getCenter().getY());
        }
    }

    private class Field extends Rectangle {
        int x, y;
        boolean dark;
        boolean legal = false;

        private Field(boolean dark, int x, int y) {
            super(fieldSize, fieldSize, fieldSize, fieldSize);
            this.dark = dark;
            this.x = x;
            this.y = y;
            toNormal();
            setStrokeType(StrokeType.INSIDE);
            setStroke(Color.color(1, 0, 1));
            setStrokeWidth(0);

            setOnMouseEntered(e -> state.onFieldMouseEntered(this));
            setOnMousePressed(e -> state.onFieldClick(this));
        }

        Point2D getCenter() {
            return new Point2D(x * fieldSize + fieldSize / 2 + 25, y * fieldSize + fieldSize / 2);
        }

        void highlight() {
            setStrokeWidth(4);
        }

        void dehighlight() {
            setStrokeWidth(0);
        }

        void markAsLegal() {
            setCursor(Cursor.CLOSED_HAND);
            legal = true;
            setFill(dark ? Color.color(0.2, 0.6, 0.2) : Color.color(0.4, 0.8, 0.4));
        }

        void toNormal() {
            setCursor(Cursor.DEFAULT);
            legal = false;
            setFill(dark ? Color.color(0.2, 0.2, 0.2) : Color.color(0.7, 0.7, 0.7));
        }
    }
}