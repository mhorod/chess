package app.mock;

import app.core.interactor.Spectator;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockSpectator implements Spectator<MockMove, MockPiece> {

    public List<Map.Entry<Integer, MockMove>> receivedMoves = new ArrayList<>();

    @Override
    public void update(int player, MockMove move, List<MockPiece> changedPieces) {
        receivedMoves.add(new AbstractMap.SimpleEntry<>(player, move));
    }
}
