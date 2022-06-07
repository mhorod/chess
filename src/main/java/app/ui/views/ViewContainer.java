package app.ui.views;

import app.ui.styles.Style;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public class ViewContainer extends StackPane {
    View currentView;
    Style style;
    double mouseX, mouseY;

    public ViewContainer(Style style) {
        setAlignment(Pos.CENTER);
        this.style = style;
        setOnMouseMoved(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
            if (currentView != null)
                currentView.updateMousePosition(mouseX, mouseY);
        });
    }

    public void changeView(View newView) {
        getChildren().add(newView);
        newView.appear();
        if (currentView != null)
            currentView.disappear();
        currentView = newView;
        layout();
        currentView.updateMousePosition(mouseX, mouseY);
    }

    public Style getGameStyle() {
        return style;
    }

    public void setGameStyle(Style style) {
        getScene().setFill(style.background);
        if (currentView != null)
            currentView.setGameStyle(style);
        this.style = style;
    }

}
