package app.chess.rules.ruleset;

import app.chess.*;
import app.chess.moves.*;
import app.chess.pieces.*;
import app.chess.rules.*;
import app.chess.utils.*;

public class RoadCannotBeObstructed implements Rule {
    @Override
    public boolean canBeAppliedTo(ChessMove move) {
        //Knights don't care
        if (move.getClass() == Castle.class) {
            return false;
        } else {
            return move.getPiece().getKind() != ChessPieceKind.KNIGHT;
        }
    }

    @Override
    public boolean validate(ChessMove move, ChessPiece[][] board) {

        if (!canBeAppliedTo(move)) {
            return true;
        }

        return Utils.roadNotObstructed(move.getPiece().getPosition(), move.getField(), board);
    }
}
