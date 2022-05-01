package app.chess;

import app.core.game.*;

import java.util.*;

public class Rook extends ChessPiece{
    Rook(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    @Override
    public ChessPieceKind getKind() {
        return ChessPieceKind.ROOK;
    }

    @Override
    public List<ChessMove> getPotentialMoves() {
        final Field currentPosition = this.getPosition();

        //This is a rook, so it can move horizontally and vertically

        ArrayList<ChessMove> potentialMoves = new ArrayList<>();

        for(int i = 1; i <= Chess.SIZE; i++){
            Field fieldOnTheSameFile = new Field(i,currentPosition.file());
            Field fieldOnTheSameRank = new Field(currentPosition.rank(), i);

            ChessMove moveOnTheSameFile = new NormalMove(this,fieldOnTheSameFile);
            ChessMove moveOnTheSameRank = new NormalMove(this, fieldOnTheSameRank);

            potentialMoves.add(moveOnTheSameFile);
            potentialMoves.add(moveOnTheSameRank);
        }

        return potentialMoves;
    }
}
