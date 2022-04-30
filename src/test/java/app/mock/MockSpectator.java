package app.mock;

import app.core.interactor.*;

import java.util.*;

public class MockSpectator implements Participant<MockMove, MockPiece> {

    public List<Map.Entry<Integer, MockMove>> receivedMoves = new ArrayList<>();

    @Override
    public void update(int player, MockMove move) {
        receivedMoves.add(new AbstractMap.SimpleEntry<>(player, move));
    }
}
