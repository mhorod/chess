package app.chess.pieces;

import app.chess.AbstractChessPiece;
import app.chess.ChessPiece;
import app.core.game.Field;

/**
 * Factory to easily create piece from kind and color
 */
public final class ChessPieceFactory {
    private ChessPieceFactory() {
    }

    public static AbstractChessPiece newPiece(
            Field field, ChessPieceKind kind, ChessPieceColor color
    ) {
        return switch (kind) {
            case PAWN -> new Pawn(field, color);
            case KNIGHT -> new Knight(field, color);
            case BISHOP -> new Bishop(field, color);
            case ROOK -> new Rook(field, color);
            case QUEEN -> new Queen(field, color);
            case KING -> new King(field, color);
        };
    }

    public static AbstractChessPiece copyPiece(ChessPiece piece) {
        if (piece == null) {
            return null;
        }
        return switch (piece.getKind()) {
            case PAWN -> new Pawn((Pawn) piece);
            case KNIGHT -> new Knight((Knight) piece);
            case BISHOP -> new Bishop((Bishop) piece);
            case ROOK -> new Rook((Rook) piece);
            case QUEEN -> new Queen((Queen) piece);
            case KING -> new King((King) piece);
        };
    }
}
