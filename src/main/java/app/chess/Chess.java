package app.chess;

import app.chess.moves.Castle;
import app.chess.moves.ChessMove;
import app.chess.pieces.ChessPiece;
import app.chess.pieces.ChessPieceKind;
import app.chess.pieces.King;
import app.core.game.Field;
import app.core.game.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO: Extend Game with appropriate types and implement chess logic
 */
public class Chess implements Game<ChessMove, ChessPiece> {
    public static final int SIZE = 8;
    ChessPiece[][] board;
    private boolean blackToMove = false;
    private boolean pendingPromotion = false; //In case there is a promotion of a pawn, that move is split into 2 submoves
    private boolean testMode = false; //this is NOT how it should be done, but it's the simplest way

    public Chess(Board board) {
        this.board = board.pieces;
    }

    public static boolean fieldIsValid(Field toValidate) {
        return toValidate.rank() <= SIZE && toValidate.file() <= SIZE && toValidate.rank() > 0 && toValidate.file() > 0;
    }


    /**
     * @param checkPlayer Whether to return pieces of only one player
     * @param player Player whose pieces should be returned
     * @return All pieces on board that satisfact the given criteria
     */
    private List<ChessPiece> getMatchingPieces(boolean checkPlayer, int player) {
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

    private boolean validateKingSafety(int player) {
        //Validates king safety the way that is done in validateKingSafety(ChessMove move) but it doesn't perform any move
        //Yes, it's duplicating some parts of code, but we will have to live with it for now

        int secondPlayer = player == 0 ? 1 : 0;

        try {
            testMode = true;
            getLegalMoves(secondPlayer);
        } catch (KingCanBeTaken e) {
            return false;
        } finally {
            testMode = false;
        }
        return true;
    }

    private boolean validateKingSafety(ChessMove move) {
        int player = move.getPiece().getPlayer();

        int secondPlayer = player == 0 ? 1 : 0; //We want to look at the board from the perspective of the second player, asking whether he can take the king now
        //The idea is simple:
        //We "make" a move moving the figure to the given place
        //Then check if something went terribly wrong

        //Is it effective? No
        //Is it supposed to be effective? No
        //Will it make this code simpler? Yes

        int previousRank = move.getPiece().getPosition().rank();
        int previousFile = move.getPiece().getPosition().file();

        int rank = move.getField().rank();
        int file = move.getField().file();

        ChessPiece whatWasThere = board[rank][file]; //So that we can

        board[rank][file] = move.getPiece(); //Now we "move" the figure
        board[previousRank][previousFile] = null;

        try {
            testMode = true;
            getLegalMoves(secondPlayer);
        } catch (KingCanBeTaken e) {
            return false;
        } finally {
            testMode = false;
            //We revert the state of the board to the previous status
            board[previousRank][previousFile] = move.getPiece();
            board[rank][file] = whatWasThere;
        }
        return true;
    }

    private boolean pawnMoveValidation(ChessMove move) {
        //Because pawns have somewhat weird moving possibilities, validation of their moves is inside another function
        int currentRank = move.getPiece().getPosition().rank();
        int currentFile = move.getPiece().getPosition().file();

        int newRank = move.getField().rank();
        int newFile = move.getField().file();

        if (currentFile - newFile == 0) {
            //Going forward is considered first

            //First, let's consider going forward by 2 - the only case where we have to perform this check
            if (Math.abs(newRank - currentRank) == 2) {
                //We want to check if the path is not obstructed
                if (!roadNotObstructed(move.getPiece().getPosition(), move.getField())) {
                    //The only type of move whether we have to check if something is on the road is being obstructed
                    return false;
                }
            }
            //And we can't move forward if the place we want to move to is already taken by ANY piece
            if (board[newRank][newFile] != null) {
                return false;
            }
        } else {
            //Now we are considering going to the sides, so there has to be enemy piece involved
            ChessPiece wasThereBefore = board[newRank][newFile];

            if (wasThereBefore == null) {
                //Perhaps en passant is possible
                //If not, we'll return false
                wasThereBefore = board[move.getPiece().getPlayer() == 0 ? newRank - 1 : newRank + 1][newFile];
                if (wasThereBefore == null || !wasThereBefore.enPassantable() || wasThereBefore.getPlayer() == move.getPiece()
                                                                                                                   .getPlayer()) {
                    return false;
                }

            } else {
                //somebody's here
                if (wasThereBefore.getPlayer() != move.getPiece().getPlayer()) {
                    //There's enemy piece to be taken, so we can validate this move
                    //Except for when the king is under attack
                    if (wasThereBefore.getKind() == ChessPieceKind.KING) {
                        throw new KingCanBeTaken();
                    }
                } else {
                    return false;
                }
            }
        }

        if (!testMode && !validateKingSafety(move)) {
            //We are not in a test mode (where we don't validateKing'sSafety), so we cannot move the pawn
            //For what "testMode" is, please look at validateKingSafety code
            return false;
        }

        return true;
    }

    private boolean roadNotObstructed(Field currentPosition, Field newPosition) {
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

            //System.out.println(iterRank + " " + iterFile);

            if (board[iterRank][iterFile] != null) {
                //Something's on our way, that means that the move is invalid
                return false;
            }

        }
        return true;
    }

