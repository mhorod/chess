package app.chess.pieces;

import app.chess.*;
import app.chess.moves.*;
import app.chess.utils.*;
import app.core.game.*;

import java.util.*;

public class Pawn extends AbstractChessPiece {
    private boolean movedBy2RanksRecently = false;

    public Pawn(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    public Pawn(Pawn toCopy) {
        super(toCopy);
        overwriteState(toCopy);
    }

    @Override
    public void overwriteState(AbstractChessPiece toCopy) {
        super.overwriteState(toCopy);
        movedBy2RanksRecently = ((Pawn) toCopy).movedBy2RanksRecently;
    }

    @Override
    public ChessPieceKind getKind() {
        return ChessPieceKind.PAWN;
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
