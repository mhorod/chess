package app.chess;

import app.core.game.Piece;

public interface ChessPiece extends Piece {
    boolean wasMoved();
    ChessPieceKind getKind();
}
