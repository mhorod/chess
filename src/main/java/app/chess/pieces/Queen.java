package app.chess.pieces;

import app.chess.AbstractChessPiece;
import app.chess.moves.ChessMove;
import app.chess.moves.NormalMove;
import app.core.game.Field;

import java.util.ArrayList;
import java.util.List;

public class Queen extends AbstractChessPiece {

    public Queen(Field position, ChessPieceColor color) {
        super(position, ChessPieceKind.QUEEN, color);
    }

    public Queen(Queen from) {
        super(from);
    }


    @Override
    public List<ChessMove> getPotentialMoves() {
        //Queen can move like a Rook and Bishop, somewhat standing on the same place
        //This means that there is no sense in writing this piece's logic, we might just create a fake rook and fake bishop with the same Field and then basically merge their results

        var fakeRook = new Rook(this.getPosition(), color);
        var fakeBishop = new Bishop(this.getPosition(), color);

        List<ChessMove> fakePotentialMoves = fakeRook.getPotentialMoves();
        fakePotentialMoves.addAll(fakeBishop.getPotentialMoves());

        //We cannot return above list right away, because it contains references to fake pieces
        //Thus, we will create a new one with correct reference to our piece

        ArrayList<ChessMove> potentialMoves = new ArrayList<>();

        for (ChessMove fakePotentialMove : fakePotentialMoves) {
            ChessMove legitimatePotentialMove = new NormalMove(this, fakePotentialMove.getField());
            potentialMoves.add(legitimatePotentialMove);
        }

        return potentialMoves;
    }
}
