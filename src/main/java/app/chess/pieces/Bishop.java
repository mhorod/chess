package app.chess.pieces;

import app.chess.AbstractChessPiece;
import app.chess.Chess;
import app.chess.moves.ChessMove;
import app.chess.moves.NormalMove;
import app.chess.utils.Utils;
import app.core.game.Field;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends AbstractChessPiece {

    public Bishop(Field position, ChessPieceColor color) {
        super(position, ChessPieceKind.BISHOP, color);
    }

    public Bishop(Bishop toCopy) {
        super(toCopy);
    }

    @Override
    public List<ChessMove> getPotentialMoves() {
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();

        int initialRank = this.getPosition().rank();
        int initialFile = this.getPosition().file();
        //Not the cleanest implementation, but will do
        for (int rankVector = -1; rankVector <= 1; rankVector += 2) {
            for (int fileVector = -1; fileVector <= 1; fileVector += 2) {
                for (int howManyTimes = 1; howManyTimes <= Chess.SIZE; howManyTimes++) {
                    Field potentialField = new Field(initialRank + rankVector * howManyTimes,
                                                     initialFile + fileVector * howManyTimes);
                    if (Utils.fieldIsValid(potentialField)) {
                        potentialMoves.add(new NormalMove(this, potentialField));
                    }
                }
            }
        }
        return potentialMoves;
    }
}
