package app.minesweeper;

import app.core.game.Field;
import app.core.game.Piece;

import java.util.Objects;

public class MinesweeperPiece implements Piece {
    Field position;
    boolean isAlive = true;
    MinesweeperPieceKind kind;

    // fake piece is put as a choice in the move
    // fake pieces hash differently, so they appear in consistent order
    // in the ui PiecePicker
    boolean isFake = false;

    public MinesweeperPiece(Field position, MinesweeperPieceKind kind) {
        this.position = position;
        this.kind = kind;
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
        return 0;
    }

    public MinesweeperPieceKind getKind() {
        return kind;
    }

    public boolean isCovered() {
        return kind.isCovered();
    }

    public boolean isFlagged() {
        return kind.type == MinesweeperPieceKind.Type.FLAG;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        else if (other.getClass() != getClass())
            return false;
        else
            return hashCode() == Objects.hashCode(other);
    }

    @Override
    public int hashCode() {
        if (isFake) {
            return kind.type.hashCode();
        } else
            return Objects.hash(position, kind);
    }
}
