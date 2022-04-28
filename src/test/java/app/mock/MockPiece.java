package app.mock;

import app.core.game.*;

public class MockPiece implements Piece {
    int id;
    int player;

    public MockPiece(int player) {
        this(0, player);
    }

    public MockPiece(int id, int player) {
        this.id = id;
        this.player = player;
    }

    public int getId() {
        return id;
    }

    @Override
    public Field getPosition() {
        return null;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public int getPlayer() {
        return player;
    }
}
