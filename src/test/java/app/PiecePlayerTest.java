package app;

import app.core.interactor.InteractiveGame;
import app.core.interactor.Player;
import app.mock.MockGame;
import app.mock.MockInteractivePiece;
import app.mock.MockMove;
import app.mock.MockPiece;
import app.utils.pieceplayer.IllegalMoveAttempt;
import app.utils.pieceplayer.StandalonePiecePlayer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;

public class PiecePlayerTest {

    @Test
    public void cannot_make_move_as_another_piece() {
        var game = new InteractiveGame<>(new MockGame());
        var p = new Player<MockMove, MockPiece>();

        game.connectPlayer(0, p);
        var player = new StandalonePiecePlayer<>(p);
        game.connectSpectator(player);

        List<MockInteractivePiece> pieces = new ArrayList<>();

        player.connectPieces(() -> {
            var piece = new MockInteractivePiece();
            pieces.add(piece);
            return piece;
        });


        var otherPieceMoves = pieces.get(1).getLegalMoves();
        assertThrows(IllegalMoveAttempt.class, () -> pieces.get(0).makeMove(otherPieceMoves.get(0)));
    }
}
