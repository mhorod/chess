package app.minesweeper;


import app.core.game.moves.PiecePick;

/**
 * In minesweeper, we only pick a new state for a tile - we either uncover it or flag/unflag it
 */
public class MinesweeperMove extends PiecePick<MinesweeperPiece> {
    protected MinesweeperMove(MinesweeperPiece piece, MinesweeperPiece pick) {
        super(piece, pick);
    }
}
