package app.chess.pieces;

import app.chess.moves.*;
import app.core.game.*;

import java.util.*;

public abstract class ChessPiece implements Piece {

    static class IncorrectPiecePlacement extends RuntimeException{}
    boolean wasMoved = false;
    boolean isBlack;
    boolean isAlive = true; //Defaults to true, who would like to create a dead chess piece anyways

    Field position;

    public ChessPiece(Field position, boolean isBlack) {

        this.position = position;

        this.isBlack = isBlack;

        int rank = position.rank();
        int file = position.file();

        if(rank > 8 || rank < 1 || file > 8 || file < 1) { //A quick validation to check whether arguments supplied here are correct
            throw new IncorrectPiecePlacement();
        }

    }

    @Override
    public Field getPosition(){
        return position;
    }

    @Override
    public boolean isAlive(){
        return isAlive;
    }
    @Override
    public int getPlayer(){
        return isBlack ? 1 : 0;
    }

    public boolean wasMoved(){
        return wasMoved;
    }

    public abstract ChessPieceKind getKind();

    /**
     *
     * @return A list of ChessMoves that are potentially valid (i.e. the caller needs to check if king's safety is ok and whether path to the given field isn't obstructed
     */
    public abstract List<ChessMove> getPotentialMoves();

}
