package app.chess.rules.ruleset;

import app.chess.*;
import app.chess.moves.*;
import app.chess.utils.*;

public class KingCantBeCheckedDuringCastling extends CastlingRules {

    @Override
    public boolean validate(ChessMove move, ChessPiece[][] board) {
        if (!canBeAppliedTo(move)) {
            return true;
        } else {
            return Utils.kingIsSafe(move.getPiece().getPlayer(), board);
        }
    }
}
