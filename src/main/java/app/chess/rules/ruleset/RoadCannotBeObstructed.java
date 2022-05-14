package app.chess.rules.ruleset;

import app.chess.ChessPiece;
import app.chess.moves.Castle;
import app.chess.moves.ChessMove;
import app.chess.pieces.ChessPieceKind;
import app.chess.rules.Rule;
import app.chess.utils.Utils;

public class RoadCannotBeObstructed implements Rule {
    @Override
    public boolean canBeAppliedTo(ChessMove move) {
        //Knights don't care
        if (move.getClass() == Castle.class)
            return false;
        else
            return move.getPiece().getKind() != ChessPieceKind.KNIGHT;
    }

    @Override
    public boolean validate(ChessMove move, ChessPiece[][] board) {

        if (!canBeAppliedTo(move))
            return true;

        return Utils.roadNotObstructed(move.getPiece().getPosition(), move.getField(), board);
    }
}
