package app.chess.rules.ruleset;

import app.chess.ChessBoard;
import app.chess.ChessPiece;
import app.chess.moves.ChessMove;
import app.chess.rules.Rule;

public class FriendlyFireIsDisallowed implements Rule {

    @Override
    public boolean canBeAppliedTo(ChessMove move) {
        return true;
    }

    @Override
    public boolean validate(ChessMove move, ChessPiece[][] board) {
        var alreadyThere = ChessBoard.getPieceByField(move.getField(), board);
        if (alreadyThere == null)
            return true;
        else
            return alreadyThere.getPlayer() != move.getPiece().getPlayer();
    }
}
