package app.ui.views;

import app.ui.GameContainer;
import app.ui.ImageManager;
import app.ui.View;
import app.ui.menu.DerpyButton;
import app.ui.menu.GogglingPiece;
import app.ui.utils.ColoredImage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MainMenu extends VBox implements View {
    GameContainer container;
    GogglingPiece piece;

    public MainMenu(GameContainer container) {
        super();
        this.container = container;

        var play = new DerpyButton("PLAY", container.getStyle().whitePiece);
        play.setOnMouseClicked(e -> {
            container.changeView(new ChessHotseat(container));
        });
        var settings = new DerpyButton("SETTINGS", container.getStyle().whitePiece);
        var exit = new DerpyButton("EXIT", container.getStyle().whitePiece);
        exit.setOnMouseClicked(e -> {
            container.exit();
        });
        var menu = new VBox();
        menu.setAlignment(Pos.CENTER_LEFT);
        menu.setSpacing(5);

        menu.getChildren().add(play);
        menu.getChildren().add(settings);
        menu.getChildren().add(exit);

        var content = new HBox();
        content.setSpacing(30);
        content.setAlignment(Pos.CENTER);

        piece = new GogglingPiece(container.getStyle().whitePiece);
        setOnMouseMoved(e -> piece.update(e.getSceneX(), e.getSceneY()));
        content.getChildren().add(piece);
        content.getChildren().add(menu);


        var logo = new ColoredImage(ImageManager.logo, container.getStyle().blackPiece);
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
    public void updateMousePosition(double mouseX, double mouseY) {
        piece.update(mouseX, mouseY);
    }
}
