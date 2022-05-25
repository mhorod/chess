package app.ui.styles;

import app.ui.App;
import app.ui.Style;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Rainbow extends Style {
    {
        whitePiece = Color.web("#C4E2A6");
        blackPiece = Color.web("#EB7993");

        whiteField = Color.web("#EBECD0");
        blackField = Color.web("#B58A60");

        borderWhite = Color.web("#595A9B");
        borderBlack = Color.web("#595A9B");

        borderText = Color.web("#EEEEEE");
        whiteFieldCircle = Color.web("#D49EC2");
        blackFieldCircle = Color.web("#D49EC2");

        whitePieceBorder = Color.web("#f6f668");
        blackPieceBorder = Color.web("#f6f668");


        background = Color.web("#EFC8FF");
        font = Font.loadFont(App.class.getResource("/fonts/regular.otf").toExternalForm(), 20);
    }
}
