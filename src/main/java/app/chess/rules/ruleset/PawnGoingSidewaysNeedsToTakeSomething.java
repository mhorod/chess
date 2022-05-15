package app.chess.rules.ruleset;

import app.chess.*;
import app.chess.moves.*;
import app.chess.pieces.*;
import app.chess.rules.*;

public class PawnGoingSidewaysNeedsToTakeSomething implements Rule {
    @Override
    public boolean canBeAppliedTo(ChessMove move) {
        if (move.getPiece().getKind() != ChessPieceKind.PAWN) {
            return false;
        }

        int currentFile = move.getPiece().getPosition().file();
        int newFile = move.getField().file();

        return currentFile != newFile;
    }

    @Override
    public boolean validate(ChessMove move, ChessPiece[][] board) {
        if (!canBeAppliedTo(move)) {
            return true;
        }

        int newRank = move.getField().rank();
        int newFile = move.getField().file();

        var wasThereBefore = RulesetPieceConverter.convert(board[newRank][newFile]);

        //somebody's here
        if (wasThereBefore == null) {
            //Perhaps en passant is possible
            //If not, we'll return false
            wasThereBefore = RulesetPieceConverter.convert(
                    board[move.getPiece().getPlayer() == 0 ? newRank - 1 : newRank + 1][newFile]);
            return wasThereBefore != null && wasThereBefore.enPassantable() && wasThereBefore.getPlayer() != move.getPiece()
                                                                                                                 .getPlayer();
        } else {
            return wasThereBefore.getPlayer() != move.getPiece().getPlayer();
        }
    }
}
