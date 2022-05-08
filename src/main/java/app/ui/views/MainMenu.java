package app.ui.views;

import app.ui.ImageManager;
import app.ui.menu.*;
import app.ui.utils.ColoredImage;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MainMenu extends VBox implements View {
    MenuContainer container;
    Elephant piece;
    ElephantSpace elephantSpace;

    public MainMenu(MenuContainer container) {
        super();
        this.container = container;

        var menu = new Menu(new String[]{"PLAY", "SETTINGS", "EXIT"}, new Runnable[]{
                () -> container.changeView(new PlayMenu(container)),
                this::doNothing,
                Platform::exit
        }, container.getGameStyle());

        var content = new HBox();
        content.setSpacing(30);
        content.setAlignment(Pos.CENTER);

        elephantSpace = new ElephantSpace();
        content.getChildren().add(elephantSpace);
        content.getChildren().add(menu);


        var logo = new ColoredImage(ImageManager.logo, container.getGameStyle().blackPiece);
        logo.setFitWidth(200);
        logo.setPreserveRatio(true);

        var text = new Text("Epic Chess");
        text.setFont(new Font(DerpyButton.font.getFamily(), 60));

        setSpacing(10);
        setPadding(new Insets(50));
        //setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
        getChildren().add(logo);
        getChildren().add(text);
        getChildren().add(content);
        setAlignment(Pos.CENTER);
        setFillWidth(true);
        //setBackground(new Background(new BackgroundFill(container.getStyle().whiteField, new CornerRadii(10), Insets.EMPTY)));
    }

    void doNothing() {
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
