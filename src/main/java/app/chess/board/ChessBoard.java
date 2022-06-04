package app.chess.board;

import app.chess.AbstractChessPiece;
import app.chess.pieces.ChessPieceColor;
import app.chess.pieces.ChessPieceFactory;
import app.chess.pieces.ChessPieceKind;
import app.chess.san.ChessField;
import app.core.game.Field;

/**
 * Utility class to simplify chessboard creation
 */
public class ChessBoard {
    protected int size;
    AbstractChessPiece[][] pieces;

    public ChessBoard(int size) {
        pieces = new AbstractChessPiece[size][size];
    }

    /**
     * Create and put a single chess piece on the board
     *
     * @param field target position
     * @param kind kind of piece
     * @param color black or white
     */
    public final void put(Field field, ChessPieceKind kind, ChessPieceColor color) {
        pieces[field.rank()][field.file()] = ChessPieceFactory.newPiece(field, kind, color);
    }

    public final void put(String fieldCode, ChessPieceKind kind, ChessPieceColor color) {
        Field field = ChessField.fromString(fieldCode);
        pieces[field.rank()][field.file()] = ChessPieceFactory.newPiece(field, kind, color);
    }

    public AbstractChessPiece[][] getPieces() {
        return pieces;
    }
}
