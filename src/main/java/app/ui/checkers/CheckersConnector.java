package app.ui.checkers;

import app.checkers.CheckersPiece;
import app.ui.board.Board;
import app.ui.board.GraphicalBoard;
import app.utils.pieceplayer.PiecePlayer;

public class CheckersConnector {
    public static void connect(GraphicalBoard<CheckersPiece> graphicalBoard, PiecePlayer<?, CheckersPiece> player) {
        new Board<>(
                player,
                graphicalBoard,
                (piece) -> new GraphicalCheckersPiece(piece, graphicalBoard.style.whitePiece, graphicalBoard.style.blackPiece)
        );
    }
}
