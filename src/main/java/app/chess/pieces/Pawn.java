package app.chess.pieces;

import app.chess.AbstractChessPiece;
import app.chess.moves.ChessMove;
import app.chess.moves.NormalMove;
import app.chess.utils.Utils;
import app.core.game.Field;

import java.util.ArrayList;
import java.util.List;

import static app.chess.pieces.ChessPieceKind.PAWN;

public class Pawn extends AbstractChessPiece {
    private boolean movedBy2RanksRecently = false;

    public Pawn(Field position, ChessPieceColor color) {
        super(position, PAWN, color);
    }

    public Pawn(Pawn from) {
        super(from);
        overwriteState(from);
    }

    @Override
    public void overwriteState(AbstractChessPiece from) {
        super.overwriteState(from);
        movedBy2RanksRecently = ((Pawn) from).movedBy2RanksRecently;
    }


    @Override
    public List<ChessMove> getPotentialMoves() {
        int player = this.getPlayer();

        int twoMovesForwardRank = player == 0 ? 2 : 7;
        int multiplier = player == 0 ? 1 : -1;

        ArrayList<ChessMove> potentialMoves = new ArrayList<>();

        //Pawn has maximally 4 possible moves: 2 forward, 1 to left, 1 to right

        int currentRank = this.getPosition().rank();
        int currentFile = this.getPosition().file();

        if (currentRank == twoMovesForwardRank) {
            Field whereToGo = new Field(currentRank + 2 * multiplier, currentFile);
            //no Field validation is needed in this case (because if you go 2 forward then, you are on a rank that guarantees that you won't get out of range)
            potentialMoves.add(new NormalMove(this.wrap(), whereToGo));
        }

        for (int fileModifier = -1; fileModifier <= 1; fileModifier++) {
            //Moves that go 1 forward
            Field whereToGo = new Field(currentRank + multiplier, currentFile + fileModifier);
            if (Utils.fieldIsValid(whereToGo)) {
                potentialMoves.add(new NormalMove(this.wrap(), whereToGo));
            }
        }
        return potentialMoves;
    }

    @Override
    public void move(Field newPosition) {
        if (Math.abs(position.rank() - newPosition.rank()) == 2) {
            movedBy2RanksRecently = true;
        }
        position = newPosition;
        wasMoved = true;
    }

    @Override
    public void resetMoved() {
        wasMoved = false;
        movedBy2RanksRecently = false;
    }

    @Override
    public boolean enPassantable() {
        if (movedBy2RanksRecently) {
            return true;
        } else
            return false;
    }
}
