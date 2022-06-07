package app.ui.styles;

import app.ui.App;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ChessCom extends Style {
    {
        whitePiece = Color.web("#fafafa");
        blackPiece = Color.web("#575454");

        whiteField = Color.web("#EBECD0");
        blackField = Color.web("#779556");

        borderWhite = Color.web("#444");
        borderBlack = Color.web("#444");

        borderText = Color.web("#EEEEEE");
        whiteFieldCircle = Color.web("#c6c6c6");
        blackFieldCircle = Color.web("#c6c6c6");

        whitePieceBorder = Color.web("#f6f668");
        blackPieceBorder = Color.web("#f6f668");


        background = Color.web("#DDFFC8");
        font = Font.loadFont(App.class.getResource("/fonts/regular.otf").toExternalForm(), 20);
    }
}
