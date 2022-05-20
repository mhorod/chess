package app.chess.rules.ruleset;

import app.chess.*;
import app.chess.moves.*;
import app.chess.utils.*;
import app.core.game.*;

public class NothingOnTheRoadOfTheKingIsUnderAttack extends CastlingRules {

    @Override
    public boolean validate(ChessMove move, ChessPiece[][] board) {
        if (!canBeAppliedTo(move)) {
            return true;
        }

        final int currentRank = move.getPiece().getPosition().rank();
        final int currentFile = move.getPiece().getPosition().file();

        final int newFile = move.getField().file();

        final boolean kingSideCastling = newFile - currentFile > 0;  //If the file is increasing, this means (both for white and black) that we are castling king side

        int multiplier = kingSideCastling ? 1 : -1;
        int enemyPlayer = move.getPiece().getPlayer() == 0 ? 1 : 0;

        for (int i = 1; i <= 2; i++) {
            Field toValidate = new Field(currentRank, currentFile + i * multiplier);
            if (Utils.fieldIsUnderAttack(enemyPlayer, toValidate, board)) {
                return false;
            }
        }

        return true;
    }
}
