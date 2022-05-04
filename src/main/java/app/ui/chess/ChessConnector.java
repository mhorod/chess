package app.ui.chess;

import app.chess.pieces.ChessPiece;
import app.chess.pieces.ChessPieceKind;
import app.core.game.moves.Move;
import app.ui.board.Board;
import app.ui.board.GraphicalBoard;
import app.utils.pieceplayer.PiecePlayer;

public class ChessConnector {
    public static void connect(GraphicalBoard<ChessPiece> graphicalBoard, PiecePlayer<? extends Move<ChessPiece>, ChessPiece> player) {
        new Board<>(
                player,
                graphicalBoard,
                () -> new GraphicalChessPiece(ChessPieceKind.KING, graphicalBoard.style.whitePiece, graphicalBoard.style.blackPiece)
        );
    }
}
