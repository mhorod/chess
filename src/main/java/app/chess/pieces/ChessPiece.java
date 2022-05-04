package app.chess.pieces;

import app.core.game.Piece;

/**
 * Extends Piece interface with methods specific for chess pieces
 */
public interface ChessPiece extends Piece {
    ChessPieceKind getKind();

    ChessPieceColor getColor();
}