    /**
     * @param move
     * @return Assuming that castling is possible, returns the position on which the rook should be located. Makes
     *         ABSOLUTELY NO GUARANTEE that castling is legal.
     */
    private Field getRookPositionBasedOnCastling(Castle move) {
        final int currentRank = move.getPiece().getPosition().rank();
        final int currentFile = move.getPiece().getPosition().file();

        final int newRank = move.getField().rank();
        final int newFile = move.getField().file();

        final boolean kingSideCastling = newFile - currentFile > 0;  //If the file is increasing, this means (both for white and black) that we are castling king side

        final int rookRank = move.getPiece().getPlayer() == 0 ? 1 : 8; //White has rooks on first rank, black on eighth
        final int rookFile = kingSideCastling ? 8 : 1;

        return new Field(rookRank, rookFile);
    }

    private boolean validateMove(Castle move) {
        //There are 6 conditions for castling to be valid and checking for some of them is going to be annoying to say the least

        //First: King cannot be in check
        if (!testMode && !validateKingSafety(move.getPiece().getPlayer())) {
            return false;
        }

        //Second: Rook and king haven't moved since beginning of the game

        final int currentRank = move.getPiece().getPosition().rank();
        final int currentFile = move.getPiece().getPosition().file();

        final int newRank = move.getField().rank();
        final int newFile = move.getField().file();

        final boolean kingSideCastling = newFile - currentFile > 0;  //If the file is increasing, this means (both for white and black) that we are castling king side

        final int rookRank = move.getPiece().getPlayer() == 0 ? 1 : 8; //White has rooks on first rank, black on eighth
        final int rookFile = kingSideCastling ? 8 : 1;

        ChessPiece rook = board[rookRank][rookFile]; //You might ask why I've decided to call that variable "rook" when I have no guarantee that such piece is a rook. Well, I'm checking that in the if that is below

        if (rook == null || !rook.canParticipateInCastling()) {
            //There's no rook to even, you know, castle with
            //Or something that on that place cannot castle
            return false;
        }
        //You might ask: Why is that sufficient? Well, only rooks and kings can ever participate in castling, and any move invalidates that right.
        //That pretty much means that whatever is standing on that place has been standing there for all of the game, so it is eligible for castling

        //It'd be great to check what's going on with our king
        //We have a guarantee that the piece that tries to castle is king, because we are checking that in Castle constructor

        if (!move.getPiece().canParticipateInCastling()) {
            return false;
        }

        //So, our rook didn't move and our king also didn't
        //That's perfect, second condition is satisfied

        //Third condition: Road between rook and king is not obstructed
        if (!roadNotObstructed(move.getPiece().getPosition(), new Field(rookRank, rookFile))) {
            return false;
        }

        //Fourth condition: Nothing on the road of the king is under attack
        int multiplier = kingSideCastling ? 1 : -1;

        for (int i = 1; i <= 2; i++) {
            //This is somewhat weird, but we are going to check if this is ok by placing a fake king on a piece that cannot be attacked and checking if anything goes wrong
            //Truly magnificent
            //(and crazy ineffective, but it was never meant to be effective)
            ChessPiece currentPiece = board[currentRank][currentFile + i * multiplier];
            board[currentRank][currentFile + i * multiplier] = new King(
                    new Field(currentRank, currentFile + i * multiplier), move.getPiece().getPlayer() != 0);
            if (!testMode && !validateKingSafety(move.getPiece().getPlayer())) {
                board[currentRank][currentFile + i * multiplier] = currentPiece;
                return false;
            }
            board[currentRank][currentFile + i * multiplier] = currentPiece;
        }

        return true;
    }


