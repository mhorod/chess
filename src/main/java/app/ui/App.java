package app.ui;

import app.chess.Chess;
import app.chess.ChessPiece;
import app.chess.StandardChessBoard;
import app.core.interactor.InteractiveGame;
import app.ui.board.boards.InvertedBoard;
import app.ui.board.boards.NormalBoard;
import app.ui.chess.ChessConnector;
import app.utils.pieceplayer.HotSeatPlayer;
import app.utils.pieceplayer.PieceSpectator;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Style style = new Style() {
            {
                whitePiece = Color.color(0.9, 0.9, 0.9);
                blackPiece = Color.web("#FF8CE1");

                whiteField = Color.web("#FFC8E5");
                blackField = Color.web("#EA5CB1");

                borderWhite = Color.web("#75234f");
                borderBlack = Color.web("#75234f");

                borderText = Color.color(0.9, 0.9, 0.9);
                whiteFieldCircle = Color.web("#00bbfa");
                blackFieldCircle = Color.web("#47e0ff");
                font = Font.loadFont(App.class.getResource("/fonts/regular.otf").toExternalForm(), 20);
            }
        };


        Chess chess = new Chess(new StandardChessBoard());
        var game = new InteractiveGame<>(chess);

        var hotSeatPlayer = new HotSeatPlayer<>(game, game);
        var pieceSpectator = new PieceSpectator<>(game, game);

        var hotSeatBoard = new NormalBoard<ChessPiece>(40, style);
        ChessConnector.connect(hotSeatBoard, hotSeatPlayer);

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