package app.ui.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;

public class Menu extends VBox {
    public Menu() {
        super();
        Button b1 = new Button("Play");
        Button b2 = new Button("Exit");
        getChildren().add(b1);
        getChildren().add(b2);
        setAlignment(Pos.CENTER);
        setBackground(Background.EMPTY);
    }
}
