package app;

import app.core.interactor.*;
import app.mock.*;
import app.utils.pieceplayer.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class PiecePlayerTest {

    @Test
    public void cannot_make_move_as_another_piece() {
        var game = new InteractiveGame<>(new MockGame());
        var player = new PiecePlayer<MockMove, MockPiece>();

        game.connectPlayer(0, player);

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
