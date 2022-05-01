package app.chess;

import app.core.game.*;

import java.util.*;

public class Bishop extends ChessPiece{

    Bishop(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    @Override
    public ChessPieceKind getKind() {
        return ChessPieceKind.BISHOP;
    }

    @Override
    public List<ChessMove> getPotentialMoves() {
        return null;
    }
}
