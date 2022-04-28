package app.mock;

import app.core.game.*;
import app.core.game.moves.*;

public class MockPieceMove extends PieceMove<MockPiece> implements MockMove {
    protected MockPieceMove(MockPiece piece, Field field) {
        super(piece, field);
    }
}
