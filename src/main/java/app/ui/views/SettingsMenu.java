package app.ui.views;

import app.ui.menu.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SettingsMenu extends VBox implements MenuView {
    ElephantSpace elephantSpace;

    public SettingsMenu(MenuContainer container) {
        super();
        var text = new Text("Settings");
        text.setFont(new Font(DerpyButton.font.getFamily(), 60));
        getChildren().add(text);

        var content = new HBox();
        elephantSpace = new ElephantSpace();
        content.getChildren().add(elephantSpace);
        content.getChildren()
                .add(new Menu(new String[]{"CHANGE STYLE", "RETURN"}, new Runnable[]{
                        () -> container.changeMenu(new StyleMenu(container)),
                        container::goBack
                }, container.getGameStyle()));
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