package app.chess;

import app.chess.moves.*;
import app.chess.pieces.ChessPieceKind;
import app.chess.rules.*;
import app.chess.utils.*;
import app.core.game.Field;
import app.core.game.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chess implements Game<ChessMove, ChessPiece> {
    public static final int SIZE = 8;
    ChessPiece[][] board;

    private final StateManager manager = new StateManager();
    private final Mover mover = new StandardMover();
    private final Validator validator = new StandardValidator();

    public Chess(Board board) {
        this.board = board.pieces;
    }

    @Override
    public List<ChessPiece> getPieces(int player) {
        return Utils.getMatchingPieces(true, player, board);
    }

    @Override
    public List<ChessPiece> getAllPieces() {
        return Utils.getMatchingPieces(false, 0, board);
    }

    private List<ChessMove> getPromotionMoves(int player) {
        return Collections.emptyList();
    }

    @Override
    public List<ChessMove> getLegalMoves(int player) {

        if (manager.thereIsPromotionPending()) {
            if (true)
                throw new BoardDiscrepancy();
            //Promotion is this funny case where we should create entirely different branch
            manager.markPromotionAsDone();
            return getPromotionMoves(player);
        }

        List<ChessPiece> playersPieces = getPieces(player);
        List<ChessMove> allLegalMoves = new ArrayList<>();

        for (var currentPiece : playersPieces) {
            List<ChessMove> someLegalMoves = validator.getLegalMoves(currentPiece, board);
            allLegalMoves.addAll(someLegalMoves);
        }

        return allLegalMoves;
    }

    @Override
    public List<ChessMove> getLegalMoves(int player, ChessPiece piece) {
        if(player != manager.getCurrentPlayer()){
            return Collections.emptyList();
        }
        return validator.getLegalMoves(piece,board);
    }

    @Override
    public List<ChessPiece> makeMove(int player, ChessMove move) {
        return mover.makeMove(player, move, board, manager);
    }

    @Override
    public int getPlayerCount() {
        return 2; //It's always going to be that way in classical chess
    }
    
    static class BoardDiscrepancy extends RuntimeException {
    }

}
