package app.chess;

import app.core.game.*;

public class Queen extends ChessPiece{

    Queen(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    @Override
    public ChessPieceKind getKind() {
        return ChessPieceKind.QUEEN;
    }
}
