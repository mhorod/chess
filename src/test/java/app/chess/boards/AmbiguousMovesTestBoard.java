package app.chess.boards;


import app.chess.board.StandardChessBoard;

import static app.chess.pieces.ChessPieceColor.WHITE;
import static app.chess.pieces.ChessPieceKind.*;

/**
 * Board to test moves that cannot be determined from kind and field
 */
public class AmbiguousMovesTestBoard extends StandardChessBoard {
    /*
     * 8   K        k
     * 7 R            R
     * 6
     * 5
     * 4
     * 3 Q
     * 2
     * 1 Q   Q         R
     *   a b c d e f g h
     */
    public AmbiguousMovesTestBoard() {
        super(false);
        put("a1", QUEEN, WHITE);
        put("a3", QUEEN, WHITE);
        put("c1", QUEEN, WHITE);
        put("a7", ROOK, WHITE);
        put("h7", ROOK, WHITE);
        put("h1", ROOK, WHITE);
        put("b8", KING, WHITE);
        put("g8", KING, WHITE);

    }
}
