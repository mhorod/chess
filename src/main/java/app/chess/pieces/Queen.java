package app.chess.pieces;

import app.chess.AbstractChessPiece;
import app.chess.moves.ChessMove;
import app.chess.moves.NormalMove;
import app.core.game.Field;

import java.util.ArrayList;
import java.util.List;

import static app.chess.pieces.ChessPieceKind.QUEEN;

public class Queen extends AbstractChessPiece {

    public Queen(Field position, ChessPieceColor color) {
        super(position, QUEEN, color);
    }

    public Queen(Queen from) {
        super(from);
    }


    @Override
    public List<ChessMove> getPotentialMoves() {
        //Queen can move like a Rook and Bishop, somewhat standing on the same place
        //This means that there is no sense in writing this piece's logic, we might just create a fake rook and fake bishop with the same Field and then basically merge their results

        //We don't care about any other parameter than the position, because pieces don't care about interactions with others when returning potential moves
        var fakeRook = new Rook(this.getPosition(), getColor());
        var fakeBishop = new Bishop(this.getPosition(), getColor());

        List<ChessMove> fakePotentialMoves = fakeRook.getPotentialMoves();
        fakePotentialMoves.addAll(fakeBishop.getPotentialMoves());

        //We cannot return above list right away, because it contains references to fake pieces
        //Thus, we will create a new one with correct reference to our piece

        ArrayList<ChessMove> potentialMoves = new ArrayList<>();

        for (ChessMove fakePotentialMove : fakePotentialMoves) {
            ChessMove legitimatePotentialMove = new NormalMove(this.wrap(), fakePotentialMove.getField());
            potentialMoves.add(legitimatePotentialMove);
        }

        return potentialMoves;
    }
}
