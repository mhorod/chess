package app.minesweeper;

import app.core.game.Field;
import app.core.game.Piece;

public class MinesweeperPiece implements Piece {
    Field position;
    boolean isAlive = true;
    MinesweeperPieceKind kind;
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
    public int hashCode() {
        if (isFake) {
            return kind.type.hashCode();
        } else
            return position.hashCode() ^ kind.hashCode();
    }
}
