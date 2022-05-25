package app.mock;

import app.core.game.Field;
import app.core.game.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockGame implements Game<MockMove, MockPiece> {

    List<MockPiece> pieces;
    Map<Integer, List<MockPiece>> playerPieces;

    public MockGame() {
        pieces = new ArrayList<>();
        playerPieces = new HashMap<>();
        playerPieces.put(0, new ArrayList<>());
        playerPieces.put(1, new ArrayList<>());

        for (var i = 0; i < 4; i++) {
            var piece = new MockPiece(i % 2);
            pieces.add(piece);
            playerPieces.get(i % 2).add(piece);
        }
    }

    @Override
    public List<MockPiece> getPieces(int player) {
        return playerPieces.get(player);
    }

    @Override
    public List<MockPiece> getAllPieces() {
        return pieces;
    }

    @Override
    public List<MockMove> getLegalMoves(int player) {
        var result = new ArrayList<MockMove>();
        for (var piece : playerPieces.get(player))
            result.add(new MockPieceMove(piece, new Field(0, 0)));
        return result;
    }

    @Override
    public List<MockMove> getLegalMoves(int player, MockPiece piece) {
        var result = new ArrayList<MockMove>();
        result.add(new MockPieceMove(piece, new Field(0, 0)));
        return result;
    }

    @Override
    public List<MockPiece> makeMove(int player, MockMove move) {
        return new ArrayList<>();
    }

    @Override
    public int getPlayerCount() {
        return 2;
    }
}