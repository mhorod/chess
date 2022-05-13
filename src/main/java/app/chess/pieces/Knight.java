package app.chess.pieces;

import app.chess.AbstractChessPiece;
import app.chess.Chess;
import app.chess.moves.ChessMove;
import app.chess.moves.NormalMove;
import app.core.game.Field;

import java.util.ArrayList;
import java.util.List;

public class Knight extends AbstractChessPiece {
    public Knight(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    @Override
    public ChessPieceKind getKind() {
        return ChessPieceKind.KNIGHT;
    }

    @Override
    public List<ChessMove> getPotentialMoves() {
        //Movements of this piece are quite interesting

        int vectorOne = 2;
        int vectorTwo = 1;

        int currentRank = this.getPosition().rank();
        int currentFile = this.getPosition().file();

        ArrayList<ChessMove> potentialMoves = new ArrayList<>();

        //We're moving by a vector [1,2], but we might also by a vector [2,1], so the first loop is going to account for that
        for (int i = 0; i < 2; i++) {
            //We would like to swap vectorOne and vectorTwo each time
            int temp = vectorOne;
            vectorOne = vectorTwo;
            vectorTwo = temp;

            //Now that we have swapping of the vector accounted for
            //We need to go through every possibility of "-" placement, e. g. [-2, 1], [2, -1], [-2, -1] etc.

            //We will use a bitmask for that
            for (int bitmask = 0; bitmask < 4; bitmask++) {
                //2 last bits tell us whether there is a "-" or "+"
                //If there is 0 then there is "+", else "-"

                // 2 -> 1 0
                // 1 -> 0 1

                int firstMultiplier = (bitmask & 2) == 0 ? 1 : -1;
                int secondMultiplier = (bitmask & 1) == 0 ? 1 : -1;

                Field potentialField = new Field(currentRank + vectorOne * firstMultiplier,
                                                 currentFile + vectorTwo * secondMultiplier);
                if (Chess.fieldIsValid(potentialField)) {
                    potentialMoves.add(new NormalMove(this.wrap(), potentialField));
                }
            }


        }
        return potentialMoves;
    }
}
