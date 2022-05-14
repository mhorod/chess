package app.chess;

class StateManager {
    private int currentPlayer = 0;
    private boolean pendingPromotion = false;

    void switchCurrentPlayer(){
        if(pendingPromotion){
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
    boolean thereIsPromotionPending(){
        return pendingPromotion;
    }
    int getCurrentPlayer(){
        return currentPlayer;
    }

    static class StillWaitingForPromotion extends RuntimeException{}
}