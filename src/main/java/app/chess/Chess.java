package app.chess;

import app.chess.moves.*;
import app.chess.pieces.*;
import app.chess.rules.*;
import app.chess.utils.*;
import app.core.game.*;

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

    /**
     * TODO: Possibly extract this function somewhere where it makes more sense semantically 
     */
    private List<ChessMove> getPromotionMoves(int player) {

        var where = Utils.findPawnThatCanBePromoted(player,board).getPosition();

        //And now we need to create piece pick for each piece that's in game... ouch
        List<AbstractChessPiece> subAnswer = new ArrayList<>();
        boolean isBlack = player != 0;

        subAnswer.add(new Rook(where,isBlack));
        subAnswer.add(new Queen(where, isBlack));
        subAnswer.add(new Knight(where, isBlack));
        subAnswer.add(new Bishop(where, isBlack));

        List<ChessMove> answer = new ArrayList<>();

        for(var piece : subAnswer){
            answer.add(new PiecePick(piece.wrap(),where));
        }

        return answer;
    }

    @Override
    public List<ChessMove> getLegalMoves(int player) {

        if (manager.thereIsPromotionPending()) {
            //Promotion is this funny case where we should create entirely different branch
            return getPromotionMoves(player);
        }

        List<ChessPiece> playersPieces = getPieces(player);
        List<ChessMove> allLegalMoves = new ArrayList<>();

        for (var currentPiece : playersPieces) {
            List<ChessMove> someLegalMoves = getLegalMoves(player, currentPiece);
            allLegalMoves.addAll(someLegalMoves);
        }

        return allLegalMoves;
    }

    @Override
    public List<ChessMove> getLegalMoves(int player, ChessPiece piece) {
        if(player != manager.getCurrentPlayer()){
            return Collections.emptyList();
        }

        if(manager.thereIsPromotionPending()){
            if(piece == Utils.findPawnThatCanBePromoted(player,board)){
                return getPromotionMoves(player);
            }
            else{
                return Collections.emptyList();
            }
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
