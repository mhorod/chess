package app.chess.rules.ruleset;

import app.chess.ChessPiece;
import app.chess.moves.ChessMove;
import app.chess.pieces.ChessPieceKind;
import app.chess.rules.Rule;

public class PawnGoingForwardCantTakePieces implements Rule {
    @Override
    public boolean canBeAppliedTo(ChessMove move) {
        //Only applies to pawns that go forward, pretty much as it is in the title here
        if (move.getPiece().getKind() != ChessPieceKind.PAWN)
            return false;

        int currentFile = move.getPiece().getPosition().file();
        int newFile = move.getField().file();

        return currentFile == newFile;
    }

    @Override
    public boolean validate(ChessMove move, ChessPiece[][] board) {
        if (!canBeAppliedTo(move))
            return true;
        else
            return board[move.getField().rank()][move.getField().file()] == null;
    }
}
