package app.ui.styles;


import java.util.Map;

/**
 * Maps displayed names to style configuration
 */
public class Styles {
    public static final Map<String, Style> styles = Map.of("Cute Pink", new CutePink(), "Lichess", new Lichess(),
                                                           "Chess.com", new ChessCom(), "Rainbow", new Rainbow());
}
