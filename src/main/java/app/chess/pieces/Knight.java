package app.chess;

import app.chess.pieces.*;
import app.core.game.*;

import java.util.*;

public class Knight extends ChessPiece {
    Knight(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    @Override
    public ChessPieceKind getKind() {
        return ChessPieceKind.KNIGHT;
    }

    @Override
    public List<ChessMove> getPotentialMoves() {
        return Collections.emptyList();
    }
}
