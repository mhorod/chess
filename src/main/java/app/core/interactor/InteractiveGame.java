package app.core.interactor;

import app.core.game.*;
import app.core.game.moves.*;

import java.util.*;

/**
 * Wraps game allowing players to interact with each other and notifies participants about events
 */
public final class InteractiveGame<M extends Move<P>, P extends Piece, G extends Game<M, P>> implements Game<M, P> {
    G game;
    boolean[] isConnected;
    int connectedPlayers = 0;

    List<Participant<M, P>> participants;

    public InteractiveGame(G game) {
        this.game = game;
        isConnected = new boolean[game.getPlayerCount()];
        participants = new ArrayList<>();
    }

    /**
     * Connects the player and handles the controls
     *
     * @param playerId id of connected player
     * @param player player to be connected
     */
    public void connectPlayer(int playerId, Player<M, P> player) {
        if (player == null)
            throw new NullPointerException();
        else if (playerId < 0 || playerId > game.getPlayerCount())
            throw new IllegalArgumentException("player id should be in range [0, " + game.getPlayerCount() + ")");
        else if (isConnected[playerId])
            throw new IllegalArgumentException("player with this id has already connected");

        isConnected[playerId] = true;
        connectedPlayers++;
        player.player = playerId;
        player.game = this;
        participants.add(player);
    }

    /**
     * Connects spectator that listens to the events
     *
     * @param spectator spectator to be connected
     */
    public void connectSpectator(Participant<M, P> spectator) {
        participants.add(spectator);
    }

    @Override
    public List<P> getPieces(int player) {
        return game.getPieces(player);
    }

    @Override
    public List<P> getAllPieces() {
        return game.getAllPieces();
    }

    @Override
    public List<M> getLegalMoves(int player) {
        return game.getLegalMoves(player);
    }

    @Override
    public List<M> getLegalMoves(int player, P piece) {
        return game.getLegalMoves(player, piece);
    }

    @Override
    public List<P> makeMove(int player, M move) {
        if (connectedPlayers < game.getPlayerCount())
            throw new IllegalStateException("not all players are connected");

        var changedPieces = game.makeMove(player, move);
        for (var p : participants)
            p.update(player, move);
        return changedPieces;
    }

    @Override
    public int getPlayerCount() {
        return game.getPlayerCount();
    }
}
