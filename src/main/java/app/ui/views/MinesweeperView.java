package app.ui.views;

import app.core.interactor.InteractiveGame;
import app.core.interactor.Spectator;
import app.minesweeper.Minesweeper;
import app.minesweeper.MinesweeperMove;
import app.minesweeper.MinesweeperPiece;
import app.ui.board.boards.NormalBoard;
import app.ui.menu.DerpyButton;
import app.ui.menu.MenuContainer;
import app.ui.minesweeper.MinesweeperConnector;
import app.ui.styles.Style;
import app.utils.pieceplayer.StandalonePiecePlayer;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class MinesweeperView extends View {

    public MinesweeperView(ViewContainer container) {
        super(container);

        var minesweeper = new Minesweeper();
        var game = new InteractiveGame<>(minesweeper);

        var player = new StandalonePiecePlayer<>(game, 0);


        var board = new NormalBoard<MinesweeperPiece>(40, container.getGameStyle());
        MinesweeperConnector.connect(board, player);

        setAlignment(Pos.CENTER);
        getChildren().add(board);

        var returnButton = new DerpyButton("Return", container.getGameStyle().whitePiece);
        returnButton.setOnMouseClicked(e -> {
            var menu = new MenuContainer(container, container.getGameStyle());
            changeView(menu);
            menu.changeMenu(new MainMenu(menu));
        });

        var gameStatus = new Text();
        gameStatus.setTextAlignment(TextAlignment.CENTER);
        gameStatus.setFont(new Font(DerpyButton.font.getFamily(), 40));

        var restartButton = new DerpyButton("Play again!", container.getGameStyle().whitePiece);
        restartButton.setVisible(false);
        restartButton.setOnMouseClicked(e -> {
            container.changeView(new MinesweeperView(container));
        });

        var spectator = new Spectator<MinesweeperMove, MinesweeperPiece>() {
            @Override
            public void update(int player, MinesweeperMove move, List<MinesweeperPiece> changedPieces) {
                switch (minesweeper.getState()) {

                    case WON -> {
                        gameStatus.setText("You win!");
                        restartButton.setVisible(true);
                    }
                    case LOST -> {
                        gameStatus.setText("You lose!");
                        restartButton.setVisible(true);
                    }
                }
            }
        };
        game.connectSpectator(spectator);

        getChildren().add(gameStatus);
        getChildren().add(returnButton);
        getChildren().add(restartButton);

        //setFillHeight(true);
        setSpacing(10);
    }

    @Override
    protected void setGameStyle(Style style) {

    }
}
