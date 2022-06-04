package app.ui.styles;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Properties used to style user interface
 */
public abstract class Style {
    // pieces
    public Color whitePiece;
    public Color blackPiece;

    // fields
    public Color whiteField;
    public Color blackField;

    // background and font color of numbers/letters on the board side
    public Color borderWhite;
    public Color borderBlack;
    public Color borderText;

    // circles indicating moves
    public Color whiteFieldCircle;
    public Color blackFieldCircle;

    // border of piece that can be captured
    public Color whitePieceBorder;
    public Color blackPieceBorder;

    // background and of the app
    public Color background;
    public Font font;
}
