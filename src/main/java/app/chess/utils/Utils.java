package app.chess.utils;

import app.chess.ChessPiece;
import app.chess.moves.Castle;
import app.chess.moves.ChessMove;
import app.chess.pieces.ChessPieceKind;
import app.chess.rules.KingsSafetyDisabledRules;
import app.chess.rules.StandardValidator;
import app.chess.rules.Validator;
import app.core.game.Field;

import java.util.ArrayList;
import java.util.List;

import static app.chess.Chess.SIZE;

public final class Utils {
    private Utils() {
        //Nothing is done here, I'm only doing this to hide the implicit public constructor as it's only a utility class
    }

    public static boolean roadNotObstructed(Field currentPosition, Field newPosition, ChessPiece[][] board) {
        //First, we will validate the path itself (not including the last spot)
        //Because of that, we don't calculate the knight as it sort of "jumps"

        int rankDelta = newPosition.rank() - currentPosition.rank();
        int fileDelta = newPosition.file() - currentPosition.file();

        if (rankDelta == 0 && fileDelta == 0) {
            //You can't move on a field on which you already are
            return false;
        }

        int rankVector = Integer.compare(rankDelta, 0);
        int fileVector = Integer.compare(fileDelta, 0);

        int iterRank = currentPosition.rank();
        int iterFile = currentPosition.file();

        while (iterRank + rankVector != newPosition.rank() || iterFile + fileVector != newPosition.file()) {
            iterRank += rankVector;
            iterFile += fileVector;

            if (board[iterRank][iterFile] != null) {
                //Something's on our way, that means that the move is invalid
                return false;
            }

        }
        return true;
    }

    /**
     * @return Assuming that castling is possible, returns the position on which the rook should be located. Makes
     *         ABSOLUTELY NO GUARANTEE that castling is legal.
     */
    public static Field getRookPositionBasedOnCastling(Castle move) {
        final int currentFile = move.getPiece().getPosition().file();

        final int newFile = move.getField().file();

        final boolean kingSideCastling = newFile - currentFile > 0;  //If the file is increasing, this means (both for white and black) that we are castling king side

        final int rookRank = move.getPiece().getPlayer() == 0 ? 1 : 8; //White has rooks on first rank, black on eighth
        final int rookFile = kingSideCastling ? 8 : 1;

        return new Field(rookRank, rookFile);
    }

    public static boolean fieldIsValid(Field toValidate) {
        return toValidate.rank() <= SIZE && toValidate.file() <= SIZE && toValidate.rank() > 0 && toValidate.file() > 0;
    }

    public static ChessPiece getPieceByField(Field field, ChessPiece[][] board) {
        return board[field.rank()][field.file()];
    }

    /**
     * Puts the piece on a given piece on board, WITHOUT changing any data about piece location (inside the piece).
     * Should be used with caution.
     *
     * @return Piece that was already on a given field
     */
    public static ChessPiece putPieceOnBoard(ChessPiece who, Field field, ChessPiece[][] board) {
        var wasThereBefore = getPieceByField(field, board);
        board[field.rank()][field.file()] = who;
        return wasThereBefore;
    }

    /**
     * @param checkPlayer Whether to return pieces of only one player
     * @param player Player whose pieces should be returned
     * @return All pieces on board that satisfact the given criteria
     */
    public static List<ChessPiece> getMatchingPieces(boolean checkPlayer, int player, ChessPiece[][] board) {
        ArrayList<ChessPiece> piecesList = new ArrayList<>();

        for (int rank = 1; rank <= SIZE; rank++) {
            for (int file = 1; file <= SIZE; file++) {
                ChessPiece currentPiece = board[rank][file];
                if (currentPiece != null && (currentPiece.getPlayer() == player || !checkPlayer)) {
                    //If checkPlayer is false, the second condition is always true
                    piecesList.add(board[rank][file]);
                }
            }
        }
        return piecesList;
    }

    public static boolean fieldIsUnderAttack(int byWho, Field field, ChessPiece[][] board) {
        List<ChessPiece> playerPieces = getMatchingPieces(true, byWho, board);
        Validator validator = new StandardValidator();
        var ruleset = new KingsSafetyDisabledRules().getRules();

        for (var piece : playerPieces) {
            List<ChessMove> movesForPiece = validator.getLegalMoves(piece, board, ruleset);
            for (var move : movesForPiece) {
                if (move.getField().rank() == field.rank() && move.getField().file() == field.file()) {
                    return true;
                }
            }
        }

        return false;
    }

    public static Field getKingsPosition(int player, ChessPiece[][] board) {
        //Assumption is that there is only one king of each colour on board, which is a pretty reasonable assumption may I say
        //Also please note that we have to iterate over board this way, because I don't want to rely on internal piece position tracking
        //Why you might ask?
        //Well, it boils down to the implementation of the YourKingCannotBeCheckedAfterYourMove rule, which only moves a figure on board
        //Without actually, ya know, modifying its internal state
        //Took me an hour to debug and I don't want to talk about it.

        for (int i = 1; i <= SIZE; i++) {
            for (int j = 1; j <= SIZE; j++) {
                if (board[i][j] != null && board[i][j].getKind() == ChessPieceKind.KING && board[i][j].getPlayer() == player) {
                    return new Field(i, j);
                }
            }
        }
        //Something weird had to happen
        throw new ThereIsNoKingOnBoard();
    }

    public static boolean kingIsSafe(int whose, ChessPiece[][] board) {
        Field kingLocation = getKingsPosition(whose, board);
        int enemyPlayer = whose == 0 ? 1 : 0;

        return !fieldIsUnderAttack(enemyPlayer, kingLocation, board);
    }

    public static ChessPiece findPawnThatCanBePromoted(int player, ChessPiece[][] board) {
        var allPieces = Utils.getMatchingPieces(true, player, board);
        final int promotionRank = player == 0 ? 8 : 1;
        Field where = null;
        for (var piece : allPieces) {
            if (piece.getKind() == ChessPieceKind.PAWN && piece.getPosition().rank() == promotionRank) {
                //That's the piece we want, so we will remember the Field on which it stands
                where = piece.getPosition();
            }
        }
        if (where == null) {
            throw new BoardDiscrepancy();
        }
        return getPieceByField(where, board);
    }

    static class ThereIsNoKingOnBoard extends RuntimeException {
    }

    public static class BoardDiscrepancy extends RuntimeException {
    }
}
