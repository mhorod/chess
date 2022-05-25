package app.ui.minesweeper;

import app.minesweeper.MinesweeperPieceKind;
import javafx.scene.image.Image;

import java.util.Map;

import static app.minesweeper.MinesweeperPieceKind.Type.NUMBER;

public class MinesweeperImages {

    private static final Map<MinesweeperPieceKind, Image> pieces = Map.ofEntries();

    public static Image getPieceImage(MinesweeperPieceKind pieceKind) {
        if (pieceKind.type == NUMBER)
            return new Image(
                    MinesweeperImages.class.getResource("/img/minesweeper/" + pieceKind.number + ".png").toString());
        else {
            var name = pieceKind.type.toString().toLowerCase();
            return new Image(MinesweeperImages.class.getResource("/img/minesweeper/" + name + ".png").toString());

        }
    }
}
