package app.ui.board;

import app.ui.utils.Position;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class GraphicalField extends VBox {
    private final Circle indicator;
    private final Position center;
    private boolean isLegal;

    public GraphicalField(Color color, Color indicatorColor, double size, Position center) {
        super();
        this.center = center;
        setMaxWidth(size);
        setMaxHeight(size);
        setMinWidth(size);
        setMinHeight(size);
        setAlignment(Pos.CENTER);

        indicator = new Circle(size / 4);
        indicator.setFill(indicatorColor);
        getChildren().add(indicator);

        setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        toNormal();
    }

    public Position getCenter() {
        return center;
    }

    public double getSize() {
        return getMinHeight();
    }

    public void highlight() {
        indicator.setRadius(getSize() / 3);
    }

    public boolean isLegal() {
        return isLegal;
    }

    public void unhighlight() {
        indicator.setRadius(getSize() / 4);
    }

    public void markAsLegal() {
        if (isLegal) return;
        indicator.setOpacity(0.0);
        indicator.setVisible(true);

        var transition = new FadeTransition(Duration.millis(200), indicator);
        transition.setToValue(1.0);
        transition.setOnFinished(e -> {
            indicator.setOpacity(1.0);
            indicator.setVisible(true);
        });
        transition.play();

        setCursor(Cursor.CLOSED_HAND);
        isLegal = true;
    }

    public void toNormal() {
        setCursor(Cursor.DEFAULT);
        var transition = new FadeTransition(Duration.millis(200), indicator);
        transition.setToValue(0.0);
        transition.setOnFinished(e -> {
            indicator.setVisible(false);
        });
        transition.play();

        isLegal = false;
    }
}
