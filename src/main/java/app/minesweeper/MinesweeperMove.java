package app.minesweeper;


import app.core.game.moves.PiecePick;

/**
 * In minesweeper, we only pick a new state for a tile - we either uncover it or flag/unflag it
 */
public class MinesweeperMove extends PiecePick<MinesweeperPiece> {

    MinesweeperPiece changed;
    Kind kind;

    protected MinesweeperMove(MinesweeperPiece changed, MinesweeperPiece pick) {
        super(pick);
        this.changed = changed;
    }

    public enum Kind {
        UNCOVER, FLAG
    }
}
