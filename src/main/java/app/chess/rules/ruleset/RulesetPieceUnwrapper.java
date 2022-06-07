package app.chess.rules.ruleset;

import app.chess.AbstractChessPiece;
import app.chess.ChessPiece;
import app.chess.ChessPieceUnwrapper;

public class RulesetPieceUnwrapper extends ChessPieceUnwrapper {
    private RulesetPieceUnwrapper() {
    }

    static AbstractChessPiece convert(ChessPiece toConvert) {
        return baseUnwrap(toConvert);
    }
}
