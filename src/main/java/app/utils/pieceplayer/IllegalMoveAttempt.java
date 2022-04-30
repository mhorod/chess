package app.utils.pieceplayer;

/**
 * Thrown when an illegal move is attempted, e.g. moving piece that belongs to other player, or breaking game rules
 */
@SuppressWarnings("serial")
public class IllegalMoveAttempt extends RuntimeException {
    public IllegalMoveAttempt() {
    }

    public IllegalMoveAttempt(String message) {
        super(message);
    }
}
