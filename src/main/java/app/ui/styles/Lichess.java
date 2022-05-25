package app.ui.styles;

import app.ui.App;
import app.ui.Style;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Lichess extends Style {
    {
        whitePiece = Color.web("#EEEEEE");
        blackPiece = Color.web("#666666");

        whiteField = Color.web("#F0D9B5");
        blackField = Color.web("#B58862");

        borderWhite = Color.web("#646F41");
        borderBlack = Color.web("#646F41");

        borderText = Color.web("#EEEEEE");
        whiteFieldCircle = Color.web("#829769");
        blackFieldCircle = Color.web("#646F41");

        whitePieceBorder = Color.web("#829769");
        blackPieceBorder = Color.web("#829769");

        background = Color.web("#FEFFC8");
        font = Font.loadFont(App.class.getResource("/fonts/regular.otf").toExternalForm(), 20);
    }
}
