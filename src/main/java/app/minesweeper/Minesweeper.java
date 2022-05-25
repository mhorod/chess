package app.minesweeper;

import app.core.game.Field;
import app.core.game.Game;

import java.util.*;

import static app.minesweeper.Minesweeper.State.NONE;
import static app.minesweeper.Minesweeper.State.WON;
import static app.minesweeper.MinesweeperPieceKind.Type.*;

public class Minesweeper implements Game<MinesweeperMove, MinesweeperPiece> {

    private final int MINES = 10;
    boolean lost = false;

    Map<Field, MinesweeperPiece> pieces = new HashMap<>();
    Set<Field> mines = new HashSet<>();

    public Minesweeper() {
        for (int i = 1; i < 9; i++)
            for (int j = 1; j < 9; j++)
                pieces.put(new Field(i, j),
                           new MinesweeperPiece(new Field(i, j), new MinesweeperPieceKind(COVERED_EMPTY)));

        var random = new Random();
        for (int i = 0; i < MINES; i++) {
            var rank = random.nextInt(1, 8);
            var file = random.nextInt(1, 8);
            mines.add(new Field(rank, file));
        }
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

        if (mines.contains(piece.getPosition())) {
            piece.kind.type = MINE;
            lost = true;
        } else {
            uncoverSingle(piece);
            var around = minesAround(piece);
            if (around == 0)
                return uncoverZeros(piece);
        }
        return List.of(piece);
    }

    void uncoverSingle(MinesweeperPiece piece) {
        var around = minesAround(piece);
        if (around == 0)
            piece.isAlive = false;
        else {
            piece.kind.type = NUMBER;
            piece.kind.number = around;
        }
    }

    List<MinesweeperPiece> uncoverZeros(MinesweeperPiece piece) {
        // Traverses board with BFS algorithm revealing pieces that don't touch mines
        // and adjacent that do

        List<MinesweeperPiece> result = new ArrayList<>();
        Queue<MinesweeperPiece> q = new ArrayDeque<>();
        q.add(piece);
        result.add(piece);

        while (!q.isEmpty()) {
            var current = q.poll();
            for (var p : surrounding(current)) {
                if (!p.isAlive)
                    continue;
                uncoverSingle(p);
                result.add(p);
                if (minesAround(p) == 0)
                    q.add(p);
            }
        }
        return result;
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
                    continue;

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
        if (lost) return List.of();
        return pieces.values()
                     .stream()
                     .filter(MinesweeperPiece::isCovered)
                     .flatMap(p -> getLegalMoves(0, p).stream())
                     .toList();
    }

    @Override
    public List<MinesweeperMove> getLegalMoves(int player, MinesweeperPiece piece) {

        if (lost || !piece.isCovered())
            return List.of();
        else {
            var flagPiece = new MinesweeperPiece(piece.position, new MinesweeperPieceKind(FLAG));
            var flag = new MinesweeperMove(piece, flagPiece);

            var shovelPiece = new MinesweeperPiece(piece.getPosition(), new MinesweeperPieceKind(SHOVEL));
            var shovel = new MinesweeperMove(piece, shovelPiece);

            flagPiece.isFake = shovelPiece.isFake = true;
            return List.of(flag, shovel);
        }
    }

    boolean won() {
        return !lost && pieces.values().stream().allMatch(p -> !p.isAlive || !p.isCovered() || p.isFlagged());
    }

    @Override
    public int getPlayerCount() {
        return 1; // This is a game for one player
    }

    public State getState() {
        if (lost)
            return State.LOST;
        else if (won())
            return WON;
        else
            return NONE;
    }

    public enum State {
        NONE, WON, LOST
    }
}
