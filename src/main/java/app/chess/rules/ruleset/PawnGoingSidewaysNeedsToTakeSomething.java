package app.chess.rules.ruleset;

import app.chess.ChessPiece;
import app.chess.moves.ChessMove;
import app.chess.pieces.ChessPieceKind;
import app.chess.rules.Rule;

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
        if (!canBeAppliedTo(move))
            return true;

        int newRank = move.getField().rank();
        int newFile = move.getField().file();

        var wasThereBefore = RulesetPieceUnwrapper.convert(board[newRank][newFile]);

        if (wasThereBefore == null) {
            //Perhaps en passant is possible
            //If not, we'll return false
            wasThereBefore = RulesetPieceUnwrapper.convert(
                    board[move.getPiece().getPlayer() == 0 ? newRank - 1 : newRank + 1][newFile]);
            if (wasThereBefore == null || !wasThereBefore.enPassantable() || wasThereBefore.getPlayer() == move.getPiece()
                                                                                                               .getPlayer()) {
                return false;
            }
        } else if (wasThereBefore.getPlayer() == move.getPiece().getPlayer())
            //somebody's here
            return false;

        return true;
    }
}
