package app.chess.rules.ruleset;

import app.chess.*;
import app.chess.moves.*;
import app.chess.rules.*;
import app.chess.utils.*;

public class YourKingCannotBeCheckedAfterYourMove implements Rule {
    @Override
    public boolean canBeAppliedTo(ChessMove move) {
        return true;
    }

    @Override
    public boolean validate(ChessMove move, ChessPiece[][] board) {
        //Temporarily modifies the board and checks if something went wrong
        //Please note that it doesn't modify anything at the end of the day

        var wasThereBefore = Utils.putPieceOnBoard(move.getPiece(),move.getField(), board);
        Utils.putPieceOnBoard(null, move.getPiece().getPosition(), board);

        try{
            return Utils.kingIsSafe(move.getPiece().getPlayer(), board);
        }
        finally{
            var goBack = Utils.putPieceOnBoard(wasThereBefore, move.getField(), board);
            if(Utils.putPieceOnBoard(goBack, goBack.getPosition(), board) != null){
                System.err.println(goBack);
                throw new Utils.BoardDiscrepancy(); //It shouldn't happen, but when it does something went absolutely and terribly wrong
            }
        }
    }
}
