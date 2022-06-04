package app.ui.views;

import app.ui.menu.*;
import app.ui.styles.Style;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class FunnyMenu extends VBox implements MenuView {
    ElephantSpace elephantSpace;

    public FunnyMenu(MenuContainer container) {
        super();
        var text = new Text("Jump");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(new Font(DerpyButton.font.getFamily(), 60));
        getChildren().add(text);

        var content = new HBox();
        content.setSpacing(10);
        elephantSpace = new ElephantSpace();
        content.getChildren().add(elephantSpace);
        content.getChildren()
               .add(new Menu(new String[]{"GO RIGHT", "GO LEFT"},
                             new Runnable[]{() -> container.changeMenu(new FunnyMenu(container)), container::goBack},
                             container.getGameStyle()));
        getChildren().add(content);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));

    }

    @Override
    public ElephantSpace getSpaceForElephant() {
        return elephantSpace;
    }

    @Override
    public Node getContent() {
        return this;
    }

    @Override
    public void setGameStyle(Style style) {

    }
}
