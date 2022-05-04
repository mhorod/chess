package app.checkers;

import app.core.game.Field;
import app.core.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Checkers implements Game<CheckersMove, CheckersPiece> {

    CheckersPiece[][] board = new CheckersPiece[8][8];
    List<CheckersPiece> player0pieces = new ArrayList<>();
    List<CheckersPiece> player1pieces = new ArrayList<>();

    CheckersPiece forcedPiece;
    int turn = 0;

    public Checkers() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 3; y++) {
                if ((x + y) % 2 == 0) {
                    board[x][y] = new CheckersPiece(new Field(y + 1, x + 1), 0);
                    player0pieces.add(board[x][y]);
                }
            }
        }

        for (int x = 0; x < 8; x++) {
            for (int y = 5; y < 8; y++) {
                if ((x + y) % 2 == 0) {
                    board[x][y] = new CheckersPiece(new Field(y + 1, x + 1), 1);
                    player1pieces.add(board[x][y]);
                }
            }
        }
    }

    @Override
    public List<CheckersPiece> makeMove(int player, CheckersMove move) {
        if (move.getPiece().getPlayer() == 0 && move.getField().rank() == 8)
            move.getPiece().isUpgraded = true;

        if (move.getPiece().getPlayer() == 1 && move.getField().rank() == 1)
            move.getPiece().isUpgraded = true;

        var result = new ArrayList<CheckersPiece>();
        result.add(move.getPiece());
        if (move.captures) {
            result.addAll(killAll(move.getPiece().getPosition(), move.getField(), move.dirX, move.dirY));

            setPiece(move.getPiece().getPosition(), null);
            setPiece(move.getField(), move.getPiece());
            move.getPiece().position = move.getField();

            if ((move.getPiece().isUpgraded() && !upgradedCapturingMoves(move.getPiece()).isEmpty()) ||
                    (!move.getPiece().isUpgraded() && !capturingMoves(move.getPiece()).isEmpty()))
                forcedPiece = move.getPiece();
            else {
                forcedPiece = null;
                turn = turn == 0 ? 1 : 0;
            }
            return result;
        }

        setPiece(move.getPiece().getPosition(), null);
        setPiece(move.getField(), move.getPiece());
        move.getPiece().position = move.getField();
        turn = turn == 0 ? 1 : 0;

        return result;
    }

    @Override
    public List<CheckersPiece> getPieces(int player) {
        if (player == 0) return player0pieces;
        if (player == 1) return player1pieces;
        return List.of();
    }

    @Override
    public List<CheckersPiece> getAllPieces() {
        return Stream.concat(player0pieces.stream(), player1pieces.stream()).toList();
    }

    @Override
    public List<CheckersMove> getLegalMoves(int player) {
        var result = new ArrayList<CheckersMove>();
        for (var piece : getPieces(player))
            result.addAll(getLegalMoves(player, piece));
        return result;
    }

    @Override
    public List<CheckersMove> getLegalMoves(int player, CheckersPiece piece) {
        if (forcedPiece != null && forcedPiece != piece) return List.of();
        if (turn != player) return List.of();
        if (!piece.isAlive() || piece.getPlayer() != player) return List.of();
        if (canAnyCapture(player)) {
            if (!piece.isUpgraded()) return capturingMoves(piece);
            else return upgradedCapturingMoves(piece);
        }

        if (!piece.isUpgraded()) return normalMoves(piece);
        else return upgradedMoves(piece);

    }

    Field capturingMove(CheckersPiece piece, int dirX, int dirY) {
        Field field = new Field(piece.getPosition().rank() + dirX, piece.getPosition().file() + dirY);
        if (canMove(field)) return null;
        else if (getPiece(field) != null && getPiece(field).getPlayer() != piece.getPlayer()) {
            field = new Field(field.rank() + dirX, field.file() + dirY);
            return canMove(field) ? field : null;
        }
        return null;
    }

    List<CheckersPiece> killAll(Field source, Field destination, int dirX, int dirY) {
        List<CheckersPiece> result = new ArrayList<>();
        Field field = new Field(source.rank() + dirX, source.file() + dirY);
        while (!field.equals(destination)) {
            if (getPiece(field) != null) {
                getPiece(field).isAlive = false;
                result.add(getPiece(field));
                setPiece(field, null);
            }

            field = new Field(field.rank() + dirX, field.file() + dirY);
        }
        return result;
    }

    List<CheckersMove> upgradedMoves(CheckersPiece piece) {
        List<CheckersMove> result = new ArrayList<>();
        result.addAll(upgradedMoves(piece, 1, 1));
        result.addAll(upgradedMoves(piece, 1, -1));
        result.addAll(upgradedMoves(piece, -1, 1));
        result.addAll(upgradedMoves(piece, -1, -1));
        return result;
    }

    List<CheckersMove> upgradedMoves(CheckersPiece piece, int dirX, int dirY) {
        List<CheckersMove> result = new ArrayList<>();

        Field field = new Field(piece.getPosition().rank() + dirX, piece.getPosition().file() + dirY);
        while (canMove(field)) {
            result.add(new CheckersMove(piece, field, false, dirX, dirY));
            field = new Field(field.rank() + dirX, field.file() + dirY);
        }
        return result;
    }

    List<CheckersMove> upgradedCapturingMoves(CheckersPiece piece) {
        List<CheckersMove> result = new ArrayList<>();
        result.addAll(upgradedCapturingMoves(piece, 1, 1));
        result.addAll(upgradedCapturingMoves(piece, 1, -1));
        result.addAll(upgradedCapturingMoves(piece, -1, 1));
        result.addAll(upgradedCapturingMoves(piece, -1, -1));
        return result;
    }

    List<CheckersMove> upgradedCapturingMoves(CheckersPiece piece, int dirX, int dirY) {
        List<CheckersMove> result = new ArrayList<>();

        Field field = new Field(piece.getPosition().rank() + dirX, piece.getPosition().file() + dirY);
        while (isFieldValid(field) && getPiece(field) == null) {
            field = new Field(field.rank() + dirX, field.file() + dirY);
        }
        if (isFieldValid(field) && getPiece(field).getPlayer() == piece.getPlayer()) return List.of();
        field = new Field(field.rank() + dirX, field.file() + dirY);
        while (canMove(field)) {
            result.add(new CheckersMove(piece, field, true, dirX, dirY));
            field = new Field(field.rank() + dirX, field.file() + dirY);
        }
        return result;
    }

    List<CheckersMove> normalMoves(CheckersPiece piece) {
        var result = new ArrayList<CheckersMove>();
        if (piece.getPlayer() == 0) {
            Field field = new Field(piece.getPosition().rank() + 1, piece.getPosition().file() + 1);
            if (canMove(field)) result.add(new CheckersMove(piece, field, false, 1, 1));
            field = new Field(piece.getPosition().rank() + 1, piece.getPosition().file() - 1);
            if (canMove(field)) result.add(new CheckersMove(piece, field, false, 1, -1));
        } else {
            Field field = new Field(piece.getPosition().rank() - 1, piece.getPosition().file() + 1);
            if (canMove(field)) result.add(new CheckersMove(piece, field, false, -1, 1));
            field = new Field(piece.getPosition().rank() - 1, piece.getPosition().file() - 1);
            if (canMove(field)) result.add(new CheckersMove(piece, field, false, -1, -1));
        }

        return result;
    }

    List<CheckersMove> capturingMoves(CheckersPiece piece) {
        var result = new ArrayList<CheckersMove>();
        Field f = capturingMove(piece, 1, 1);
        if (f != null) result.add(new CheckersMove(piece, f, true, 1, 1));
        f = capturingMove(piece, 1, -1);
        if (f != null) result.add(new CheckersMove(piece, f, true, 1, -1));
        f = capturingMove(piece, -1, 1);
        if (f != null) result.add(new CheckersMove(piece, f, true, -1, 1));
        f = capturingMove(piece, -1, -1);
        if (f != null) result.add(new CheckersMove(piece, f, true, -1, -1));
        return result;
    }

    boolean canAnyCapture(int player) {
        for (var piece : getPieces(player))
            if (piece.isAlive()) {
                if (!piece.isUpgraded() && capturingMoves(piece).size() != 0) return true;
                if (piece.isUpgraded() && upgradedCapturingMoves(piece).size() != 0) return true;
            }
        return false;
    }

    boolean canMove(Field field) {
        if (!isFieldValid(field)) return false;
        return getPiece(field) == null;
    }

    CheckersPiece getPiece(Field field) {
        if (!isFieldValid(field)) return null;
        return board[field.file() - 1][field.rank() - 1];
    }

    void setPiece(Field field, CheckersPiece piece) {
        if (!isFieldValid(field)) return;
        board[field.file() - 1][field.rank() - 1] = piece;
    }

    boolean isFieldValid(Field f) {
        return f.rank() >= 1 && f.rank() <= 8 && f.file() >= 1 && f.file() <= 8;
    }

    @Override
    public int getPlayerCount() {
        return 2;
    }
}
