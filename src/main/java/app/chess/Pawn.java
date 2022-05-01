package app.chess;

import app.core.game.*;

import java.util.*;

public class Pawn extends ChessPiece {

    Pawn(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    @Override
    public ChessPieceKind getKind() {
        return ChessPieceKind.PAWN;
    }

    @Override
    public List<ChessMove> getPotentialMoves() {
        return null;
    }
}
