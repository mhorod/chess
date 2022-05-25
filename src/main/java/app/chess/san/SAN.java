package app.chess.san;

import app.chess.Chess;
import app.chess.ChessPiece;
import app.chess.moves.ChessMove;
import app.core.game.GameView;

import java.util.Arrays;
import java.util.List;

public class SAN {

    private SAN() {
    }

    public static String encodeMove(GameView<ChessMove, ChessPiece> chess, ChessMove move) {
        return null;
    }

    public static List<ChessMove> decodeMove(GameView<ChessMove, ChessPiece> chess, int player, String move) {
        return app.chess.san.SANDecoder.decodeMove(chess, player, move);
    }

    /**
     * Convert moves from SAN and apply them on the game alternating the player
     */
    public static void applyMoves(Chess chess, List<String> moves, int firstPlayer) {
        int player = firstPlayer;
        for (var move : moves) {
            for (var decoded : decodeMove(chess, player, move))
                chess.makeMove(player, decoded);
            player = 1 - player;
        }
    }
    public static void applyMoves(Chess chess, List<String> moves) {
        applyMoves(chess, moves, 0);
    }

    /**
     * Convert moves from a single SAN sequence and apply them on the game
     */
    public static void applyMoves(Chess chess, String moveSequence) {
        applyMoves(chess, splitMoveSequence(moveSequence), 0);
    }

    /**
     * Convert moves from a single SAN sequence and apply them on the game
     */
    public static void applyMoves(Chess chess, String moveSequence, int firstPlayer) {
        applyMoves(chess, splitMoveSequence(moveSequence), firstPlayer);
    }

    /**
     * Split move sequence into individual moves
     * <p>
     * e.g. 1. e4 e5 2. Nf3 Nc6 -> [e4, e5, Nf3, Nf6]
     */
    public static List<String> splitMoveSequence(String moveSequence) {
        return Arrays.stream(moveSequence.split("(\\d\\.)"))
                     .flatMap(m -> Arrays.stream(m.split(" ")))
                     .map(String::strip)
                     .filter(m -> m.length() >= 2)
                     .toList();
    }

    public static class InvalidSANMove extends RuntimeException {
        public InvalidSANMove() {
        }

        public InvalidSANMove(String message) {
            super(message);
        }
    }
}
