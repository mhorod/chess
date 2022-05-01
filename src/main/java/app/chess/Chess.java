package app.chess;

import app.chess.moves.*;
import app.chess.pieces.*;
import app.core.game.*;

import java.util.*;

/**
 * TODO: Extend Game with appropriate types and implement chess logic
 */
public class Chess implements Game<ChessMove, ChessPiece> {
    static class IllegalMove extends RuntimeException{}
    static class PlayerDiscrepancy extends RuntimeException{}
    static class BoardDiscrepancy extends RuntimeException{}
    static class KingCanBeTaken extends RuntimeException{}
    public static final int SIZE = 8;
    ChessPiece[][] chessBoard = new ChessPiece[SIZE+1][SIZE+1]; //We are adding 1 because we will start counting at 1
    //This is a little uncommon, but in Chess such numeration is also starting from 1 and it will hopefully create fewer bugs if we stick to this convention

    private boolean blackToMove = false;
    private boolean pendingPromotion = false; //In case there is a promotion of a pawn, that move is split into 2 submoves

    private void initializePawns(){
        final int whitePawnRank = 2;
        final int blackPawnRank = 7;

        for(int i=1;i<=SIZE;i++){
            chessBoard[whitePawnRank][i] = new Pawn(new Field(whitePawnRank, i),false);
            chessBoard[blackPawnRank][i] = new Pawn(new Field(blackPawnRank, i), true);
        }
    }
    private void initializeBoard(){
        //Well, we're going to have to hard-code pieces initial placement here
        //Yes, writing code this repetitive is painful, but somebody has to do it

        /*
         * 8 r n b q k b n r
         * 7 p p p p p p p p
         * 6
         * 5
         * 4
         * 3
         * 2 P P P P P P P P
         * 1 R N B Q K B N R
         *   1 2 3 4 5 6 7 8
         */

        initializePawns();

        final int whiteRank = 1;
        final int blackRank = 8;

        //Initializing rooks

            //White
            chessBoard[whiteRank][1] = new Rook(new Field(whiteRank, 1), false);
            chessBoard[whiteRank][8] = new Rook(new Field(whiteRank,8), false);

            //Black
            chessBoard[blackRank][1] = new Rook(new Field(blackRank,1),true);
            chessBoard[blackRank][8] = new Rook(new Field(blackRank,8),true);

        //Initializing knights

            //White
            chessBoard[whiteRank][2] = new Knight(new Field(whiteRank,2), false);
            chessBoard[whiteRank][7] = new Knight(new Field(whiteRank,7), false);

            //Black
            chessBoard[blackRank][2] = new Knight(new Field(blackRank,2), true);
            chessBoard[blackRank][7] = new Knight(new Field(blackRank,7), true);

        //Initializing bishops

            //White
            chessBoard[whiteRank][3] = new Bishop(new Field(whiteRank,3), false);
            chessBoard[whiteRank][6] = new Bishop(new Field(whiteRank,6), false);

            //Black
            chessBoard[blackRank][3] = new Bishop(new Field(blackRank,3), true);
            chessBoard[blackRank][6] = new Bishop(new Field(blackRank,6), true);

        //Queens

            //White
            chessBoard[whiteRank][4] = new Queen(new Field(whiteRank,4),false);

            //Black
            chessBoard[blackRank][4] = new Queen(new Field(blackRank,4), true);

        //Kings

            //White
            chessBoard[whiteRank][5] = new King(new Field(whiteRank,5), false);

            //Black
            chessBoard[blackRank][5] = new King(new Field(blackRank,5), true);

    }
    public Chess(){
        initializeBoard();
    }

    public static boolean fieldIsValid(Field toValidate){
        return toValidate.rank() <= SIZE && toValidate.file() <= SIZE && toValidate.rank() > 0 && toValidate.file() > 0;
    }

    /**
     *
     * @param checkPlayer Whether to return pieces of only one player
     * @param player Player whose pieces should be returned
     * @return All pieces on board that satisfact the given criteria
     */
    private List<ChessPiece> getMatchingPieces(boolean checkPlayer, int player){
        ArrayList<ChessPiece> piecesList = new ArrayList<>();

        for(int rank=1;rank<=SIZE;rank++){
            for(int file=1;file<=SIZE;file++){
                ChessPiece currentPiece = chessBoard[rank][file];
                if(currentPiece != null && (currentPiece.getPlayer() == player || !checkPlayer) ){
                    //If checkPlayer is false, the second condition is always true
                    piecesList.add(chessBoard[rank][file]);
                }
            }
        }
        return piecesList;
    }

