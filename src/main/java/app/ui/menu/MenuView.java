package app.ui.menu;

import app.ui.styles.Style;
import javafx.scene.Node;

public interface MenuView {
    ElephantSpace getSpaceForElephant();

    Node getContent();

    void setGameStyle(Style style);
}
