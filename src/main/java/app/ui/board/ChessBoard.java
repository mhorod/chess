package app.ui.board;

import app.chess.moves.ChessMove;
import app.chess.pieces.ChessPiece;
import app.chess.pieces.ChessPieceKind;
import app.ui.Style;
import app.utils.pieceplayer.PiecePlayer;

public class ChessBoard extends GraphicalBoard<ChessPiece> {
    Board<ChessPiece> board;

    public ChessBoard(double fieldSize, Style style, PiecePlayer<ChessMove, ChessPiece> player) {
        super(fieldSize, style);
        board = new Board<>(
                player,
                this,
                () -> new GraphicalChessPiece(ChessPieceKind.KING, style.whitePiece, style.blackPiece)
        );
    }
}
