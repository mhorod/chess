package app.ui.views;

import app.core.interactor.InteractiveGame;
import app.minesweeper.Minesweeper;
import app.minesweeper.MinesweeperPiece;
import app.ui.board.boards.NormalBoard;
import app.ui.menu.DerpyButton;
import app.ui.menu.MenuContainer;
import app.ui.minesweeper.MinesweeperConnector;
import app.utils.pieceplayer.StandalonePiecePlayer;
import javafx.geometry.Pos;

public class MinesweeperView extends View {

    public MinesweeperView(ViewContainer container) {
        super(container);

        var game = new InteractiveGame<>(new Minesweeper());

        var player = new StandalonePiecePlayer<>(game, 0);


        var board = new NormalBoard<MinesweeperPiece>(40, container.getGameStyle());
        MinesweeperConnector.connect(board, player);

        setAlignment(Pos.CENTER);
        getChildren().add(board);

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
