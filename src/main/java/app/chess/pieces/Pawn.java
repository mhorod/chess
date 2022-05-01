package app.chess.pieces;

import app.chess.moves.*;
import app.core.game.*;

import java.util.*;

public class Pawn extends ChessPiece {

    public Pawn(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    @Override
    public ChessPieceKind getKind() {
        return ChessPieceKind.PAWN;
    }

    @Override
    public List<ChessMove> getPotentialMoves() {
        return Collections.emptyList();
    }
}
