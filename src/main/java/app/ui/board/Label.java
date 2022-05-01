package app.ui.board;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Label extends VBox {
    Label(String content, Font font, Color color, Color textColor) {
        super();
        var text = new Text(content);
        text.setFont(font);
        text.setFill(textColor);
        text.setEffect(new DropShadow());
        setMinWidth(25);
        setMinHeight(25);
        setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        getChildren().add(text);
        setAlignment(Pos.CENTER);
    }
}
