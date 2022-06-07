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

    public static ChessPiece newPiece(
            Field field, ChessPieceKind kind, ChessPieceColor color
    ) {
        var piece = switch (kind) {
            case PAWN -> new Pawn(field, color);
            case KNIGHT -> new Knight(field, color);
            case BISHOP -> new Bishop(field, color);
            case ROOK -> new Rook(field, color);
            case QUEEN -> new Queen(field, color);
            case KING -> new King(field, color);
        };
        return piece.wrap();
    }

    public static ChessPiece copyPiece(ChessPiece toCopy) {
        if (toCopy == null) {
            return null;
        }
        AbstractChessPiece unwrappedPiece = PiecesPieceConverter.convert(toCopy);
        AbstractChessPiece result = switch (unwrappedPiece.getKind()) {
            case PAWN -> new Pawn((Pawn) unwrappedPiece);
            case KNIGHT -> new Knight((Knight) unwrappedPiece);
            case BISHOP -> new Bishop((Bishop) unwrappedPiece);
            case ROOK -> new Rook((Rook) unwrappedPiece);
            case QUEEN -> new Queen((Queen) unwrappedPiece);
            case KING -> new King((King) unwrappedPiece);
        };
        return result.wrap();
    }
}
