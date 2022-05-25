package app.chess.pieces;

import app.chess.*;
import app.chess.moves.*;
import app.chess.utils.*;
import app.core.game.*;

import java.util.*;

public class King extends AbstractChessPiece {

    boolean wasMovedBefore = false; //Do not confuse with wasMoved of ChessPiece, this is supposed to be changed one time
    //This field is going to be used to check whether king can castle

    public King(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    public King(King toCopy) {
        super(toCopy);
        overwriteState(toCopy);
        //This is not particularly effective as we're overwriting things 2 times
    }

    @Override
    public void overwriteState(AbstractChessPiece toCopy) {
        super.overwriteState(toCopy);
        wasMovedBefore = ((King) toCopy).wasMovedBefore;
    }

    @Override
    public ChessPieceKind getKind() {
        return ChessPieceKind.KING;
    }

    @Override
    public List<ChessMove> getPotentialMoves() {
        ArrayList<ChessMove> potentialMoves = new ArrayList<>();

        int currentRank = this.getPosition().rank();
        int currentFile = this.getPosition().file();

        //We will need two loops that account for every possible move king can make (except for the one where that piece stays in its place)
        for (int rankModifier = -1; rankModifier <= 1; rankModifier++) {
            for (int fileModifier = -1; fileModifier <= 1; fileModifier++) {
                if (rankModifier != 0 || fileModifier != 0) {
                    //Because move that doesn't move is not the greatest move, I'd say
                    Field potentialField = new Field(currentRank + rankModifier, currentFile + fileModifier);
                    if (Utils.fieldIsValid(potentialField)) {
                        potentialMoves.add(new NormalMove(this.wrap(), potentialField));
                    }
                }
            }
        }

        //However, we're not finished yet - there is castling

        //(unless we are, because the king's moved)
        if (!canParticipateInCastling()) {
            return potentialMoves;
        }

        //Castling, as far as king is concerned, is only moving 2 files to the left/right
        //Because there are only 2 possible castlings, I'm not going to write a loop for that

        //...just kidding!
        for (int multiplier = -1; multiplier <= 1; multiplier++) {
            if (multiplier != 0) {
                Field potentialField = new Field(currentRank, currentFile + 2 * multiplier);
                if (Utils.fieldIsValid(potentialField)) {
                    potentialMoves.add(new Castle(this.wrap(), potentialField));
                }
            }
        }

        return potentialMoves;
    }

    @Override
    public void move(Field newPosition) {
        wasMovedBefore = true;
        position = newPosition;
    }

    @Override
    public boolean canParticipateInCastling() {
        return !wasMovedBefore;
    }

}
