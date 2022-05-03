package app.ui.board;

import app.ui.utils.ColoredImage;
import app.ui.utils.Position;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public abstract class GraphicalPiece<P extends app.core.game.Piece> extends ColoredImage {

    public GraphicalPiece(Image image, Color color) {
        super(image, color);
        setCursor(Cursor.HAND);
    }

    public abstract void update(P piece);


    public void pickUp(GraphicalField graphicalField) {
        toFront();
        setSize(graphicalField.getSize() * 1.2);
        setCenter(graphicalField.getCenter());
    }

    public void putDown(GraphicalField graphicalField) {
        setSize(graphicalField.getSize());
        setCenterFluid(graphicalField.getCenter());
    }

    public void setSize(double size) {
        setX(getCenter().x() - size / 2);
        setY(getCenter().y() - size / 2);
        setFitWidth(size);
        setFitHeight(size);
    }

    public void disappear() {
        setCursor(Cursor.DEFAULT);
        var transition = new FadeTransition(Duration.millis(200), this);
        transition.setToValue(0.0);
        transition.setOnFinished(e -> setVisible(false));
        transition.play();
    }

    public Position getCenter() {
        return new Position(getX() + getFitWidth() / 2, getY() + getFitHeight() / 2);
    }

    public void setCenter(Position pos) {
        setX(pos.x() - getFitWidth() / 2);
        setY(pos.y() - getFitHeight() / 2);
    }

    public void setCenterFluid(Position pos) {
        var transition = new MoveTransition(Duration.millis(Math.log(distance2(getCenter(), pos) + 1) * 30), getCenter(), pos);
        transition.play();
    }

    private double distance2(Position a, Position b) {
        double deltaX = a.x() - b.x();
        double deltaY = a.y() - b.y();
        return deltaX * deltaX + deltaY * deltaY;
    }

    class MoveTransition extends Transition {
        Position from;
        Position to;

        public MoveTransition(Duration duration, Position from, Position to) {
            setCycleDuration(duration);
            this.from = from;
            this.to = to;
        }

        @Override
        protected void interpolate(double fraction) {
            double x = from.x() + fraction * (to.x() - from.x());
            double y = from.y() + fraction * (to.y() - from.y());
            setCenter(new Position(x, y));
        }
    }
}


