package app.ui;

import app.ai.dumb.DumbPlayer;
import app.chess.Chess;
import app.chess.ChessPiece;
import app.chess.board.StandardChessBoard;
import app.chess.moves.ChessMove;
import app.core.interactor.InteractiveGame;
import app.ui.board.boards.InvertedBoard;
import app.ui.board.boards.NormalBoard;
import app.ui.chess.ChessConnector;
import app.ui.styles.ChessCom;
import app.ui.styles.CutePink;
import app.ui.styles.Lichess;
import app.ui.styles.Rainbow;
import app.utils.pieceplayer.PieceSpectator;
import app.utils.pieceplayer.StandalonePiecePlayer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Map;

public class App extends Application {
    @Override
    public void start(Stage stage) {

        Map<String, Style> styles = Map.of("Cute Pink", new CutePink(), "Lichess", new Lichess(), "Chess.com",
                                           new ChessCom(), "Rainbow", new Rainbow());
        Style style = styles.get("Cute Pink");

        Chess chess = new Chess(new StandardChessBoard());
        var game = new InteractiveGame<>(chess);

        var player = new StandalonePiecePlayer<>(game, 0);
        var pieceSpectator = new PieceSpectator<>(game, game);

        var ai = new DumbPlayer<ChessMove, ChessPiece>();
        game.connectPlayer(1, ai);
        game.connectSpectator(ai);


        var hotSeatBoard = new NormalBoard<ChessPiece>(40, style);
        ChessConnector.connect(hotSeatBoard, player);

        var spectatorBoard = new InvertedBoard<ChessPiece>(40, style);
        ChessConnector.connect(spectatorBoard, pieceSpectator);

        HBox pane = new HBox();
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(hotSeatBoard);
        pane.getChildren().add(spectatorBoard);
        pane.setFillHeight(true);
        pane.setSpacing(100);
        Scene scene = new Scene(pane, 1024, 800, true);
        scene.setFill(Color.web("#222"));
        stage.setScene(scene);
        stage.setTitle("Epic chess");
        stage.show();
    }
}