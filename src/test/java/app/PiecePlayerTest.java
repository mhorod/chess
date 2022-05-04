package app;

import app.core.interactor.InteractiveGame;
import app.mock.MockGame;
import app.mock.MockInteractivePiece;
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

        var player = new StandalonePiecePlayer<>(game, 0);

        List<MockInteractivePiece> pieces = new ArrayList<>();

        player.connectPieces((p) -> {
            var piece = new MockInteractivePiece();
            pieces.add(piece);
            return piece;
        });


        var otherPieceMoves = pieces.get(1).getLegalMoves();
        assertThrows(IllegalMoveAttempt.class, () -> pieces.get(0).makeMove(otherPieceMoves.get(0)));
    }
}
