package app.ui.views;

import app.chess.Chess;
import app.chess.ChessBoard;
import app.chess.ChessPiece;
import app.core.interactor.InteractiveGame;
import app.ui.GameContainer;
import app.ui.board.boards.InvertedBoard;
import app.ui.board.boards.NormalBoard;
import app.ui.chess.ChessConnector;
import app.utils.pieceplayer.HotSeatPlayer;
import app.utils.pieceplayer.PieceSpectator;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class ChessHotseat extends HBox {
    GameContainer container;

    public ChessHotseat(GameContainer container) {
        super();
        this.container = container;

        Chess chess = new Chess(new ChessBoard());
        var game = new InteractiveGame<>(chess);

        var hotSeatPlayer = new HotSeatPlayer<>(game, game);
        var pieceSpectator = new PieceSpectator<>(game, game);

        var hotSeatBoard = new NormalBoard<ChessPiece>(40, container.getStyle());
        ChessConnector.connect(hotSeatBoard, hotSeatPlayer);

        var spectatorBoard = new InvertedBoard<ChessPiece>(40, container.getStyle());
        ChessConnector.connect(spectatorBoard, pieceSpectator);


        setAlignment(Pos.CENTER);
        getChildren().add(hotSeatBoard);
        getChildren().add(spectatorBoard);
        setFillHeight(true);
        setSpacing(100);

    }
}
