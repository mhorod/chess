package app.ui;

import app.chess.pieces.ChessPieceKind;
import javafx.scene.image.Image;

import java.util.Map;

import static app.chess.pieces.ChessPieceKind.BISHOP;

public class Images {
    public static final Image logo = new Image(Images.class.getResource("/img/logo.png").toString());
    public static final Image button = new Image(Images.class.getResource("/img/button.png").toString());
    public static final Image longButton = new Image(Images.class.getResource("/img/long_button.png").toString());
    private static final Map<ChessPieceKind, Image> piecesHQ = Map.ofEntries(
            Map.entry(BISHOP, new Image(Images.class.getResource("/img/bishopHQ.png").toString())));


    public static Image getPieceImageHQ(ChessPieceKind pieceType) {
        return piecesHQ.get(pieceType);
    }
}
