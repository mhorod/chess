package app.chess.pieces;

import app.chess.ChessPiece;
import app.core.game.Field;

/**
 * Factory to easily create piece from kind and color
 */
public final class ChessPieceFactory {
    private ChessPieceFactory(){}
    public static ChessPiece newPiece(
            Field field, ChessPieceKind kind, ChessPieceColor color
    ) {
        boolean isBlack = color == ChessPieceColor.BLACK;
        var piece = switch (kind) {
            case PAWN -> new Pawn(field, isBlack);
            case KNIGHT -> new Knight(field, isBlack);
            case BISHOP -> new Bishop(field, isBlack);
            case ROOK -> new Rook(field, isBlack);
            case QUEEN -> new Queen(field, isBlack);
            case KING -> new King(field, isBlack);
        };
        return piece.wrap();
    }
}
