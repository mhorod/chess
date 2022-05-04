package app.ui.board;

import app.chess.pieces.AbstractChessPiece;
import app.chess.pieces.ChessPieceKind;
import app.core.game.Field;
import app.ui.Style;
import app.ui.board.state.Machine;
import app.ui.utils.Position;
import app.utils.pieceplayer.InteractivePiece;
import app.utils.pieceplayer.PiecePlayer;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Board<M extends app.core.game.moves.Move<P>, P extends AbstractChessPiece> extends Pane {
    public List<Piece<M, P>> pieces;
    double fieldSize;
    Style style;
    GraphicalField[][] graphicalFields = new GraphicalField[8][8];
    Machine<Piece<M, P>> stateMachine;

    public Board(PiecePlayer<M, P> player, double fieldSize, Style style) {
        this.style = style;
        this.fieldSize = fieldSize;
        setMaxWidth(8 * fieldSize + 25);
        setMaxHeight(8 * fieldSize + 25);
        setEffect(new DropShadow());
        GridPane grid = new GridPane();

        this.stateMachine = new Machine<>(this);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if ((x + y) % 2 == 0)
                    graphicalFields[x][y] = new GraphicalField(style.blackField, style.blackFieldCircle, fieldSize,
                                                               new Position(25 + x * fieldSize + fieldSize / 2,
                                                                            y * fieldSize + fieldSize / 2));
                else
                    graphicalFields[x][y] = new GraphicalField(style.whiteField, style.whiteFieldCircle, fieldSize,
                                                               new Position(25 + x * fieldSize + fieldSize / 2,
                                                                            y * fieldSize + fieldSize / 2));
                final Field field = new Field(y + 1, 8 - x);
                graphicalFields[x][y].setOnMousePressed(e -> stateMachine.onFieldClick(field));
                graphicalFields[x][y].setOnMouseEntered(e -> stateMachine.onFieldMouseEntered(field));
                grid.add(graphicalFields[x][y], x + 1, y + 1);
            }
        }

        for (int y = 0; y < 8; y++) {
            Label label;
            String text = String.valueOf(y + 1);
            if (y % 2 == 1)
                label = new Label(text, style.font, style.borderBlack, style.borderText);
            else
                label = new Label(text, style.font, style.borderWhite, style.borderText);
            grid.add(label, 0, y + 1);
        }

        for (int x = 0; x < 8; x++) {
            Label label;
            String text = String.valueOf((char) (7 - x + 65));
            if (x % 2 == 0)
                label = new Label(text, style.font, style.borderBlack, style.borderText);
            else
                label = new Label(text, style.font, style.borderWhite, style.borderText);
            grid.add(label, x + 1, 9);
        }


        //Color white = style.whitePiece;
        Color black = style.blackPiece;
        getChildren().add(grid);

        var board = this;
        var supplier = new Supplier<InteractivePiece<M, P>>() {
            final List<Piece<M, P>> pieces = new ArrayList<>();

            public InteractivePiece<M, P> get() {
                var piece = new Piece<>(new GraphicalPiece(ChessPieceKind.KING, graphicalFields[0][0], black),
                                        stateMachine, board);
                pieces.add(piece);
                return piece.logical;
            }
        };

        player.connectPieces(supplier);
        pieces = supplier.pieces;

        for (var p : supplier.pieces) {
            p.logical.update();
            getChildren().add(p.graphical);
        }
    }

    public GraphicalField getGraphicalField(Field field) {
        return graphicalFields[8 - field.file()][field.rank() - 1];
    }
}
