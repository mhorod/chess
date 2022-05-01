package app.ui;

import javafx.scene.image.Image;

import java.util.Map;

import static app.ui.PieceType.*;

public class ImageManager {
    private static final Map<PieceType, Image> pieces = Map.ofEntries(
            Map.entry(KNIGHT, new Image(ImageManager.class.getResource("/img/knight.png").toString())),
            Map.entry(KING, new Image(ImageManager.class.getResource("/img/king.png").toString())),
            Map.entry(ROOK, new Image(ImageManager.class.getResource("/img/rook.png").toString())),
            Map.entry(BISHOP, new Image(ImageManager.class.getResource("/img/bishop.png").toString())),
            Map.entry(QUEEN, new Image(ImageManager.class.getResource("/img/queen.png").toString())),
            Map.entry(PAWN, new Image(ImageManager.class.getResource("/img/pawn.png").toString()))
    );

    public static Image getPieceImage(PieceType pieceType) {
        return pieces.get(pieceType);
    }
}
