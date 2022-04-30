package app.chess;

import app.core.game.*;

public class ChessPawn extends ChessPiece {

    ChessPawn(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    @Override
    ChessPieceKind getKind() {
        return ChessPieceKind.PAWN;
    }
}
