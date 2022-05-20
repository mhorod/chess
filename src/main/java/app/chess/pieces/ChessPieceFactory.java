package app.chess.pieces;

import app.chess.*;
import app.core.game.*;

/**
 * Factory to easily create piece from kind and color
 */
public final class ChessPieceFactory {
    private ChessPieceFactory() {
    }

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
