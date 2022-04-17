package com.example.chess;

/**
 * An class that represents a chess piece.
 * The chess board will be a 2-dimensional array of such pieces.
 *
 * @author Dominik Matuszek
 */

public class Piece {

    static class illegalTypeChangeException extends RuntimeException {
    }

    public enum color {
        WHITE, BLACK
    }

    public enum type {
        PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING
    }

    private final color col;
    private type tp; //type is not final because pawns can change their type at the last back rank

    public Piece(color col, type tp) {
        this.col = col;
        this.tp = tp;
    }

    public void changeType(type newType) {

        if (tp != type.PAWN) {
            //We don't want any other figure to change its type
            throw new illegalTypeChangeException();
        }

        if (newType == type.KING || newType == type.PAWN) {
            //michał co ty robisz
            throw new illegalTypeChangeException();
        }
        tp = newType;
    }

    public color getColor() {
        return col;
    }

    public type getType() {
        return tp;
    }

    public boolean canMoveLikeRook() {
        return tp == type.ROOK || tp == type.QUEEN;
    }

    public boolean canMoveLikeBishop() {
        return tp == type.BISHOP || tp == type.QUEEN;
    }

    public boolean canMoveLikeKnight() {
        return tp == type.KNIGHT;
    }

    public boolean canMoveLikeKing() {
        return tp == type.KING;
    }

    public boolean isPawn() {
        return tp == type.PAWN;
    }

}
