package app.ui.menu;

import app.chess.pieces.ChessPieceKind;
import app.ui.ImageManager;
import app.ui.utils.ColoredImage;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Elephant extends Pane {
    Circle eye = new Circle(5);
    Circle outerEye = new Circle(10);
    double eyeX = 0.34, eyeY = 0.29;
    ElephantSpace currentSpace;

    public Elephant(Color color, ElephantSpace currentSpace) {
        this.currentSpace = currentSpace;
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

    public void moveTo(ElephantSpace newSpace) {

        var target = newSpace.localToScene(newSpace.getBoundsInLocal());
        var parentBounds = getParent().localToScene(getParent().getBoundsInLocal());
        new JumpTransition(300, new Point2D(target.getMinX() - parentBounds.getMinX(), target.getMinY() - parentBounds.getMinY())).play();
        currentSpace = newSpace;
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

    class JumpTransition extends Transition {
        Point2D target;
        Point2D start;
        double squishY = 0;
        double squishFraction;

        public JumpTransition(double speed, Point2D target) {
            this.target = target;
            this.start = new Point2D(Elephant.this.getTranslateX(), Elephant.this.getTranslateY());
            var time = 0.2 + Math.abs(target.getX() - start.getX()) / speed;
            squishFraction = 0.2 / time;
            setCycleDuration(Duration.seconds(time));

            setOnFinished(e -> {
                setTranslateX(target.getX());
                setTranslateY(target.getY());
            });
        }

        @Override
        protected void interpolate(double fraction) {
            if (fraction < squishFraction) {
                setSquish(1 - 0.3 * fraction / squishFraction);
                setTranslateY(start.getY() + squishY);
            } else {
                setSquish(Math.min(0.7 + 2 * 0.3 * (fraction - squishFraction) / squishFraction, 1.0));
                fraction = (fraction - squishFraction) / (1 - squishFraction);

                var x = start.getX() + (target.getX() - start.getX()) * fraction;
                setTranslateX(x);
                setTranslateY(calculateY(x) + squishY);

            }
        }

        void setSquish(double squish) {
            setScaleY(squish);
            setScaleX(2 - squish);
            squishY = 100 * (1 - squish);
        }

        double calculateY(double x) {
            var a = 0.01;
            var b = (start.getY() - target.getY()) / (start.getX() - target.getX()) - a * (start.getX() + target.getX());
            var c = a * start.getX() * target.getX() + (target.getY() * start.getX() - start.getY() * target.getX()) / (start.getX() - target.getX());
            return a * x * x + b * x + c;
        }
    }
}
