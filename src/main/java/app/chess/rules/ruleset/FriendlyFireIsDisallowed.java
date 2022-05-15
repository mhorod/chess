package app.chess.rules.ruleset;

import app.chess.*;
import app.chess.moves.*;
import app.chess.rules.*;

public class FriendlyFireIsDisallowed implements Rule {

    @Override
    public boolean canBeAppliedTo(ChessMove move) {
        return true;
    }

    @Override
    public boolean validate(ChessMove move, ChessPiece[][] board) {
        var alreadyThere = ChessBoard.getPieceByField(move.getField(), board);
        if (alreadyThere == null) {
            return true;
        } else {
            return alreadyThere.getPlayer() != move.getPiece().getPlayer();
        }
    }
}
