package app.ui.views;

import app.ai.DumbPlayer;
import app.chess.Chess;
import app.chess.ChessPiece;
import app.chess.ChessState;
import app.chess.board.StandardChessBoard;
import app.chess.moves.ChessMove;
import app.core.interactor.InteractiveGame;
import app.core.interactor.Spectator;
import app.ui.board.boards.NormalBoard;
import app.ui.games.chess.ChessConnector;
import app.ui.menu.DerpyButton;
import app.ui.menu.MenuContainer;
import app.ui.menus.MainMenu;
import app.ui.styles.Style;
import app.utils.pieceplayer.StandalonePiecePlayer;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class ChessAI extends View {

    public ChessAI(ViewContainer container) {
        super(container);

        Chess chess = new Chess(new StandardChessBoard());
        var game = new InteractiveGame<>(chess);

        var player = new StandalonePiecePlayer<>(game, 0);
        var ai = new DumbPlayer<ChessMove, ChessPiece>();

        game.connectPlayer(1, ai);
        game.connectSpectator(ai);

        var playerBoard = new NormalBoard<ChessPiece>(40, container.getGameStyle());
        ChessConnector.connect(playerBoard, player);

        setAlignment(Pos.CENTER);
        getChildren().add(playerBoard);

        var gameStatus = new Text();
        gameStatus.setTextAlignment(TextAlignment.CENTER);
        gameStatus.setFont(new Font(DerpyButton.font.getFamily(), 40));

        var restartButton = new DerpyButton("Play again!", container.getGameStyle().whitePiece);
        restartButton.setVisible(false);
        restartButton.setOnMouseClicked(e -> {
            container.changeView(new ChessHotseat(container));
        });

        var spectator = new Spectator<ChessMove, ChessPiece>() {
            @Override
            public void update(int player, ChessMove move, List<ChessPiece> changedPieces) {
                if (chess.getState(1) == ChessState.MATED) {
                    gameStatus.setText("You win!");
                    restartButton.setVisible(true);
                }
                if (chess.getState(0) == ChessState.MATED) {
                    gameStatus.setText("You lose!");
                    restartButton.setVisible(true);

                }
                if (chess.getState(chess.getCurrentPlayer()) == ChessState.DRAW) {
                    gameStatus.setText("It's a draw!");
                    restartButton.setVisible(true);
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
