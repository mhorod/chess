package app.chess;

import app.core.game.Field;

import static app.chess.pieces.ChessPieceColor.BLACK;
import static app.chess.pieces.ChessPieceColor.WHITE;
import static app.chess.pieces.ChessPieceKind.*;

/**
 * Chessboard that initializes with standard chess layout
 */
public class ChessBoard extends Board {

    /*
     * 8 r n b q k b n r
     * 7 p p p p p p p p
     * 6
     * 5
     * 4
     * 3
     * 2 P P P P P P P P
     * 1 R N B Q K B N R
     *   1 2 3 4 5 6 7 8
     */

    public static final int WHITE_PAWN_RANK = 2;
    public static final int WHITE_FIGURE_RANK = 1;
    public static final int BLACK_PAWN_RANK = 7;
    public static final int BLACK_FIGURE_RANK = 8;

    public ChessBoard() {
        // Adding one here because Chess starts indexing from 1
        // This is a little uncommon, but in Chess such numeration is also starting from 1 and it will hopefully create fewer bugs if we stick to this convention
        super(Chess.SIZE + 1);
        putPawns();
        putRooks();
        putKnights();
        putBishops();
        putKings();
        putQueens();
    }

    private void putPawns() {
        for (int file = 1; file <= Chess.SIZE; file++) {
            put(new Field(WHITE_PAWN_RANK, file), PAWN, WHITE);
            put(new Field(BLACK_PAWN_RANK, file), PAWN, BLACK);
        }
    }

    private void putRooks() {
        put(new Field(WHITE_FIGURE_RANK, 1), ROOK, WHITE);
        put(new Field(WHITE_FIGURE_RANK, 8), ROOK, WHITE);

        put(new Field(BLACK_FIGURE_RANK, 1), ROOK, BLACK);
        put(new Field(BLACK_FIGURE_RANK, 8), ROOK, BLACK);
    }

    private void putKnights() {
        put(new Field(WHITE_FIGURE_RANK, 2), KNIGHT, WHITE);
        put(new Field(WHITE_FIGURE_RANK, 7), KNIGHT, WHITE);

        put(new Field(BLACK_FIGURE_RANK, 2), KNIGHT, BLACK);
        put(new Field(BLACK_FIGURE_RANK, 7), KNIGHT, BLACK);
    }

    private void putBishops() {
        put(new Field(WHITE_FIGURE_RANK, 3), BISHOP, WHITE);
        put(new Field(WHITE_FIGURE_RANK, 6), BISHOP, WHITE);

        put(new Field(BLACK_FIGURE_RANK, 3), BISHOP, BLACK);
        put(new Field(BLACK_FIGURE_RANK, 6), BISHOP, BLACK);
    }

    private void putQueens() {
        put(new Field(WHITE_FIGURE_RANK, 4), QUEEN, WHITE);
        put(new Field(BLACK_FIGURE_RANK, 4), QUEEN, BLACK);
    }

    private void putKings() {
        put(new Field(WHITE_FIGURE_RANK, 5), KING, WHITE);
        put(new Field(BLACK_FIGURE_RANK, 5), KING, BLACK);
    }
}