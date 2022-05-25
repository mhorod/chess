package app.chess;

import app.chess.boards.CastleTestBoard;
import app.chess.boards.PromotionTestBoard;
import app.chess.san.ChessField;
import app.chess.san.SAN;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.List;

import static app.chess.pieces.ChessPieceKind.KING;
import static app.chess.pieces.ChessPieceKind.KNIGHT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Enclosed.class)
public class SANTest {
    public static class SplitMoveSequenceTest {
        @Test
        public void single_move() {
            var moves = "1. e4";
            assertEquals(List.of("e4"), SAN.splitMoveSequence(moves));
        }

        @Test
        public void single_block() {
            var moves = "1. e4 e5";
            assertEquals(List.of("e4", "e5"), SAN.splitMoveSequence(moves));
        }

        @Test
        public void multiple_blocks() {
            var moves = "1. e4 e5 2. Nf3 Nc6 3. Bb5 O-O";
            assertEquals(List.of("e4", "e5", "Nf3", "Nc6", "Bb5", "O-O"), SAN.splitMoveSequence(moves));
        }

        @Test
        public void last_block_with_single_move() {
            var moves = "1. e4 e5 2. Nf3";
            assertEquals(List.of("e4", "e5", "Nf3"), SAN.splitMoveSequence(moves));
        }

        @Test
        public void no_space_after_dot() {
            var moves = "1.e4 e5 2.Nf3 Nc6";
            assertEquals(List.of("e4", "e5", "Nf3", "Nc6"), SAN.splitMoveSequence(moves));
        }

        @Test
        public void castling_with_zeros() {
            var moves = "1. 0-0 0-0-0";
            assertEquals(List.of("0-0", "0-0-0"), SAN.splitMoveSequence(moves));
        }
    }

    public static class ApplyMovesTest {
        @Test
        public void apply_castle() {
            var chess = new Chess(new CastleTestBoard());
            SAN.applyMoves(chess, "1. O-O");
            var pieces = chess.getPieces(0);
            var king = pieces.stream().filter(p -> p.getKind() == KING).findFirst().orElseThrow();
            assertEquals(ChessField.fromString("g1"), king.getPosition());
        }

        @Test
        public void apply_promotion() {
            var chess = new Chess(new PromotionTestBoard());
            SAN.applyMoves(chess, "1. a8=N");
            var pieces = chess.getAllPieces();
            var knight = pieces.stream().filter(p -> p.getKind() == KNIGHT).findFirst();
            assertTrue(knight.isPresent());
        }
    }
}
