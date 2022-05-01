package app.ui;

import app.chess.Chess;
import app.chess.moves.ChessMove;
import app.chess.pieces.ChessPiece;
import app.core.interactor.InteractiveGame;
import app.ui.board.Board;
import app.utils.pieceplayer.PiecePlayer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
                whiteFieldCircle = Color.web("#FF5CE1");
                blackFieldCircle = Color.web("#F0F0F0");
                font = Font.loadFont(App.class.getResource("/fonts/regular.otf").toExternalForm(), 20);
            }
        };


        Chess chess = new Chess();
        var game = new InteractiveGame<>(chess);
        var whitePlayer = new PiecePlayer<ChessMove, ChessPiece>();
        var blackPlayer = new PiecePlayer<ChessMove, ChessPiece>();
        game.connectPlayer(0, whitePlayer);
        game.connectPlayer(1, blackPlayer);

        Pane board1 = new Board<>(whitePlayer, 40, style);
        Pane board2 = new Board<>(blackPlayer, 40, style);

        HBox pane = new HBox();
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(board1);
        pane.getChildren().add(board2);
        pane.setFillHeight(true);
        pane.setSpacing(100);
        Scene scene = new Scene(pane, 1024, 800, true);
        scene.setFill(Color.web("#222"));
        stage.setScene(scene);
        stage.setTitle("Epic chess");
        stage.show();
    }
}