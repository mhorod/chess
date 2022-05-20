package app.chess;

class StateManager {
    private int currentPlayer = 0;
    private boolean pendingPromotion = false;

    StateManager() {
    }

    StateManager(StateManager toCopy) {
        copyState(toCopy);
    }

    /**
     * Overrides the state of a given state manager.
     *
     * @param toCopy State manager we should copy
     */
    void copyState(StateManager toCopy) {
        currentPlayer = toCopy.currentPlayer;
        pendingPromotion = toCopy.pendingPromotion;
    }

    void switchCurrentPlayer() {
        if (pendingPromotion) {
            throw new StillWaitingForPromotion();
        }
        currentPlayer = currentPlayer == 0 ? 1 : 0;
    }


    void waitForPromotion() {
        pendingPromotion = true;
    }

    void markPromotionAsDone() {
        pendingPromotion = false;
    }

    boolean thereIsPromotionPending() {
        return pendingPromotion;
    }

    int getCurrentPlayer() {
        return currentPlayer;
    }

    static class StillWaitingForPromotion extends RuntimeException {
    }
}