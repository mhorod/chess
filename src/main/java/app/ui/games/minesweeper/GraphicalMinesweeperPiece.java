package app.ui.games.minesweeper;

import app.minesweeper.MinesweeperPiece;
import app.ui.board.GraphicalPiece;
import javafx.scene.paint.Color;

public class GraphicalMinesweeperPiece extends GraphicalPiece<MinesweeperPiece> {
    private final Color white, black;

    public GraphicalMinesweeperPiece(MinesweeperPiece piece, Color white, Color black) {
        super(MinesweeperImages.getPieceImage(piece.getKind()), piece.getPlayer() == 0 ? white : black);
        this.white = white;
        this.black = black;
    }

    @Override
    public void update(MinesweeperPiece piece) {
        setImage(MinesweeperImages.getPieceImage(piece.getKind()));
        setColor(piece.getPlayer() == 0 ? white : black);
        if (!piece.isAlive())
            disappear();
    }
}
