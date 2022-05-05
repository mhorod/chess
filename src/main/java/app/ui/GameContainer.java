package app.ui;

import javafx.scene.Node;

public interface GameContainer {
    void changeView(Node node);

    void exit();

    Style getStyle();
}
