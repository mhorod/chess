package app.ui.views;

import app.chess.Chess;
import app.chess.ChessBoard;
import app.chess.ChessPiece;
import app.core.interactor.InteractiveGame;
import app.ui.board.boards.NormalBoard;
import app.ui.chess.ChessConnector;
import app.ui.menu.DerpyButton;
import app.ui.menu.MenuContainer;
import app.utils.pieceplayer.HotSeatPlayer;
import javafx.geometry.Pos;

public class ChessHotseat extends View {

    public ChessHotseat(ViewContainer container) {
        super(container);

        Chess chess = new Chess(new ChessBoard());
        var game = new InteractiveGame<>(chess);

        var hotSeatPlayer = new HotSeatPlayer<>(game, game);

        var hotSeatBoard = new NormalBoard<ChessPiece>(40, container.getGameStyle());
        ChessConnector.connect(hotSeatBoard, hotSeatPlayer);

        setAlignment(Pos.CENTER);
        getChildren().add(hotSeatBoard);

        var returnButton = new DerpyButton("Return", container.getGameStyle().whitePiece);
        getChildren().add(returnButton);
        returnButton.setOnMouseClicked(e -> {
            var menu = new MenuContainer(container, container.getGameStyle());
            changeView(menu);
            menu.changeMenu(new MainMenu(menu));
        });
        //setFillHeight(true);
        setSpacing(100);

    }
}
