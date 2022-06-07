package app.ui.menu;

import app.ui.utils.ColoredImage;
import app.ui.utils.Images;
import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DerpyButton extends StackPane {
    public static final Font font = Font.loadFont(DerpyButton.class.getResource("/fonts/scb.ttf").toExternalForm(), 30);
    ColoredImage background;

    public DerpyButton(String text, Color color) {
        background = new ColoredImage(Images.button, color);
        background.setPreserveRatio(true);
        background.setFitWidth(128);

        var textGraphic = new Text(text);
        textGraphic.setFont(font);
        var textWidth = textGraphic.getLayoutBounds().getWidth();
        if (textWidth > 100) {
            textGraphic.setFont(new Font(font.getFamily(), 30 * 100 / textWidth));
        }
        getChildren().add(background);
        getChildren().add(textGraphic);


        setOnMouseEntered(e -> {
            setScaleX(1.05);
            setScaleY(1.05);
        });

        setOnMouseExited(e -> {
            setScaleX(1);
            setScaleY(1);
        });
        setCursor(Cursor.HAND);
    }

    public void setColor(Color color) {
        background.setColor(color);
    }
}
