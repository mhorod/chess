package app.chess;


import app.chess.pieces.ChessPieceColor;
import app.chess.pieces.ChessPieceKind;
import app.core.game.Piece;

/**
 * Extends Piece with chess-specific information
 */
public interface ChessPiece extends Piece {

    /**
     * @return kind of the piece - pawn, rook, etc.
     */
    ChessPieceKind getKind();

    /**
     * @return color of the piece - black or white
     */
    ChessPieceColor getColor();
}
