package app.ui.menu;

import app.ui.Style;
import app.ui.views.View;
import app.ui.views.ViewContainer;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class MenuContainer extends View {
    Style style;
    Elephant elephant;
    HBox viewsContainer = new HBox();
    Pane pane = new Pane();
    ArrayList<MenuView> menuViews = new ArrayList<>();
    double centerX = 0;

    public MenuContainer(ViewContainer parent, Style style) {
        super(parent);
        this.style = style;
        pane.getChildren().add(viewsContainer);
        getChildren().add(pane);
        setFillWidth(true);
        setAlignment(Pos.CENTER);

        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (!menuViews.isEmpty())
                pane.setTranslateX(getWidth() / 2 - viewsContainer.getWidth() + menuViews.get(menuViews.size() - 1).getContent().getLayoutBounds().getWidth() / 2);
        });

    }


    public void changeMenu(MenuView menuView) {
        var currentView = menuViews.isEmpty() ? null : menuViews.get(menuViews.size() - 1);
        menuViews.add(menuView);

        var content = menuView.getContent();
        viewsContainer.getChildren().add(content);


        if (elephant == null || currentView == null) {
            elephant = new Elephant(style.whitePiece, menuView.getSpaceForElephant());
            pane.getChildren().add(elephant);
        } else {
            currentView.getSpaceForElephant().shrink();
        }
        layout();
        elephant.moveTo(menuView.getSpaceForElephant());

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
        var lastView = menuViews.get(menuViews.size() - 1);
        var newView = menuViews.get(menuViews.size() - 2);

        viewsContainer.getChildren().remove(lastView.getContent());
        newView.getSpaceForElephant().grow();
        layout();
        elephant.moveTo(newView.getSpaceForElephant());
        newView.getSpaceForElephant().smoothGrow();
        var scroll = new TranslateTransition(Duration.millis(500), pane);
        scroll.setToX(getWidth() / 2 - viewsContainer.getWidth() + newView.getContent().getLayoutBounds().getWidth() / 2);
        scroll.play();
        menuViews.remove(menuViews.size() - 1);
    }

    public void updateMousePosition(double mouseX, double mouseY) {
        if (elephant != null)
            elephant.update(mouseX, mouseY);
    }

    public Style getGameStyle() {
        return style;
    }

    @Override
    protected void setGameStyle(Style style) {
        for (var menuView : menuViews)
            menuView.setGameStyle(style);
        elephant.setGameStyle(style);
    }
}
