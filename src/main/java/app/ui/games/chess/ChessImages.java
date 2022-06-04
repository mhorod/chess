package app.ui.games.chess;

import app.chess.pieces.ChessPieceKind;
import app.ui.Images;
import javafx.scene.image.Image;

import java.util.Map;

import static app.chess.pieces.ChessPieceKind.*;

public class ChessImages {

    private static final Map<ChessPieceKind, Image> pieces = Map.ofEntries(
            Map.entry(KNIGHT, new Image(Images.class.getResource("/img/knight.png").toString())),
            Map.entry(KING, new Image(Images.class.getResource("/img/king.png").toString())),
            Map.entry(ROOK, new Image(Images.class.getResource("/img/rook.png").toString())),
            Map.entry(BISHOP, new Image(Images.class.getResource("/img/bishop.png").toString())),
            Map.entry(QUEEN, new Image(Images.class.getResource("/img/queen.png").toString())),
            Map.entry(PAWN, new Image(Images.class.getResource("/img/pawn.png").toString())));

    public static Image getPieceImage(ChessPieceKind pieceType) {
        return pieces.get(pieceType);
    }
}
