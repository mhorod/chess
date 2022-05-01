package app.ui.board;

import app.core.game.Field;

import java.util.List;

public abstract class LogicalPiece {
    public Field getPosition() {
        return new Field(0, 0);
    }

    public List<Field> getLegalMoves() {
        return List.of(new Field(0, 0));
    }

    public void makeMove(Field field) {

    }

    abstract void update();
}
