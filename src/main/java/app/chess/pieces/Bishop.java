package app.chess.pieces;

import app.chess.Chess;
import app.chess.moves.ChessMove;
import app.chess.moves.NormalMove;
import app.core.game.Field;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends AbstractChessPiece {

    public Bishop(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    @Override
    public ChessPieceKind getKind() {
        return ChessPieceKind.BISHOP;
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
                    if (Chess.fieldIsValid(potentialField)) {
                        potentialMoves.add(new NormalMove(this, potentialField));
                    }
                }
            }
        }
        return potentialMoves;
    }
}
