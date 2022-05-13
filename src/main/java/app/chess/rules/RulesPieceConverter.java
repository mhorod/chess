package app.chess.rules;

import app.chess.*;

final class RulesPieceConverter extends ChessPieceConverter {
    private RulesPieceConverter(){}
    //That's how we won't violate the ISP.
    //I don't want to talk about it, it will just work this way.
    static AbstractChessPiece convert(ChessPiece toConvert){
        return fakeConvert(toConvert);
    }
}
