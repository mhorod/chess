package app.chess.pieces;

import app.chess.*;
import app.chess.moves.*;
import app.core.game.*;

import java.util.*;

public class Queen extends ChessPiece {

    public Queen(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    @Override
    public ChessPieceKind getKind() {
        return ChessPieceKind.QUEEN;
    }

    @Override
    public List<ChessMove> getPotentialMoves() {
        //Queen can move like a Rook and Bishop, somewhat standing on the same place
        //This means that there is no sense in writing this piece's logic, we might just create a fake rook and fake bishop with the same Field and then basically merge their results

        ChessPiece fakeRook = new Rook(this.getPosition(),false); //We don't care about any other parameter than the position, because pieces don't care about interactions with others when returning potential moves
        ChessPiece fakeBishop = new Bishop(this.getPosition(), false);

        List<ChessMove> fakePotentialMoves = fakeRook.getPotentialMoves();
        fakePotentialMoves.addAll(fakeBishop.getPotentialMoves());

        //We cannot return above list right away, because it contains references to fake pieces
        //Thus, we will create a new one with correct reference to our piece

        ArrayList<ChessMove> potentialMoves = new ArrayList<>();

        for(ChessMove fakePotentialMove : fakePotentialMoves){
            ChessMove legitimatePotentialMove = new NormalMove(this, fakePotentialMove.getField());
            potentialMoves.add(legitimatePotentialMove);
        }
        
        return potentialMoves;
    }
}
