package app.ai;

import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.interactor.Player;
import app.core.interactor.Spectator;

import java.util.List;
import java.util.Random;

public class DumbPlayer<M extends Move<P>, P extends Piece> extends Player<M, P> implements Spectator<M, P> {

    Random random = new Random();

    @Override
    public void update(int player, M move, List<P> changedPieces) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (Exception ignored) {
            }
            var moves = getLegalMoves();
            if (!moves.isEmpty()) {
                var choice = random.nextInt(moves.size());
                makeMove(moves.get(choice));
            }
        });
        thread.start();
    }
}
