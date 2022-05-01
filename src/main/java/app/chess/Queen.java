package app.chess;

import app.core.game.*;

import java.util.*;

public class Queen extends ChessPiece{

    Queen(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    @Override
    public ChessPieceKind getKind() {
        return ChessPieceKind.QUEEN;
    }

    @Override
    public List<ChessMove> getPotentialMoves() {
        return null;
    }
}
