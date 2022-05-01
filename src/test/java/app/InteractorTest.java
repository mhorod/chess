package app;

import app.core.interactor.*;
import app.mock.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class InteractorTest {

    @Test
    public void connect_invalid_player_with_invalid_id_throws_an_exception() {
        var game = new InteractiveGame<>(new MockGame());
        assertThrows(IllegalArgumentException.class, () -> game.connectPlayer(-1, new MockPlayer()));
        assertThrows(IllegalArgumentException.class, () -> game.connectPlayer(3, new MockPlayer()));
    }

    @Test
    public void connecting_player_with_already_taken_id_throws_an_exception() {
        var game = new InteractiveGame<>(new MockGame());
        game.connectPlayer(0, new MockPlayer());
        assertThrows(IllegalArgumentException.class, () -> game.connectPlayer(0, new MockPlayer()));
    }

    @Test
    public void connecting_null_player_throws_null_pointer_exception() {
        var game = new InteractiveGame<>(new MockGame());
        assertThrows(NullPointerException.class, () -> game.connectPlayer(0, null));
    }

    @Test
    public void player_cannot_move_until_all_players_are_connected() {
        var game = new InteractiveGame<>(new MockGame());
        var first_player = new MockPlayer();
        game.connectPlayer(0, first_player);
        assertThrows(IllegalStateException.class, () -> first_player.makeMove(new EmptyMove()));
    }

    @Test
    public void spectators_and_players_receive_move_updates() {
        var game = new InteractiveGame<>(new MockGame());

        var first_player = new MockPlayer();
        var second_player = new MockPlayer();

        game.connectPlayer(0, first_player);
        game.connectPlayer(1, second_player);

        var first_spectator = new MockSpectator();
        var second_spectator = new MockSpectator();

        game.connectSpectator(first_spectator);
        game.connectSpectator(second_spectator);

        var m1 = new EmptyMove();
        var m2 = new EmptyMove();

        List<AbstractMap.SimpleEntry<Integer, MockMove>> expected = List.of(new AbstractMap.SimpleEntry<>(0, m1),
                                                                            new AbstractMap.SimpleEntry<>(1, m2));

        first_player.makeMove(m1);
        second_player.makeMove(m2);

        assertEquals(expected, first_spectator.receivedMoves);
        assertEquals(expected, second_spectator.receivedMoves);
        assertEquals(expected, first_player.receivedMoves);
        assertEquals(expected, second_player.receivedMoves);
    }

    @Test
    public void using_unconnected_player_throws_exceptions() {
        var player = new MockPlayer();
        assertThrows(UseOfUnconnectedPlayer.class, player::getPieces);
        assertThrows(UseOfUnconnectedPlayer.class, player::getLegalMoves);
        assertThrows(UseOfUnconnectedPlayer.class, () -> player.getLegalMoves(new MockPiece(0)));
        assertThrows(UseOfUnconnectedPlayer.class, () -> player.makeMove(new EmptyMove()));
    }
}
