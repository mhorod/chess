package app.ui.board;

import app.chess.pieces.ChessPieceKind;
import app.ui.ImageManager;
import app.ui.utils.ColoredImage;
import app.ui.utils.Position;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;

public class GraphicalPiece extends ColoredImage {

    public GraphicalPiece(ChessPieceKind type, GraphicalField graphicalField, Color color) {
        super(ImageManager.getPieceImage(type), color);
        putDown(graphicalField);
        setCursor(Cursor.HAND);
    }

    public void pickUp(GraphicalField graphicalField) {
        toFront();
        setFitWidth(graphicalField.getSize() * 1.2);
        setFitHeight(graphicalField.getSize() * 1.2);
        setCenter(graphicalField.getCenter());
    }

    public void putDown(GraphicalField graphicalField) {
        setFitWidth(graphicalField.getSize());
        setFitHeight(graphicalField.getSize());
        setCenter(graphicalField.getCenter());
    }

    public void setCenter(Position pos) {
        setX(pos.x() - getFitWidth() / 2);
        setY(pos.y() - getFitHeight() / 2);
    }
}
