package app.minesweeper;

import app.core.game.Field;
import app.core.game.Game;

import java.util.*;

import static app.minesweeper.MinesweeperPieceKind.Type.*;

public class Minesweeper implements Game<MinesweeperMove, MinesweeperPiece> {

    Map<Field, MinesweeperPiece> pieces = new HashMap<>();
    Set<Field> mines = new HashSet<>();

    public Minesweeper() {
        for (int i = 1; i < 9; i++)
            for (int j = 1; j < 9; j++)
                pieces.put(new Field(i, j),
                           new MinesweeperPiece(new Field(i, j), new MinesweeperPieceKind(COVERED_EMPTY)));
    }

    @Override
    public List<MinesweeperPiece> makeMove(int player, MinesweeperMove move) {
        var piece = move.changed;
        if (move.getPiece().kind.type == SHOVEL)
            return uncover(piece);
        else
            return switchFlag(piece);
    }

    private List<MinesweeperPiece> switchFlag(MinesweeperPiece piece) {
        if (piece.kind.type == FLAG)
            piece.kind.type = COVERED_EMPTY;
        else
            piece.kind.type = FLAG;
        return List.of(piece);
    }

    List<MinesweeperPiece> uncover(MinesweeperPiece piece) {
        return List.of(piece);
    }

    List<MinesweeperPiece> surrounding(MinesweeperPiece piece) {
        int i = piece.getPosition().rank();
        int j = piece.getPosition().file();
        List<MinesweeperPiece> result = new ArrayList<>();
        for (int di = -1; di < 2; di++)
            for (int dj = -1; dj < 2; dj++) {
                if (di == 0 && dj == 0)
                    continue;
                if (i + di <= 0 || i + di >= 9 || j + dj <= 0 || j + dj >= 9)
                    result.add(pieces.get(new Field(i + di, j + dj)));
            }
        return result;
    }

    int minesAround(MinesweeperPiece piece) {
        return (int) surrounding(piece).stream().filter(p -> mines.contains(p.getPosition())).count();
    }

    @Override
    public List<MinesweeperPiece> getPieces(int player) {
        return getAllPieces();
    }

    @Override
    public List<MinesweeperPiece> getAllPieces() {
        return pieces.values().stream().toList();
    }

    @Override
    public List<MinesweeperMove> getLegalMoves(int player) {
        return pieces.values()
                     .stream()
                     .filter(MinesweeperPiece::isCovered)
                     .flatMap(p -> getLegalMoves(0, p).stream())
                     .toList();
    }

    @Override
    public List<MinesweeperMove> getLegalMoves(int player, MinesweeperPiece piece) {
        if (!piece.isCovered())
            return List.of();
        else {
            var flagPiece = new MinesweeperPiece(piece.position, new MinesweeperPieceKind(FLAG));
            var flag = new MinesweeperMove(piece, flagPiece);

            var shovelPiece = new MinesweeperPiece(piece.getPosition(), new MinesweeperPieceKind(SHOVEL));
            var shovel = new MinesweeperMove(piece, shovelPiece);

            return List.of(flag, shovel);
        }
    }

    @Override
    public int getPlayerCount() {
        return 1; // This is a game for one player
    }
}
