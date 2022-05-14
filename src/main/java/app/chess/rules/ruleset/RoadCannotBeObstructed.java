package app.chess.rules.ruleset;

import app.chess.*;
import app.chess.moves.*;
import app.chess.pieces.*;
import app.chess.rules.*;
import app.chess.utils.*;

public class RoadCannotBeObstructed implements Rule {
    @Override
    public boolean canBeAppliedTo(ChessMove move) {
        if (move.getClass() == Castle.class){
            //Castling has its own validation
            return false;
        }
        else if (move.getPiece().getKind() == ChessPieceKind.KNIGHT){
            //Knights don't care
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public boolean validate(ChessMove move, ChessPiece[][] board) {

        if(!canBeAppliedTo(move)){
            return true;
        }

        return Utils.roadNotObstructed(move.getPiece().getPosition(),move.getField(),board);
    }
}
