package app.ui.views;

import app.ui.ImageManager;
import app.ui.Style;
import app.ui.menu.DerpyButton;
import app.ui.menu.ElephantSpace;
import app.ui.menu.MenuContainer;
import app.ui.menu.MenuView;
import app.ui.utils.ColoredImage;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StyleMenu extends VBox implements MenuView {
    ElephantSpace elephantSpace;

    public StyleMenu(MenuContainer container) {
        super();
        var text = new Text("Style");
        text.setFont(new Font(DerpyButton.font.getFamily(), 60));
        getChildren().add(text);

        var content = new HBox();
        elephantSpace = new ElephantSpace();
        content.getChildren().add(elephantSpace);
        var buttons = new VBox();

        /*content.getChildren()
                .add(new Menu(new String[]{"RETURN"}, new Runnable[]{
                        container::goBack
                }, container.getGameStyle()));*/
        buttons.getChildren().add(new StyleButton("epic", container.getGameStyle()));

        content.getChildren().add(buttons);
        getChildren().add(content);
        setAlignment(Pos.CENTER);

    }

    @Override
    public ElephantSpace getSpaceForElephant() {
        return elephantSpace;
    }

    @Override
    public Node getContent() {
        return this;
    }
}

class StyleButton extends StackPane {
    StyleButton(String name, Style style) {

        var background = new ColoredImage(ImageManager.longButton, style.whiteField);
        background.setPreserveRatio(true);
        background.setFitWidth(256);

        var content = new GridPane();

        var textGraphic = new Text(name);
        textGraphic.setFont(DerpyButton.font);
        var textWidth = textGraphic.getLayoutBounds().getWidth();
        if (textWidth > 100) {
            textGraphic.setFont(new Font(DerpyButton.font.getFamily(), 30 * 100 / textWidth));
        }
        content.add(textGraphic, 0, 0);


        getChildren().add(background);
        getChildren().add(content);

        var column1 = new ColumnConstraints();
        column1.setHalignment(HPos.LEFT);
        column1.setPercentWidth(70);

        var column2 = new ColumnConstraints();
        column2.setHalignment(HPos.RIGHT);
        column2.setPercentWidth(30);
        var row1 = new RowConstraints();
        row1.setValignment(VPos.CENTER);
        row1.setPercentHeight(100);

        content.getColumnConstraints().addAll(column1, column2);
        content.getRowConstraints().add(row1);
        content.setPadding(new Insets(10));


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
}