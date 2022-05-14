package app.chess.rules.ruleset;

import app.chess.*;
import app.chess.moves.*;
import app.chess.pieces.*;
import app.chess.rules.*;

import static app.chess.pieces.ChessPieceKind.KING;

public class PawnGoingSidewaysNeedsToTakeSomething implements Rule {
    @Override
    public boolean canBeAppliedTo(ChessMove move) {

        if(move.getPiece().getKind() != ChessPieceKind.PAWN){
            return false;
        }

        int currentFile = move.getPiece().getPosition().file();
        int newFile = move.getField().file();

        if(currentFile == newFile){
            return false;
        }

        return true;
    }

    @Override
    public boolean validate(ChessMove move, ChessPiece[][] board) {

        if(!canBeAppliedTo(move)){
            return true;
        }

        int newRank = move.getField().rank();
        int newFile = move.getField().file();

        var wasThereBefore = RulesetPieceConverter.convert(board[newRank][newFile]);

        if (wasThereBefore == null) {
            //Perhaps en passant is possible
            //If not, we'll return false
            wasThereBefore = RulesetPieceConverter.convert(board[move.getPiece().getPlayer() == 0 ? newRank - 1 : newRank + 1][newFile]);
            if (wasThereBefore == null || !wasThereBefore.enPassantable() || wasThereBefore.getPlayer() == move.getPiece().getPlayer()) {
                return false;
            }

        } else {
            //somebody's here
            if (wasThereBefore.getPlayer() != move.getPiece().getPlayer()) {
                //There's enemy piece to be taken, so nothing wrong with that
            } else {
                return false;
            }
        }

        return true;
    }
}
