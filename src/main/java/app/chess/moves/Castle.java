package app.chess.moves;

import app.chess.ChessPiece;
import app.chess.pieces.ChessPieceKind;
import app.core.game.Field;
import app.core.game.moves.PieceMove;

public class Castle extends PieceMove<ChessPiece> implements ChessMove {
    public Castle(ChessPiece piece, Field field) {
        super(piece, field);

        if (piece.getKind() != ChessPieceKind.KING) {
            throw new OnlyKingCanCastle();
        }
    }

    static class OnlyKingCanCastle extends RuntimeException {
    }
}
