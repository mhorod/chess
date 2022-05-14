package app.chess.rules.ruleset;

import app.chess.*;
import app.chess.moves.*;
import app.chess.pieces.*;
import app.chess.rules.*;

public class PawnGoingForwardCantTakePieces implements Rule {
    @Override
    public boolean canBeAppliedTo(ChessMove move) {
        //Only applies to pawns that go forward, pretty much as it is in the title here
        if(move.getPiece().getKind() != ChessPieceKind.PAWN){
            return false;
        }

        int currentFile = move.getPiece().getPosition().file();
        int newFile = move.getField().file();

        if(currentFile != newFile){
            return false;
        }

        return true;
    }

    @Override
    public boolean validate(ChessMove move, ChessPiece[][] board) {
        if(!canBeAppliedTo(move)){
            return true;
        }
        else{
            return board[move.getField().rank()][move.getField().file()] == null;
        }
    }
}
