package app.chess;

import app.chess.pieces.ChessPieceColor;
import app.chess.pieces.ChessPieceFactory;
import app.chess.pieces.ChessPieceKind;
import app.core.game.Field;

/**
 * Utility class to simplify chessboard creation and operations on a chessboard
 */
public class ChessBoard {
    // Adding one here because Chess starts indexing from 1
    // This is a little uncommon, but in Chess such numeration is also starting from 1 and it will hopefully create fewer bugs if we stick to this convention
    public static final int SIZE = Chess.SIZE + 1;
    ChessPiece[][] pieces;

    public ChessBoard() {
        pieces = new ChessPiece[SIZE][SIZE];
    }

    public static boolean fieldIsValid(Field field) {
        return field.rank() < SIZE && field.file() < SIZE && field.rank() > 0 && field.file() > 0;
    }

    public static ChessPiece getPieceByField(Field field, ChessPiece[][] board) {
        return board[field.rank()][field.file()];
    }

    /**
     * Puts the piece on a given piece on board, WITHOUT changing any data about piece location (inside the piece).
     * Should be used with caution.
     *
     * @return Piece that was already on a given field
     */
    public static ChessPiece putPieceOnBoard(ChessPiece who, Field field, ChessPiece[][] board) {
        var wasThereBefore = getPieceByField(field, board);
        board[field.rank()][field.file()] = who;
        return wasThereBefore;
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

    public static class BoardDiscrepancy extends RuntimeException {
    }
}
