package app.chess;

import app.core.game.*;

public class Knight extends ChessPiece{
    Knight(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    @Override
    ChessPieceKind getKind() {
        return ChessPieceKind.KNIGHT;
    }
}
