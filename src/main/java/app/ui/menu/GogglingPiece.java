package app.ui.menu;

import app.chess.pieces.ChessPieceKind;
import app.ui.ImageManager;
import app.ui.utils.ColoredImage;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GogglingPiece extends Pane {
    Circle eye = new Circle(5);
    Circle outerEye = new Circle(10);
    double eyeX = 0.35, eyeY = 0.26;

    public GogglingPiece(Color color) {
        var piece = new ColoredImage(ImageManager.getPieceImageHQ(ChessPieceKind.BISHOP), color);
        System.out.println(piece.getImage());
        piece.setFitWidth(100);
        piece.setPreserveRatio(true);
        piece.setViewport(new Rectangle2D(30 * 4, 0, 72 * 4, 512));


        outerEye.setFill(Color.WHITE);
        outerEye.setStroke(Color.BLACK);
        outerEye.setStrokeWidth(1);
        getChildren().add(piece);


        Platform.runLater(() -> {
            outerEye.setCenterX(getWidth() * eyeX);
            outerEye.setCenterY(getHeight() * eyeY);
            eye.setCenterX(getWidth() * eyeX);
            eye.setCenterY(getHeight() * eyeY);
            getChildren().add(outerEye);
            getChildren().add(eye);
        });


    }

    public void update(double mouseX, double mouseY) {
        var mousePos = sceneToLocal(mouseX, mouseY);
        var eyePos = new Point2D(getWidth() * eyeX, getHeight() * eyeY);
        var dir = mousePos.subtract(eyePos);
        if (dir.magnitude() > 5)
            dir = dir.normalize().multiply(5);
        outerEye.setCenterX(getWidth() * eyeX);
        outerEye.setCenterY(getHeight() * eyeY);
        eye.setCenterX(eyePos.getX() + dir.getX());
        eye.setCenterY(eyePos.getY() + dir.getY());
    }
}
