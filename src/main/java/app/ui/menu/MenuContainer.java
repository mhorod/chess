package app.ui.menu;

import app.ui.Style;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;

public class MenuContainer extends VBox {
    Style style;
    Elephant elephant;
    HBox viewsContainer = new HBox();
    Pane pane = new Pane();
    ArrayList<View> views = new ArrayList<>();

    public MenuContainer(Style style) {
        this.style = style;
        pane.getChildren().add(viewsContainer);
        getChildren().add(pane);
        setFillWidth(true);

    }

    public void changeView(View view) {
        var currentView = views.isEmpty() ? null : views.get(views.size() - 1);
        views.add(view);

        var content = view.getContent();
        viewsContainer.getChildren().add(content);


        if (elephant == null || currentView == null) {
            elephant = new Elephant(style.whitePiece, view.getSpaceForElephant());
            pane.getChildren().add(elephant);
        } else {
            currentView.getSpaceForElephant().shrink();
        }
        layout();
        elephant.moveTo(view.getSpaceForElephant());

        var scroll = new TranslateTransition(Duration.millis(500), pane);
        scroll.setToX(getWidth() / 2 - viewsContainer.getWidth() + content.getLayoutBounds().getWidth() / 2);
        scroll.play();


        if (currentView != null)
            currentView.getSpaceForElephant().smoothShrink();

        content.setTranslateX(500);
        var transition = new TranslateTransition(Duration.millis(500), content);
        transition.setFromX(500);
        transition.setToX(0);

        transition.play();
    }

    public void exit() {
        Platform.exit();
    }

    public void goBack() {
        var lastView = views.get(views.size() - 1);
        var newView = views.get(views.size() - 2);

        viewsContainer.getChildren().remove(lastView.getContent());
        newView.getSpaceForElephant().grow();
        layout();
        elephant.moveTo(newView.getSpaceForElephant());
        newView.getSpaceForElephant().smoothGrow();
        var scroll = new TranslateTransition(Duration.millis(500), pane);
        scroll.setToX(getWidth() / 2 - viewsContainer.getWidth() + newView.getContent().getLayoutBounds().getWidth() / 2);
        scroll.play();
        views.remove(views.size() - 1);
    }

    public void updateMousePosition(double mouseX, double mouseY) {
        elephant.update(mouseX, mouseY);
    }

    public Style getGameStyle() {
        return style;
    }
}
