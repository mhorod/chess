package app.chess.rules;

import app.chess.*;
import app.chess.moves.*;
import app.chess.utils.*;

public class RoadCannotBeObstructed implements  Rule{
    @Override
    public boolean validate(ChessMove move, ChessPiece[][] board) {
        return !Utils.roadNotObstructed(move.getPiece().getPosition(),move.getField(),board);
    }
}