    private boolean validateMove(ChessMove move) {
        Field currentPosition = move.getPiece().getPosition();
        Field newPosition = move.getField();

        if (move.getPiece().getKind() == ChessPieceKind.PAWN) {
            return pawnMoveValidation(move);
        }

        if (move.getClass() == Castle.class) {
            //That's a dirty way of solving it, but will have to do for now
            return validateMove((Castle) move);
        }

        if (board[currentPosition.rank()][currentPosition.file()] != move.getPiece()) {
            //We've got a discrepancy in the data, as move references other piece than it should be referencing
            System.err.println(board[currentPosition.rank()][currentPosition.file()]);
            System.err.println(move.getPiece());
            System.err.println(move.getPiece().getPosition());

            System.err.println("CRITICAL ERROR " + move);
            throw new BoardDiscrepancy();
        }


        //We will initially check if the path isn't obstructed

        if (move.getPiece().getKind() != ChessPieceKind.KNIGHT) {
            //This function doesn't care about knights movement, checks only "transit"
            if (!roadNotObstructed(currentPosition, newPosition)) {
                return false;
            }
        }


        ChessPiece figureOnNewPosition = board[newPosition.rank()][newPosition.file()];

        if (figureOnNewPosition != null && figureOnNewPosition.getPlayer() == move.getPiece().getPlayer()) {
            //On the position we'd like to move into stands allied figure (thus, we cannot take it and the move is invalid)
            return false;
        }

        if (figureOnNewPosition != null && figureOnNewPosition.getKind() == ChessPieceKind.KING) {
            //waaaaaaait, we can actually take the enemy king?
            throw new KingCanBeTaken();
        }

        //Seems like the path for our piece is ok
        //Now there is king's safety to be validated


        if (!testMode && !validateKingSafety(move)) {
            return false;
        }


        //System.out.println("Hello!");
        //seems good to me

        return true;
    }

    @Override
    public List<ChessPiece> getPieces(int player) {
        return getMatchingPieces(true, player);
    }

    @Override
    public List<ChessPiece> getAllPieces() {
        return getMatchingPieces(false, 0);
    }

    private List<ChessMove> getPromotionMoves(int player) {
        return Collections.emptyList();
    }

    @Override
    public List<ChessMove> getLegalMoves(int player) {

        if (pendingPromotion) {
            //Promotion is this funny case where we should create entirely different branch
            pendingPromotion = false;
            return getPromotionMoves(player);
        }

        List<ChessPiece> playersPieces = getPieces(player);

        List<ChessMove> allPlayersLegalMoves = new ArrayList<>();

        for (ChessPiece currentPiece : playersPieces) {
            List<ChessMove> someLegalMoves = getLegalMoves(player, currentPiece);
            allPlayersLegalMoves.addAll(someLegalMoves);
        }

        return allPlayersLegalMoves;
    }

    @Override
    public List<ChessMove> getLegalMoves(int player, ChessPiece piece) {
        //Please note that this function is not particularly effective and was never meant to be.

        if (!testMode && (piece.getPlayer() == 1) != blackToMove) {
            return Collections.emptyList();
        }

        if (piece.getPlayer() != player) {
            return Collections.emptyList();
        }

        List<ChessMove> potentialMoves = piece.getPotentialMoves();
        List<ChessMove> legalMoves = new ArrayList<>();

        for (ChessMove potentialMove : potentialMoves) {
            //System.out.println("Validating " + potentialMove);
            if (validateMove(potentialMove)) {
                //System.out.println("Validation for " + potentialMove + " OK");
                legalMoves.add(potentialMove);
            }
            //System.out.println("End of validation " + potentialMove);
        }

        return legalMoves;
    }

    private void resetWasMoved() {
        var allPieces = getAllPieces();

        for (var currentPiece : allPieces) {
            currentPiece.resetMoved();
        }
    }

