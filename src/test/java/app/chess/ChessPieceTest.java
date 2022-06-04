package app.chess;

import app.chess.moves.NormalMove;
import app.chess.pieces.ChessPieceFactory;
import app.chess.pieces.ChessPieceKind;
import app.core.game.Field;
import org.junit.Test;

import java.util.function.BiFunction;

import static app.chess.AbstractChessPiece.IncorrectPiecePlacement;
import static app.chess.pieces.ChessPieceColor.WHITE;
import static app.chess.pieces.ChessPieceKind.PAWN;
import static app.chess.pieces.ChessPieceKind.ROOK;
import static org.junit.Assert.*;

public class ChessPieceTest {

    @Test
    public void cannot_create_piece_outside_the_board() {
        BiFunction<Integer, Integer, ?> f = (rank, file) -> ChessPieceFactory.newPiece(new Field(rank, file), PAWN,
                                                                                       WHITE);
        assertThrows(IncorrectPiecePlacement.class, () -> f.apply(0, 0));
        assertThrows(IncorrectPiecePlacement.class, () -> f.apply(0, 5));
        assertThrows(IncorrectPiecePlacement.class, () -> f.apply(5, 0));
        assertThrows(IncorrectPiecePlacement.class, () -> f.apply(9, 5));
        assertThrows(IncorrectPiecePlacement.class, () -> f.apply(5, 9));
        assertThrows(IncorrectPiecePlacement.class, () -> f.apply(9, 9));
    }

    @Test
    public void can_create_piece_inside_the_board() {
        BiFunction<Integer, Integer, ?> f = (rank, file) -> ChessPieceFactory.newPiece(new Field(rank, file), PAWN,
                                                                                       WHITE);
        f.apply(1, 1);
        f.apply(1, 4);
        f.apply(7, 8);
        f.apply(8, 8);
    }

    @Test
    public void chess_piece_is_equal_to_itself() {
        for (var kind : ChessPieceKind.values()) {
            var piece = ChessPieceFactory.newPiece(new Field(1, 1), kind, WHITE);
            assertEquals(piece, piece);
        }
    }


    @Test
    public void chess_pieces_created_by_factory_are_not_equal() {
        // because they reference different objects
        for (var kind : ChessPieceKind.values()) {
            var first = ChessPieceFactory.newPiece(new Field(1, 1), kind, WHITE);
            var second = ChessPieceFactory.newPiece(new Field(1, 1), kind, WHITE);
            assertNotEquals(first, second);
        }
    }

    @Test
    public void normal_moves_that_differ_in_position_are_not_equal() {
        var piece = ChessPieceFactory.newPiece(new Field(1, 1), PAWN, WHITE);
        var first_move = new NormalMove(piece, new Field(1, 2));
        var second_move = new NormalMove(piece, new Field(1, 3));
        assertNotEquals(first_move, second_move);
    }

    @Test
    public void normal_moves_that_differ_in_piece_are_not_equal() {
        var first_piece = ChessPieceFactory.newPiece(new Field(1, 1), PAWN, WHITE);
        var second_piece = ChessPieceFactory.newPiece(new Field(1, 3), ROOK, WHITE);
        var first_move = new NormalMove(first_piece, new Field(1, 2));
        var second_move = new NormalMove(second_piece, new Field(1, 2));
        assertNotEquals(first_move, second_move);
    }
}
