package app.ui.menu;

import app.ui.Style;
import javafx.scene.Node;

public interface MenuView {
    ElephantSpace getSpaceForElephant();

    Node getContent();

    void setGameStyle(Style style);
}
