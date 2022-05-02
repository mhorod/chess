package app.chess.pieces;

import app.chess.*;
import app.chess.moves.*;
import app.core.game.*;

import java.util.*;

public class King extends ChessPiece {

    public King(Field position, boolean isBlack) {
        super(position, isBlack);
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
        for(int rankModifier = -1; rankModifier <= 1; rankModifier++){
            for(int fileModifier = -1; fileModifier <= 1; fileModifier++){
                if(rankModifier != 0 || fileModifier != 0){
                    //Because move that doesn't move is not the greatest move, I'd say
                    Field potentialField = new Field(currentRank + rankModifier, currentFile + fileModifier);
                    if(Chess.fieldIsValid(potentialField)){
                        potentialMoves.add(new NormalMove(this,potentialField));
                    }
                }
            }
        }
        return potentialMoves;
    }
}
