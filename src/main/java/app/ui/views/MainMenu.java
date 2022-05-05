package app.ui.views;

import app.ui.GameContainer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;

public class MainMenu extends VBox {
    GameContainer container;

    public MainMenu(GameContainer container) {
        super();
        this.container = container;
        Button b1 = new Button("Play");
        b1.setOnAction(e -> {
            container.changeView(new ChessHotseat(container));
        });
        Button b2 = new Button("Exit");
        b2.setOnAction(e -> {
            container.exit();
        });
        setSpacing(20);
        getChildren().add(b1);
        getChildren().add(b2);
        setAlignment(Pos.CENTER);
        setBackground(Background.EMPTY);
    }
}
