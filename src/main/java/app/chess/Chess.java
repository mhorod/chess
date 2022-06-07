package app.chess;

import app.chess.board.ChessBoard;
import app.chess.moves.ChessMove;
import app.chess.moves.Promotion;
import app.chess.pieces.Bishop;
import app.chess.pieces.Knight;
import app.chess.pieces.Queen;
import app.chess.pieces.Rook;
import app.chess.rules.Rule;
import app.chess.rules.StandardValidator;
import app.chess.rules.Validator;
import app.chess.utils.Utils;
import app.core.game.Game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Chess kernel that implements behavior of standard chess
 */
public class Chess implements Game<ChessMove, ChessPiece> {
    public static final int SIZE = 8;
    private final StateManager manager = new StateManager();
    private final Mover mover = new StandardMover();
    private final Validator validator;
    ChessPiece[][] board;
    private Collection<Rule> ruleset;

    public Chess(ChessBoard board) {
        this.board = board.getPieces();
        validator = new StandardValidator();
        ruleset = validator.getDefaultRules();
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

        var promotedPawn = Utils.findPawnThatCanBePromoted(player, board);
        var where = promotedPawn.getPosition();

        //And now we need to create piece pick for each piece that's in game... ouch
        List<AbstractChessPiece> subAnswer = new ArrayList<>();
        boolean isBlack = player != 0;

        var color = promotedPawn.getColor();
        subAnswer.add(new Rook(where, color));
        subAnswer.add(new Queen(where, color));
        subAnswer.add(new Knight(where, color));
        subAnswer.add(new Bishop(where, color));

        List<ChessMove> answer = new ArrayList<>();

        for (var pieceAfterPromotion : subAnswer) {
            answer.add(new Promotion(promotedPawn.piece.wrap(), pieceAfterPromotion.wrap(), where));
        }

        return answer;
    }

    @Override
    public List<ChessMove> getLegalMoves(int player) {

        if (player != manager.getCurrentPlayer()) {
            return Collections.emptyList();
        }

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
        if (!piece.isAlive())
            return List.of();

        if (player != manager.getCurrentPlayer()) {
            return Collections.emptyList();
        }

        if (manager.thereIsPromotionPending()) {
            if (piece.equals(Utils.findPawnThatCanBePromoted(player, board))) {
                return getPromotionMoves(player);
            } else {
                return Collections.emptyList();
            }
        }

        return validator.getLegalMoves(piece, board, ruleset).stream().toList();
    }

    @Override
    public List<ChessPiece> makeMove(int player, ChessMove move) {
        return mover.makeMove(player, move, board, manager);
    }

    /**
     * Get current win/lose state of the player
     */
    public ChessState getState(int player) {
        if (manager.getCurrentPlayer() != player) {
            return ChessState.OK;
        }

        if (getLegalMoves(player).isEmpty()) {
            if (Utils.kingIsSafe(player, board)) {
                return ChessState.DRAW;
            } else {
                return ChessState.MATED;
            }
        } else {
            if (Utils.kingIsSafe(player, board)) {
                return ChessState.OK;
            } else {
                return ChessState.CHECKED;
            }
        }
    }

    public int getCurrentPlayer() {
        return manager.getCurrentPlayer();
    }

    public Boolean checkIfEnemyKingIsCheckedAfterMove(ChessMove move) {
        Function<ChessPiece[][], Boolean> checkLambda = (board) -> {
            final int playerToCheck = move.getPiece().getPlayer() == 0 ? 1 : 0;
            return !Utils.kingIsSafe(playerToCheck, board);
        };
        return PredicateChecker.safelyCheckPredicate(checkLambda, move, board, manager, mover);
    }

    public void overrideRules(List<Rule> rules) {
        ruleset = rules;
    }


    @Override
    public int getPlayerCount() {
        return 2; //It's always going to be that way in classical chess
    }

    /**
     * Exception thrown when board state is disturbed
     */
    static class BoardDiscrepancy extends RuntimeException {
    }


}