    private boolean validateKingSafety(ChessMove move){
        int player = move.getPiece().getPlayer();

        int secondPlayer = player==0 ? 1 : 0; //We want to look at the board from the perspective of the second player, asking whether he can take the king now
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

        ChessPiece whatWasThere = chessBoard[rank][file]; //So that we can

        chessBoard[rank][file] = move.getPiece(); //Now we "move" the figure
        chessBoard[previousRank][previousFile] = null;

        try{
            getLegalMoves(secondPlayer);
        }
        catch (KingCanBeTaken e){
            return false;
        }
        finally {
            //We revert the state of the board to the previous status
            chessBoard[previousRank][previousFile] = move.getPiece();
            chessBoard[rank][file] = whatWasThere;
        }
        return true;
    }

    private boolean castleValidation(ChessMove move){
        return true;
    }

    private boolean pawnMoveValidation(ChessMove move){
        //Because pawns have somewhat weird moving possibilities, validation of their moves is inside another function
        return true;
    }

    private boolean validateMove(ChessMove move){
        Field currentPosition = move.getPiece().getPosition();
        Field newPosition = move.getField();

        if(move.getPiece().getKind() == ChessPieceKind.PAWN){
            return pawnMoveValidation(move);
        }

        if(move.getClass() == Castle.class){
            //castling is tricky to validate, so I'm going to extract it to another function
            return castleValidation(move);
        }

        if(chessBoard[currentPosition.rank()][currentPosition.file()] != move.getPiece()){
            //We've got a discrepancy in the data, as move references other piece than it should be referencing
            /*System.err.println(move.getPiece().getKind());
            System.err.println(currentPosition.file() + " " + currentPosition.rank());
            System.err.println(chessBoard[currentPosition.rank()][currentPosition.file()]);*/
            throw new BoardDiscrepancy();
        }


        //We will initially check if the path isn't obstructed

        if(move.getPiece().getKind() != ChessPieceKind.KNIGHT){
            //First, we will validate the path itself (not including the last spot)
            //Because of that, we don't calculate the knight as it sort of "jumps"

            int rankDelta = newPosition.rank() - currentPosition.rank();
            int fileDelta = newPosition.file() - currentPosition.file();

            int rankVector = Integer.compare(rankDelta, 0);
            int fileVector = Integer.compare(fileDelta, 0);

            int iterRank = currentPosition.rank();
            int iterFile = currentPosition.file();

            while(iterRank+1 != newPosition.rank() && iterFile+1 != newPosition.file()){

                if(chessBoard[iterRank][iterFile] != null){
                    //Something's on our way, that means that the move is invalid
                    return false;
                }

                iterRank+=rankVector;
                iterFile+=fileVector;
            }
        }

        ChessPiece figureOnNewPosition = chessBoard[newPosition.rank()][newPosition.file()];

        if(figureOnNewPosition != null && figureOnNewPosition.getPlayer() == move.getPiece().getPlayer()){
            //On the position we'd like to move into stands allied figure (thus, we cannot take it and the move is invalid)
            return false;
        }

        if(figureOnNewPosition != null && figureOnNewPosition.getKind() == ChessPieceKind.KING){
            //waaaaaaait, we can actually take the enemy king?
            throw new KingCanBeTaken();
        }

        //Seems like the path for our piece is ok
        //Now there is king's safety to be validated

        if(!validateKingSafety(move)){
            return false;
        }

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

    @Override
    public List<ChessMove> getLegalMoves(int player) {
        List<ChessPiece> playersPieces = getPieces(player);

        List<ChessMove> allPlayersLegalMoves = new ArrayList<>();

        for(ChessPiece currentPiece : playersPieces){
            List<ChessMove> someLegalMoves = getLegalMoves(player, currentPiece);
            allPlayersLegalMoves.addAll(someLegalMoves);
        }

        return allPlayersLegalMoves;
    }

    @Override
    public List<ChessMove> getLegalMoves(int player, ChessPiece piece) {
        //Please note that this function is not particularly effective and was never meant to be.

        if(piece.getPlayer() != player){
            throw new PlayerDiscrepancy();
        }

        List<ChessMove> potentialMoves = piece.getPotentialMoves();
        List<ChessMove> legalMoves = new ArrayList<>();

        for(ChessMove potentialMove : potentialMoves){
            if(validateMove(potentialMove)){
                legalMoves.add(potentialMove);
            }
        }

        return legalMoves;
    }

    @Override
    public List<ChessPiece> makeMove(int player, ChessMove move) {
        return null;
    }

    @Override
    public int getPlayerCount() {
        return 2; //It's always going to be that way in classical chess
    }
}
