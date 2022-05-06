package app.ui;

import app.chess.pieces.ChessPieceKind;
import javafx.scene.image.Image;

import java.util.Map;

import static app.chess.pieces.ChessPieceKind.*;

public class ImageManager {
    public static final Image logo = new Image(ImageManager.class.getResource("/img/logo.png").toString());
    public static final Image button = new Image(ImageManager.class.getResource("/img/button.png").toString());
    private static final Map<ChessPieceKind, Image> pieces = Map.ofEntries(
            Map.entry(KNIGHT, new Image(ImageManager.class.getResource("/img/knight.png").toString())),
            Map.entry(KING, new Image(ImageManager.class.getResource("/img/king.png").toString())),
            Map.entry(ROOK, new Image(ImageManager.class.getResource("/img/rook.png").toString())),
            Map.entry(BISHOP, new Image(ImageManager.class.getResource("/img/bishop.png").toString())),
            Map.entry(QUEEN, new Image(ImageManager.class.getResource("/img/queen.png").toString())),
            Map.entry(PAWN, new Image(ImageManager.class.getResource("/img/pawn.png").toString()))
    );

    private static final Map<ChessPieceKind, Image> piecesHQ = Map.ofEntries(
            Map.entry(BISHOP, new Image(ImageManager.class.getResource("/img/bishopHQ.png").toString()))
    );

    public static Image getPieceImage(ChessPieceKind pieceType) {
        return pieces.get(pieceType);
    }

    public static Image getPieceImageHQ(ChessPieceKind pieceType) {
        return piecesHQ.get(pieceType);
    }
}
