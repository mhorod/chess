package app.chess.boards;

import app.chess.board.StandardChessBoard;

import static app.chess.pieces.ChessPieceColor.WHITE;
import static app.chess.pieces.ChessPieceKind.PAWN;

/**
 * Board to test promotions
 */
public class PromotionTestBoard extends StandardChessBoard {
    /*
     * 8         k
     * 7 P
     * 6
     * 5
     * 4
     * 3
     * 2
     * 1         K
     *   a b c d e f g h
     */
    public PromotionTestBoard() {
        super(false);
        put("a7", PAWN, WHITE);
        putKings();
    }
}
