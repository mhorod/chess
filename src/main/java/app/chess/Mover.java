package app.chess;

import app.chess.moves.ChessMove;

import java.util.List;

interface Mover {
    List<ChessPiece> makeMove(int player, ChessMove move, ChessPiece[][] board, StateManager manager);
}
