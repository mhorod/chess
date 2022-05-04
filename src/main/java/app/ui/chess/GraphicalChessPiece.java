package app.ui.chess;

import app.chess.pieces.ChessPiece;
import app.chess.pieces.ChessPieceKind;
import app.ui.ImageManager;
import app.ui.board.GraphicalPiece;
import javafx.scene.paint.Color;

public class GraphicalChessPiece extends GraphicalPiece<ChessPiece> {
    private final Color white, black;

    public GraphicalChessPiece(ChessPieceKind type, Color white, Color black) {
        super(ImageManager.getPieceImage(type), white);
        this.white = white;
        this.black = black;
    }

    @Override
    public void update(ChessPiece piece) {
        setImage(ImageManager.getPieceImage(piece.getKind()));
        setColor(piece.getPlayer() == 0 ? white : black);
        if (!piece.isAlive()) disappear();
    }
}
