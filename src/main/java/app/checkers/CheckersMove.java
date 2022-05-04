package app.checkers;

import app.core.game.Field;
import app.core.game.moves.PieceMove;

public class CheckersMove extends PieceMove<CheckersPiece> {
    boolean captures;
    int dirX, dirY;

    CheckersMove(CheckersPiece piece, Field field, boolean captures, int dirX, int dirY) {
        super(piece, field);
        this.dirX = dirX;
        this.dirY = dirY;
        this.captures = captures;
    }

    Field getField() {
        return field;
    }
}
