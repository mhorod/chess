package app.chess;

import app.chess.san.SAN;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

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

}
