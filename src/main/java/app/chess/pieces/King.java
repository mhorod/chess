package app.chess.pieces;

import app.chess.moves.*;
import app.core.game.*;

import java.util.*;

public class King extends ChessPiece {

    public King(Field position, boolean isBlack) {
        super(position, isBlack);
    }

    @Override
    public ChessPieceKind getKind() {
        return ChessPieceKind.KING;
    }

    @Override
    public List<ChessMove> getPotentialMoves() {
        return Collections.emptyList();
    }
}
