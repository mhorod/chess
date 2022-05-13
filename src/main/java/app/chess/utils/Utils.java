package app.chess.utils;
import app.chess.*;
import app.core.game.*;

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
}
