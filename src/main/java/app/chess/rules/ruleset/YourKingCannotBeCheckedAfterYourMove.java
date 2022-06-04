package app.chess.rules.ruleset;

import app.chess.AbstractChessPiece;
import app.chess.moves.ChessMove;
import app.chess.rules.Rule;
import app.chess.utils.Utils;

public class YourKingCannotBeCheckedAfterYourMove implements Rule {
    @Override
    public boolean canBeAppliedTo(ChessMove move) {
        return true;
    }

    @Override
    public boolean validate(ChessMove move, AbstractChessPiece[][] board) {
        //Temporarily modifies the board and checks if something went wrong
        //Please note that it doesn't modify anything at the end of the day

        var wasThereBefore = Utils.putPieceOnBoard(move.getPiece(), move.getField(), board);
        Utils.putPieceOnBoard(null, move.getPiece().getPosition(), board);

        try {
            return Utils.kingIsSafe(move.getPiece().getPlayer(), board);
        } finally {
            var goBack = Utils.putPieceOnBoard(wasThereBefore, move.getField(), board);
            if (Utils.putPieceOnBoard(goBack, goBack.getPosition(), board) != null) {
                System.err.println(goBack);
                throw new Utils.BoardDiscrepancy();
                //It shouldn't happen, but when it does something went absolutely and terribly wrong
                //You might argue that throwing an exception inside finally block is a bad idea
                //I would normally agree, but seriously though, if THIS condition happens the whole code does undefined behaviour
            }
        }
    }
}
