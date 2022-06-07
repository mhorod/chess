package app.chess.rules;

import app.chess.AbstractChessPiece;
import app.chess.ChessPiece;
import app.chess.ChessPieceUnwrapper;

final class RulesPieceUnwrapper extends ChessPieceUnwrapper {
    private RulesPieceUnwrapper() {
    }

    static AbstractChessPiece unwrap(ChessPiece toConvert) {
        return baseUnwrap(toConvert);
    }
}
