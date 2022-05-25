package app.ui.views;

import app.chess.Chess;
import app.chess.ChessPiece;
import app.chess.ChessState;
import app.chess.board.StandardChessBoard;
import app.chess.moves.ChessMove;
import app.core.interactor.InteractiveGame;
import app.core.interactor.Spectator;
import app.ui.board.boards.NormalBoard;
import app.ui.chess.ChessConnector;
import app.ui.menu.DerpyButton;
import app.ui.menu.MenuContainer;
import app.utils.pieceplayer.HotSeatPlayer;
import javafx.geometry.Pos;

import java.util.List;

public class ChessHotseat extends View {

    public ChessHotseat(ViewContainer container) {
        super(container);

        Chess chess = new Chess(new StandardChessBoard());
        var game = new InteractiveGame<>(chess);

        var hotSeatPlayer = new HotSeatPlayer<>(game, game);

        var hotSeatBoard = new NormalBoard<ChessPiece>(40, container.getGameStyle());
        ChessConnector.connect(hotSeatBoard, hotSeatPlayer);

        var spectator = new Spectator<ChessMove, ChessPiece>() {
            @Override
            public void update(int player, ChessMove move, List<ChessPiece> changedPieces) {
                if (chess.getState(1) == ChessState.MATED) {
                    System.out.println("You win!");
                }
                if (chess.getState(0) == ChessState.MATED) {
                    System.out.println("You lose!");
                }
                if (chess.getState(1 - player) == ChessState.DRAW) {
                    System.out.println("It's a draw!");
                }
            }
        };

        game.connectSpectator(spectator);

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
