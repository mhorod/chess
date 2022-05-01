package app.mock;

import app.core.interactor.*;

import java.util.*;

public class MockPlayer extends Player<MockMove, MockPiece> {

    public List<Map.Entry<Integer, MockMove>> receivedMoves = new ArrayList<>();

    @Override
    public void update(
            int player, MockMove move, List<MockPiece> changedPieces
    ) {
        receivedMoves.add(new AbstractMap.SimpleEntry<>(player, move));
    }
}
