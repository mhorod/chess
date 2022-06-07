package app.ui.menus;

import app.ui.menu.*;
import app.ui.styles.Style;
import app.ui.utils.ColoredImage;
import app.ui.utils.Images;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MainMenu extends VBox implements MenuView {
    MenuContainer container;
    ElephantSpace elephantSpace;
    ColoredImage logo;
    Menu menu;

    public MainMenu(MenuContainer container) {
        super();
        this.container = container;

        menu = new Menu(new String[]{"PLAY", "SETTINGS", "GO RIGHT", "EXIT"},
                        new Runnable[]{() -> container.changeMenu(new PlayMenu(container)), () -> container.changeMenu(
                                new SettingsMenu(container)), () -> container.changeMenu(
                                new FunnyMenu(container)), Platform::exit,}, container.getGameStyle());

        var content = new HBox();
        content.setSpacing(30);
        content.setAlignment(Pos.CENTER);

        elephantSpace = new ElephantSpace();
        content.getChildren().add(elephantSpace);
        content.getChildren().add(menu);


        logo = new ColoredImage(Images.logo, container.getGameStyle().blackPiece);
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
        logo.setColor(style.blackPiece);
        menu.setGameStyle(style);
    }
}
