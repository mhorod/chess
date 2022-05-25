package app.minesweeper;


import app.core.game.moves.PiecePick;

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
