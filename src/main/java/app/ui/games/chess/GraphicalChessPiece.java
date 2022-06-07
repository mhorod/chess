package app.ui.games.chess;

import app.chess.ChessPiece;
import app.ui.board.GraphicalPiece;
import javafx.scene.paint.Color;

public class GraphicalChessPiece extends GraphicalPiece<ChessPiece> {
    private final Color white, black;

    public GraphicalChessPiece(ChessPiece piece, Color white, Color black) {
        super(ChessImages.getPieceImage(piece.getKind()), piece.getPlayer() == 0 ? white : black);
        this.white = white;
        this.black = black;
    }

    @Override
    public void update(ChessPiece piece) {
        setImage(ChessImages.getPieceImage(piece.getKind()));
        setColor(piece.getPlayer() == 0 ? white : black);
        if (!piece.isAlive())
            disappear();
    }
}
