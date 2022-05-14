package app.chess.rules.ruleset;

import app.chess.*;

public class RulesetPieceConverter extends ChessPieceConverter {
    private RulesetPieceConverter(){}

    static AbstractChessPiece convert(ChessPiece toConvert){
        return fakeConvert(toConvert);
    }
}
