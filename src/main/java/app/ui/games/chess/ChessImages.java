package app.ui.games.chess;

import app.chess.pieces.ChessPieceKind;
import app.ui.utils.Images;
import javafx.scene.image.Image;

import java.util.Map;

import static app.chess.pieces.ChessPieceKind.*;

public class ChessImages {

    private static final Map<ChessPieceKind, Image> pieces = Map.ofEntries(
            Map.entry(KNIGHT, new Image(Images.class.getResource("/img/chess/knight.png").toString())),
            Map.entry(KING, new Image(Images.class.getResource("/img/chess/king.png").toString())),
            Map.entry(ROOK, new Image(Images.class.getResource("/img/chess/rook.png").toString())),
            Map.entry(BISHOP, new Image(Images.class.getResource("/img/chess/bishop.png").toString())),
            Map.entry(QUEEN, new Image(Images.class.getResource("/img/chess/queen.png").toString())),
            Map.entry(PAWN, new Image(Images.class.getResource("/img/chess/pawn.png").toString())));

    public static Image getPieceImage(ChessPieceKind pieceType) {
        return pieces.get(pieceType);
    }
}
