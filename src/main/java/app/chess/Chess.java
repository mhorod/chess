package app.chess;

import app.core.game.*;

import java.util.*;

/**
 * TODO: Extend Game with appropriate types and implement chess logic
 */
public class Chess implements Game<ChessMove, ChessPiece> {

    static final int SIZE = 8;
    ChessPiece[][] chessBoard = new ChessPiece[SIZE+1][SIZE+1]; //We are adding 1 because we will start counting at 1
    //This is a little uncommon, but in Chess such numeration is also starting from 1 and it will hopefully create less bugs if we stick to this convention

    private boolean blackToMove = false;

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
            chessBoard[whiteRank][1] = new Rook(new Field(whiteRank,1),false);
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
    Chess(){
        initializeBoard();
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
        return null;
    }

    @Override
    public List<ChessMove> getLegalMoves(int player, ChessPiece piece) {
        return null;
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
