package app.chess.san;

import app.core.game.Field;

/**
 * Utility class for operations on Field that are related to chess
 */
public class ChessField {
    private ChessField() {
    }

    /**
     * Converts field to string in standard chess notation
     *
     * @return encoded field, letters are lowercase
     */
    public static String toString(Field field) {
        return "" + fileToChar(field.file()) + rankToChar(field.rank());
    }

    public static char fileToChar(int file) {
        return (char) ('a' + (file - 1));
    }

    public static int charToFile(char c) {
        return c - 'a' + 1;
    }

    public static int charToRank(char c) {
        return c - '1' + 1;
    }

    public static char rankToChar(int rank) {
        return (char) ('1' + (rank - 1));
    }

    public static Field fromString(String string) {
        var file = charToFile(string.charAt(0));
        var rank = charToRank(string.charAt(1));
        return new Field(rank, file);
    }

    public static boolean isRank(char c) {
        return '1' <= c && c <= '8';
    }

    public static boolean isFile(char c) {
        return 'a' <= c && c <= 'h';
    }
}
