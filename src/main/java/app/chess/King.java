package app.chess;

import app.core.game.*;

public class King extends ChessPiece {

    King(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    @Override
    public ChessPieceKind getKind() {
        return ChessPieceKind.KING;
    }
}
