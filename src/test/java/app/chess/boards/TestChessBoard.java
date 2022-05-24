package app.chess.boards;


import app.chess.board.StandardChessBoard;

/**
 * Board with no pawns to test figure moves
 */
public class TestChessBoard extends StandardChessBoard {
    /*
     * 8 r n b q k b n r
     * 7
     * 6
     * 5
     * 4
     * 3
     * 2
     * 1 R N B Q K B N R
     *   a b c d e f g h
     */
    public TestChessBoard() {
        super(false);
        putRooks();
        putKnights();
        putBishops();
        putKings();
        putQueens();
    }
}
