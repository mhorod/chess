package app.chess.rules.ruleset;

import app.chess.*;
import app.chess.moves.*;

public class KingAndRookCantMoveBeforeCastling extends CastlingRules {

    @Override
    public boolean validate(ChessMove move, ChessPiece[][] board) {
        if (!canBeAppliedTo(move)) {
            return true;
        }

        final int currentFile = move.getPiece().getPosition().file();

        final int newFile = move.getField().file();

        //If the file is increasing, this means (both for white and black) that we are castling king side
        final boolean kingSideCastling = newFile - currentFile > 0;

        //White has rooks on first rank, black on eighth
        final int rookRank = move.getPiece().getPlayer() == 0 ? 1 : 8;
        final int rookFile = kingSideCastling ? 8 : 1;

        // You might ask why I've decided to call that variable "rook" when I have no guarantee that such piece is a rook.
        // Well, I'm checking that in the if that is below
        var rook = board[rookRank][rookFile];

        //There's no rook to even, you know, castle with
        //Or something that on that place cannot castle
        if (rook == null || !RulesetPieceConverter.convert(rook).canParticipateInCastling())
            return false;

        //You might ask: Why is that sufficient? Well, only rooks and kings can ever participate in castling, and any move invalidates that right.
        //That pretty much means that whatever is standing on that place has been standing there for all of the game, so it is eligible for castling

        //It'd be great to check what's going on with our king
        //We have a guarantee that the piece that tries to castle is king, because we are checking that in Castle constructor

        return RulesetPieceConverter.convert(move.getPiece()).canParticipateInCastling();
    }
}
