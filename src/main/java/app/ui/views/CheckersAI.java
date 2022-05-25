package app.ui.views;

import app.ai.dumb.DumbPlayer;
import app.checkers.Checkers;
import app.checkers.CheckersMove;
import app.checkers.CheckersPiece;
import app.core.interactor.InteractiveGame;
import app.core.interactor.Spectator;
import app.ui.Style;
import app.ui.board.boards.NormalBoard;
import app.ui.checkers.CheckersConnector;
import app.ui.menu.DerpyButton;
import app.ui.menu.MenuContainer;
import app.utils.pieceplayer.StandalonePiecePlayer;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class CheckersAI extends View {

    public CheckersAI(ViewContainer container) {
        super(container);

        var checkers = new Checkers();
        var game = new InteractiveGame<>(checkers);

        var player = new StandalonePiecePlayer<>(game, 0);
        var ai = new DumbPlayer<CheckersMove, CheckersPiece>();

        game.connectPlayer(1, ai);
        game.connectSpectator(ai);

        var playerBoard = new NormalBoard<CheckersPiece>(40, container.getGameStyle());
        CheckersConnector.connect(playerBoard, player);

        setAlignment(Pos.CENTER);
        getChildren().add(playerBoard);

        var gameStatus = new Text();
        gameStatus.setTextAlignment(TextAlignment.CENTER);
        gameStatus.setFont(new Font(DerpyButton.font.getFamily(), 40));

        var restartButton = new DerpyButton("Play again!", container.getGameStyle().whitePiece);
        restartButton.setVisible(false);
        restartButton.setOnMouseClicked(e -> {
            container.changeView(new CheckersAI(container));
        });

        var spectator = new Spectator<CheckersMove, CheckersPiece>() {
            @Override
            public void update(int player, CheckersMove move, List<CheckersPiece> changedPieces) {
                switch (checkers.getResult()) {

                    case WHITE_WON -> {
                        gameStatus.setText("You win!");
                        restartButton.setVisible(true);
                    }
                    case BLACK_WON -> {
                        gameStatus.setText("You lose!");
                        restartButton.setVisible(true);
                    }
                }
            }
        };

        game.connectSpectator(spectator);

        setAlignment(Pos.CENTER);

        getChildren().add(gameStatus);

        var returnButton = new DerpyButton("Return", container.getGameStyle().whitePiece);
        returnButton.setOnMouseClicked(e -> {
            var menu = new MenuContainer(container, container.getGameStyle());
            changeView(menu);
            menu.changeMenu(new MainMenu(menu));
        });
        getChildren().add(returnButton);


        getChildren().add(restartButton);
        //setFillHeight(true);
        setSpacing(10);

    }

    @Override
    protected void setGameStyle(Style style) {

    }
}