package app.chess.pieces;

import app.chess.moves.*;
import app.core.game.*;

import java.util.*;

public class Knight extends ChessPiece {
    public Knight(Field position, boolean isBlack) {
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
