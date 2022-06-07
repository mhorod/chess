package app.ui.utils;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Applies color and border filter to supplied image. Used to change color and add effects to pieces.
 */
public class ColoredImage extends ImageView {
    Color color;
    Color highlightColor;

    public ColoredImage(Image img, Color color) {
        super(img);
        this.color = color;
        setColor(color);
    }

    private static Effect colorAdjust(Color color) {
        var colorAdjust = new ColorAdjust();
        var hue = color.getHue() / 180;
        if (hue > 1)
            hue -= 2;
        colorAdjust.setHue(hue);
        colorAdjust.setSaturation(color.getSaturation());
        colorAdjust.setBrightness(color.getBrightness() - 1);
        return colorAdjust;
    }

    public void setColor(Color color) {
        this.color = color;
        if (highlightColor == null)
            setEffect(colorAdjust(color));
        else
            highlight(highlightColor);
    }

    public void highlight(Color highlightColor) {
        var colorAdjust = colorAdjust(color);
        var dropShadow = new DropShadow(4, highlightColor);
        dropShadow.setSpread(1.0);
        dropShadow.setInput(colorAdjust);
        setEffect(dropShadow);
    }

    public void unhighlight() {
        setColor(color);
    }
}
