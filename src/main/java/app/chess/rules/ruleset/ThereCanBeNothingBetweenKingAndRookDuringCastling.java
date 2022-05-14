package app.chess.rules.ruleset;

import app.chess.ChessPiece;
import app.chess.moves.Castle;
import app.chess.moves.ChessMove;
import app.chess.utils.Utils;
import app.core.game.Field;

public class ThereCanBeNothingBetweenKingAndRookDuringCastling extends CastlingRules {

    @Override
    public boolean validate(ChessMove move, ChessPiece[][] board) {
        if (!canBeAppliedTo(move))
            return true;
        Field whereRookIs = Utils.getRookPositionBasedOnCastling((Castle) move);
        return Utils.roadNotObstructed(move.getPiece().getPosition(), whereRookIs, board);
    }
}
