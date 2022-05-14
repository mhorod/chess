package app.chess.rules.ruleset;

import app.chess.*;
import app.chess.moves.*;
import app.chess.utils.*;
import app.core.game.*;

public class ThereCanBeNothingBetweenKingAndRookDuringCastling extends CastlingRules{

    @Override
    public boolean validate(ChessMove move, ChessPiece[][] board) {
        if(!canBeAppliedTo(move)){
            return true;
        }

        Field whereRookIs = Utils.getRookPositionBasedOnCastling((Castle) move, board);
        return Utils.roadNotObstructed(move.getPiece().getPosition(),whereRookIs,board);
    }
}
