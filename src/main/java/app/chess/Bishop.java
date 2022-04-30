package app.chess;

import app.core.game.*;

public class Bishop extends ChessPiece{

    Bishop(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    @Override
    public ChessPieceKind getKind() {
        return ChessPieceKind.BISHOP;
    }
}
