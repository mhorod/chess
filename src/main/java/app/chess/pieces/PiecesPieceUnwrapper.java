package app.chess.pieces;

import app.chess.AbstractChessPiece;
import app.chess.ChessPiece;
import app.chess.ChessPieceUnwrapper;

public class PiecesPieceUnwrapper extends ChessPieceUnwrapper {
    private PiecesPieceUnwrapper() {
    }

    static AbstractChessPiece unwrap(ChessPiece toConvert) {
        return baseUnwrap(toConvert);
    }

}
