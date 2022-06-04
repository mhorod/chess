package app.ui.minesweeper;

import app.core.game.moves.Move;
import app.minesweeper.MinesweeperPiece;
import app.ui.board.Board;
import app.ui.board.GraphicalBoard;
import app.utils.pieceplayer.PieceSocket;

public class MinesweeperConnector {
    public static void connect(
            GraphicalBoard<MinesweeperPiece> graphicalBoard,
            PieceSocket<? extends Move<MinesweeperPiece>, MinesweeperPiece> player
    ) {
        new Board<>(player, graphicalBoard,
                    (piece) -> new GraphicalMinesweeperPiece(piece, graphicalBoard.style.whitePiece,
                                                             graphicalBoard.style.blackPiece));
    }
}
