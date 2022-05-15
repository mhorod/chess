package app.chess.rules.ruleset;

import app.chess.ChessBoard;
import app.chess.ChessPiece;
import app.chess.moves.ChessMove;
import app.chess.rules.Rule;
import app.chess.utils.Utils;

public class YourKingCannotBeCheckedAfterYourMove implements Rule {
    @Override
    public boolean canBeAppliedTo(ChessMove move) {
        return true;
    }

    @Override
    public boolean validate(ChessMove move, ChessPiece[][] board) {
        //Temporarily modifies the board and checks if something went wrong
        //Please note that it doesn't modify anything at the end of the day

        var wasThereBefore = ChessBoard.putPieceOnBoard(move.getPiece(), move.getField(), board);
        ChessBoard.putPieceOnBoard(null, move.getPiece().getPosition(), board);

        try {
            return Utils.kingIsSafe(move.getPiece().getPlayer(), board);
        } finally {
            var goBack = ChessBoard.putPieceOnBoard(wasThereBefore, move.getField(), board);
            if (ChessBoard.putPieceOnBoard(goBack, goBack.getPosition(), board) != null) {
                System.err.println(goBack);
                throw new ChessBoard.BoardDiscrepancy(); //It shouldn't happen, but when it does something went absolutely and terribly wrong
            }
        }
    }
}
