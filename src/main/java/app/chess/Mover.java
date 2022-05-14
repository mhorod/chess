package app.chess;

import app.chess.moves.*;

import java.util.*;

interface Mover {
    public List<ChessPiece> makeMove(int player, ChessMove move, ChessPiece[][] board, StateManager manager);
}
