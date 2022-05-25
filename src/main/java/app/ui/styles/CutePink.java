package app.ui.styles;

import app.ui.App;
import app.ui.Style;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CutePink extends Style {
    {
        whitePiece = Color.web("#EEEEEE");
        blackPiece = Color.web("#FF8CE1");

        whiteField = Color.web("#FFC8E5");
        blackField = Color.web("#EA5CB1");

        borderWhite = Color.web("#75234f");
        borderBlack = Color.web("#75234f");

        borderText = Color.web("#EEEEEE");
        whiteFieldCircle = Color.web("#00BBFA");
        blackFieldCircle = Color.web("#47e0ff");

        whitePieceBorder = Color.web("#38A7D6");
        blackPieceBorder = Color.web("#38A7D6");

        background = Color.web("#FFC8E5");
        font = Font.loadFont(App.class.getResource("/fonts/regular.otf").toExternalForm(), 20);
    }
}
