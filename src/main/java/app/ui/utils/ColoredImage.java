package app.ui.utils;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class ColoredImage extends ImageView {
    public ColoredImage(Image img, Color color) {
        super(img);
        setColor(color);
    }

    public void setColor(Color color) {
        var colorAdjust = new ColorAdjust();
        var hue = color.getHue() / 180;
        if (hue > 1) hue -= 2;
        colorAdjust.setHue(hue);
        colorAdjust.setSaturation(color.getSaturation());
        colorAdjust.setBrightness(color.getBrightness() - 1);
        setEffect(colorAdjust);
    }
}