    /**
     * Executes castling under assumption that function calling it made sure that such castling is, indeed, legal
     *
     * @param move
     * @return
     */
    private List<ChessPiece> castleMove(Castle move) {
        Field whereRookIs = getRookPositionBasedOnCastling(move);

        int rookRank = whereRookIs.rank();
        int rookFile = whereRookIs.file();

        //Castling can't kill anything, etc.
        //Just changing positions of 2 pieces, nothing can go wrong
        //Right?

        ChessPiece king = move.getPiece();
        int kingRank = king.getPosition().rank(); //Yes, kingRank will be the same as rookRank
        //In fact, let's just have an additional assertion here

        if (kingRank != rookRank) {
            throw new BoardDiscrepancy();
        }

        int kingFile = king.getPosition().file();

        ChessPiece rook = board[rookRank][rookFile];

        int newRookFile = rookFile == 1 ? 4 : 6; //It is just this way
        //Rank will stay the same, obviously

        board[rookRank][rookFile] = null;
        board[kingRank][kingFile] = null;

        board[move.getField().rank()][move.getField().file()] = king;
        king.move(move.getField());

        board[rookRank][newRookFile] = rook;
        rook.move(new Field(rookRank, newRookFile));

        return List.of(move.getPiece(), rook);
    }

    @Override
    public List<ChessPiece> makeMove(int player, ChessMove move) {

        if (move.getPiece().getPlayer() != player) {
            throw new PlayerDiscrepancy();
        }


        //It's better to be sure that the move we get is actually legal
        List<ChessMove> acceptableMoves = this.getLegalMoves(player, move.getPiece());
        if (!acceptableMoves.contains(move)) {
            System.err.println(acceptableMoves);
            System.err.println(move);
            throw new IllegalMove();
        }


        ///TODO: MAKE IT NOT FAIL WITH PENDING PROMOTION
        blackToMove = !blackToMove;


        //Now, if castling is involved, I'm going to delegate it to another function because otherwise this will be to messy
        if (move.getClass() == Castle.class) {
            //I'm sure checking it that way won't cause any issues down the line
            return castleMove((Castle) move);
        }

        if (pendingPromotion) {
            ///TODO: IMPLEMENT PROMOTION
            pendingPromotion = false;
        }

        resetWasMoved(); //Because things will be overwritten

        //Given the fact that this move is legal, we can simply execute it now
        if (move.getField().rank() == SIZE || move.getField().rank() == 1) {
            //That's funny because it will result in a pawn promotion
            if (pendingPromotion) {
                throw new RuntimeException("This shouldn't even be mathematically possible");
            }
            pendingPromotion = true;
        }

        int newRank = move.getField().rank();
        int newFile = move.getField().file();

        int oldRank = move.getPiece().getPosition().rank();
        int oldFile = move.getPiece().getPosition().file();

        ChessPiece wasHereBefore = board[newRank][newFile];

        if (wasHereBefore != null && wasHereBefore.getPlayer() == player) {
            throw new IllegalMove(); //This shouldn't even be possible because we've validated that already, but just to be sure
        }

        if (wasHereBefore != null) {
            wasHereBefore.kill();
            board[newRank][newFile] = move.getPiece();
        } else {
            board[newRank][newFile] = move.getPiece();

            //you'd think that we are done
            //and you'd be wrong
            //because en passant exists
            //to mess with our code

            if (move.getPiece().getKind() == ChessPieceKind.PAWN && oldFile - newFile != 0) {
                //So, there is nothing in there but we are going sideways
                //This means en passant
                //So we'll just remove whatever stays in enpassantable place
                wasHereBefore = board[oldRank][newFile];
                if (wasHereBefore == null) {
                    //?????????
                    throw new IllegalMove();
                } else {
                    wasHereBefore.kill();
                    board[oldRank][newFile] = null;
                }
            }

        }


        board[oldRank][oldFile] = null;

        move.getPiece().move(move.getField());

        //After executing the move, we can finally return a list informing what's happening on the board
        ArrayList<ChessPiece> changedList = new ArrayList<>();
        changedList.add(move.getPiece());
        if (wasHereBefore != null) {
            changedList.add(wasHereBefore);
        }

        return changedList;
    }

    @Override
    public int getPlayerCount() {
        return 2; //It's always going to be that way in classical chess
    }

    static class IllegalMove extends RuntimeException {
    }

    static class PlayerDiscrepancy extends RuntimeException {
    }

    static class BoardDiscrepancy extends RuntimeException {
    }

    static class KingCanBeTaken extends RuntimeException {
    }
}
