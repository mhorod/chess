package app.ui.checkers;

import app.checkers.CheckersPiece;
import app.chess.pieces.ChessPieceKind;
import app.ui.ImageManager;
import app.ui.board.GraphicalPiece;
import javafx.scene.paint.Color;

public class GraphicalCheckersPiece extends GraphicalPiece<CheckersPiece> {
    private final Color white, black;

    public GraphicalCheckersPiece(CheckersPiece piece, Color white, Color black) {
        super(ImageManager.getPieceImage(ChessPieceKind.KING), piece.getPlayer() == 0 ? white : black);
        this.white = white;
        this.black = black;
    }

    @Override
    public void update(CheckersPiece piece) {
        setImage(piece.isUpgraded() ? ImageManager.getPieceImage(ChessPieceKind.QUEEN) : ImageManager.getPieceImage(ChessPieceKind.KING));
        setColor(piece.getPlayer() == 0 ? white : black);
        if (!piece.isAlive()) disappear();
    }
}
