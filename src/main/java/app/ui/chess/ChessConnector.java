package app.ui.chess;

import app.chess.ChessPiece;
import app.core.game.moves.Move;
import app.ui.board.Board;
import app.ui.board.GraphicalBoard;
import app.utils.pieceplayer.PiecePlayer;

public class ChessConnector {
    public static void connect(GraphicalBoard<ChessPiece> graphicalBoard, PiecePlayer<? extends Move<ChessPiece>, ChessPiece> player) {
        new Board<>(
                player,
                graphicalBoard,
                (piece) -> new GraphicalChessPiece(piece, graphicalBoard.style.whitePiece, graphicalBoard.style.blackPiece)
        );
    }
}
