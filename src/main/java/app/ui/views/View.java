package app.ui.views;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.VBox;

public class View extends VBox {
    ViewContainer parent;

    public View(ViewContainer parent) {
        this.parent = parent;
    }

    protected void changeView(View view) {
        parent.changeView(view);
    }

    public ViewContainer getContainer() {
        return parent;
    }

    public void appear() {
        var transition = new TranslateTransition();
        transition.setFromY(-1000);
        transition.setToY(0);
        transition.setNode(this);
        transition.play();

    }

    public void disappear() {
        var transition = new TranslateTransition();
        transition.setFromY(0);
        transition.setToY(1000);
        transition.setNode(this);
        transition.setOnFinished((e) -> {
            getContainer().getChildren().remove(this);
        });
        transition.play();
    }

    public void updateMousePosition(double x, double y) {
    }
}
