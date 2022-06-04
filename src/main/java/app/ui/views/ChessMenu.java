package app.ui.views;

import app.ui.menu.*;
import app.ui.styles.Style;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ChessMenu extends VBox implements MenuView {
    ElephantSpace elephantSpace;

    public ChessMenu(MenuContainer container) {
        super();
        var text = new Text("Play");
        text.setFont(new Font(DerpyButton.font.getFamily(), 60));
        getChildren().add(text);

        var content = new HBox();
        elephantSpace = new ElephantSpace();
        content.getChildren().add(elephantSpace);
        content.getChildren()
               .add(new Menu(new String[]{"ALONE", "WITH FRIENDS", "RETURN"}, new Runnable[]{() -> container.changeView(
                       new ChessAI(container.getContainer())), () -> container.changeView(
                       new ChessHotseat(container.getContainer())), container::goBack}, container.getGameStyle()));
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

    @Override
    public void setGameStyle(Style style) {

    }
}