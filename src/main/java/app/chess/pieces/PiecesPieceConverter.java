package app.chess.pieces;

import app.chess.*;

public class PiecesPieceConverter extends ChessPieceConverter {
    private PiecesPieceConverter() {
    }

    static AbstractChessPiece convert(ChessPiece toConvert) {
        return fakeConvert(toConvert);
    }

}
