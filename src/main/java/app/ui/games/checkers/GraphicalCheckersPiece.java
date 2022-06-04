package app.ui.games.checkers;

import app.checkers.CheckersPiece;
import app.chess.pieces.ChessPieceKind;
import app.ui.board.GraphicalPiece;
import app.ui.games.chess.ChessImages;
import javafx.scene.paint.Color;

public class GraphicalCheckersPiece extends GraphicalPiece<CheckersPiece> {
    private final Color white, black;

    public GraphicalCheckersPiece(CheckersPiece piece, Color white, Color black) {
        super(ChessImages.getPieceImage(ChessPieceKind.KING), piece.getPlayer() == 0 ? white : black);
        this.white = white;
        this.black = black;
    }

    @Override
    public void update(CheckersPiece piece) {
        setImage(piece.isUpgraded() ? ChessImages.getPieceImage(ChessPieceKind.QUEEN) : ChessImages.getPieceImage(
                ChessPieceKind.KING));
        setColor(piece.getPlayer() == 0 ? white : black);
        if (!piece.isAlive())
            disappear();
    }
}
