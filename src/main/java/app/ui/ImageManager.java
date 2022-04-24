package app.ui;

import javafx.scene.image.Image;

public class ImageManager {
    public static final Image KNIGHT = new Image(ImageManager.class.getResource("/img/knight.png").toString());
    public static final Image ROOK = new Image(ImageManager.class.getResource("/img/rook.png").toString());
    public static final Image PAWN = new Image(ImageManager.class.getResource("/img/pawn.png").toString());
    public static final Image BISHOP = new Image(ImageManager.class.getResource("/img/bishop.png").toString());
    public static final Image QUEEN = new Image(ImageManager.class.getResource("/img/queen.png").toString());
    public static final Image KING = new Image(ImageManager.class.getResource("/img/king.png").toString());
}
