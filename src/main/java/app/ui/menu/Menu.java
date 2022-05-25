package app.ui.menu;

import app.ui.Style;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;

public class Menu extends VBox {
    public Menu(String[] options, Runnable[] actions, Style style) {
        super();
        for (int i = 0; i < options.length; i++) {
            var button = new DerpyButton(options[i], style.whitePiece);
            final int finalI = i;
            button.setOnMouseClicked(e -> actions[finalI].run());
            getChildren().add(button);
        }
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(5);
        setBackground(Background.EMPTY);
    }
}
