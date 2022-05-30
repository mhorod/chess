package app.core.game;

public record Field(int rank, int file) {
    public String toString() {
        return (char) ('A' + file() - 1) + "" + rank();
    }
}
