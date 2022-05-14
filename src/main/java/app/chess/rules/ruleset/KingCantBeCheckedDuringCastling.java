package app.chess.rules.ruleset;

import app.chess.ChessPiece;
import app.chess.moves.ChessMove;
import app.chess.utils.Utils;

public class KingCantBeCheckedDuringCastling extends CastlingRules {

    @Override
    public boolean validate(ChessMove move, ChessPiece[][] board) {
        if (!canBeAppliedTo(move))
            return true;
        else
            return Utils.kingIsSafe(move.getPiece().getPlayer(), board);
    }
}
