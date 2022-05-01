package app.core.interactor;

/**
 * Thrown when Player methods are called without previous call of connectPlayer on InteractiveGame object
 */
@SuppressWarnings("serial")
public class UseOfUnconnectedPlayer extends RuntimeException {
}
