package app.checkers;

import app.core.game.Field;
import app.core.game.Piece;

public class CheckersPiece implements Piece {

    private final int player;
    boolean isAlive = true;
    boolean isUpgraded = false;
    Field position;

    public CheckersPiece(Field position, int player) {
        this.position = position;
        this.player = player;
    }

    @Override
    public Field getPosition() {
        return position;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public int getPlayer() {
        return player;
    }

    public boolean isUpgraded() {
        return isUpgraded;
    }
}
