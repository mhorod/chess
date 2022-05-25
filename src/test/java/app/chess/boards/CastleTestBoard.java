package app.chess.boards;

import app.chess.board.StandardChessBoard;

/**
 * Board only with rooks and kings, to test castling moves
 */
public class CastleTestBoard extends StandardChessBoard {
    /*
     * 8 r       k     r
     * 7
     * 6
     * 5
     * 4
     * 3
     * 2
     * 1 R       K     R
     *   a b c d e f g h
     */
    public CastleTestBoard() {
        super(false);
        putRooks();
        putKings();
    }
}
