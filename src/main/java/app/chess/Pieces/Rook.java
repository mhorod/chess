package app.chess;

import app.core.game.*;

public class Rook extends ChessPiece{
    Rook(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    @Override
    ChessPieceKind getKind() {
        return ChessPieceKind.ROOK;
    }
}
