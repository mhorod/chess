package app.ui.views;

import app.ai.dumb.DumbPlayer;
import app.chess.Chess;
import app.chess.ChessPiece;
import app.chess.board.StandardChessBoard;
import app.chess.moves.ChessMove;
import app.core.interactor.InteractiveGame;
import app.ui.Style;
import app.ui.board.boards.NormalBoard;
import app.ui.chess.ChessConnector;
import app.ui.menu.DerpyButton;
import app.ui.menu.MenuContainer;
import app.utils.pieceplayer.StandalonePiecePlayer;
import javafx.geometry.Pos;

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

    @Override
    protected void setGameStyle(Style style) {

    }
}
