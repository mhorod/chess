package app.minesweeper;

public class MinesweeperPieceKind {
    public Type type;
    public int number;

    MinesweeperPieceKind(Type type) {
        this.type = type;
    }

    MinesweeperPieceKind(int number) {
        this.number = number;
        this.type = Type.NUMBER;
    }

    public boolean isCovered() {
        return type == Type.COVERED_EMPTY || type == Type.FLAG;
    }

    public enum Type {
        COVERED_EMPTY, UNCOVERED_EMPTY, BOMB, FLAG, NUMBER, SHOVEL
    }

}
