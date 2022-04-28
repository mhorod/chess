package app.mock;

import app.core.game.moves.*;

public class EmptyMove implements MockMove {
    @Override
    public void match(MoveMatcher<MockPiece> matcher) {

    }
}
