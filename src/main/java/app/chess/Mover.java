package app.chess;

import app.chess.moves.ChessMove;

import java.util.List;

interface Mover {
    List<AbstractChessPiece> makeMove(int player, ChessMove move, AbstractChessPiece[][] board, StateManager manager);

    /**
     * Checks if a move kills a piece (or pawn).
     *
     * @param move A valid move that is to be evaluated. If it's invalid, an UB will occur.
     * @param board A board on which the move should take place.
     * @return Null if there is no piece that's going to be taken, reference to said piece otherwise.
     */
    AbstractChessPiece getPieceKilledByMove(ChessMove move, AbstractChessPiece[][] board);
}
