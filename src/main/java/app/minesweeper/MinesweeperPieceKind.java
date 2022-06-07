package app.minesweeper;

/**
 * Kind of minesweeper tile - type of tile + number of mines around
 * <p>
 * Tile can be covered or uncovered If it's covered then it can be either empty or flagged
 * <p>
 * If it's uncovered it can be either a number or a mine
 * <p>
 */
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

    /**
     * Type of tile - standard types in minesweeper and a shovel which is used as a choice for picking. Picking a shovel
     * is equivalent to uncovering the tile
     */
    public enum Type {
        COVERED_EMPTY, UNCOVERED_EMPTY, MINE, FLAG, NUMBER, SHOVEL
    }

}
