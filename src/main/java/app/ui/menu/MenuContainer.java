package app.ui.menu;

import app.ui.Style;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MenuContainer extends VBox {
    View currentView;
    Style style;
    Elephant elephant;
    HBox views = new HBox();
    Pane pane = new Pane();

    public MenuContainer(Style style) {
        this.style = style;
        pane.getChildren().add(views);
        getChildren().add(pane);
        setFillWidth(true);

    }

    public void changeView(View view) {

        var content = view.getContent();
        views.getChildren().add(content);


        if (elephant == null) {
            elephant = new Elephant(style.whitePiece, view.getSpaceForElephant());
            pane.getChildren().add(elephant);
        } else {
            currentView.getSpaceForElephant().shrink();
        }
        layout();
        elephant.moveTo(view.getSpaceForElephant());

        var scroll = new TranslateTransition(Duration.millis(500), pane);
        scroll.setToX(getWidth() / 2 - views.getWidth() + content.getLayoutBounds().getWidth() / 2);
        scroll.play();


        if (currentView != null)
            currentView.getSpaceForElephant().smoothShrink();

        content.setTranslateX(500);
        var transition = new TranslateTransition(Duration.millis(500), content);
        transition.setFromX(500);
        transition.setToX(0);

        transition.play();
        currentView = view;
    }

    public void exit() {
        Platform.exit();
    }

    public Style getGameStyle() {
        return style;
    }
}
