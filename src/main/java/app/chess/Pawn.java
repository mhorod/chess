package app.chess;

import app.core.game.*;

public class Pawn extends ChessPiece {

    Pawn(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    @Override
    public ChessPieceKind getKind() {
        return ChessPieceKind.PAWN;
    }
}
