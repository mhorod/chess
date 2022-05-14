package app.chess.utils;
import app.chess.*;
import app.chess.moves.*;
import app.core.game.*;

import static app.chess.Chess.SIZE;

public final class Utils {
    private Utils(){
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
     * @param move
     * @return Assuming that castling is possible, returns the position on which the rook should be located. Makes
     *         ABSOLUTELY NO GUARANTEE that castling is legal.
     */
    public static Field getRookPositionBasedOnCastling(Castle move, ChessPiece[][] board) {
        final int currentRank = move.getPiece().getPosition().rank();
        final int currentFile = move.getPiece().getPosition().file();

        final int newRank = move.getField().rank();
        final int newFile = move.getField().file();

        final boolean kingSideCastling = newFile - currentFile > 0;  //If the file is increasing, this means (both for white and black) that we are castling king side

        final int rookRank = move.getPiece().getPlayer() == 0 ? 1 : 8; //White has rooks on first rank, black on eighth
        final int rookFile = kingSideCastling ? 8 : 1;

        return new Field(rookRank, rookFile);
    }

    public static boolean fieldIsValid(Field toValidate) {
        return toValidate.rank() <= SIZE && toValidate.file() <= SIZE && toValidate.rank() > 0 && toValidate.file() > 0;
    }

    public static ChessPiece getPieceByField(Field field, ChessPiece[][] board){
        return board[field.rank()][field.file()];
    }

}
