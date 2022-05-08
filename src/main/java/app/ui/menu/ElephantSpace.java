package app.ui.menu;

import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ElephantSpace extends Pane {
    public ElephantSpace() {
        super();
        setMinWidth(100);
        //setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void grow() {
        new GrowTransition(Duration.millis(500)).play();
    }

    public void smoothShrink() {
        new ShrinkTransition(Duration.millis(500)).play();
    }

    public void shrink() {
        setMinWidth(0);
    }

    class GrowTransition extends Transition {
        public GrowTransition(Duration duration) {
            setCycleDuration(duration);
        }

        @Override
        protected void interpolate(double fraction) {
            setMinWidth(100 * fraction);
        }
    }

    class ShrinkTransition extends Transition {
        public ShrinkTransition(Duration duration) {
            setCycleDuration(duration);
        }

        @Override
        protected void interpolate(double fraction) {
            setMinWidth(100 - 100 * fraction);
        }
    }
}
