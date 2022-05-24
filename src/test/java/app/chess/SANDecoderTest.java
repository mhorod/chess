package app.chess;

import app.chess.board.StandardChessBoard;
import app.chess.boards.AmbiguousMovesTestBoard;
import app.chess.boards.CastleTestBoard;
import app.chess.boards.TestChessBoard;
import app.chess.san.ChessField;
import app.chess.san.SAN;
import org.junit.Test;

import java.util.List;

import static app.chess.pieces.ChessPieceKind.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SANDecoderTest {
    /**
     * Apply moves in san notation and return the game
     */
    private static Chess prepareChess(List<String> moves) {
        var chess = new Chess(new StandardChessBoard());
        SAN.applyMoves(chess, moves);
        return chess;
    }

    @Test
    public void pawn_move() {
        var chess = new Chess(new StandardChessBoard());
        var move = SAN.decodeMove(chess, 0, "e4");
        var piece = move.getPiece();
        assertEquals(ChessField.fromString("e2"), piece.getPosition());
        assertEquals(PAWN, piece.getKind());
    }


    @Test
    public void en_passant() {
        var chess = prepareChess(List.of("d4", "a5", "d5", "c5"));
        var move = SAN.decodeMove(chess, 0, "dxc6");
        var piece = move.getPiece();
        assertEquals(ChessField.fromString("d5"), piece.getPosition());
        assertEquals(PAWN, piece.getKind());
    }

    @Test
    public void rook_move() {
        var chess = new Chess(new TestChessBoard());
        var move = SAN.decodeMove(chess, 0, "Ra2");
        var piece = move.getPiece();
        assertEquals(ChessField.fromString("a1"), piece.getPosition());
        assertEquals(ROOK, piece.getKind());
    }

    @Test
    public void knight_move() {
        var chess = new Chess(new TestChessBoard());
        var move = SAN.decodeMove(chess, 0, "Nf3");
        var piece = move.getPiece();
        assertEquals(ChessField.fromString("g1"), piece.getPosition());
        assertEquals(KNIGHT, piece.getKind());
    }

    @Test
    public void bishop_move() {
        var chess = new Chess(new TestChessBoard());
        var move = SAN.decodeMove(chess, 0, "Bf4");
        var piece = move.getPiece();
        assertEquals(ChessField.fromString("c1"), piece.getPosition());
        assertEquals(BISHOP, piece.getKind());
    }

    @Test
    public void queen_move() {
        var chess = new Chess(new TestChessBoard());
        var move = SAN.decodeMove(chess, 0, "Qd4");
        var piece = move.getPiece();
        assertEquals(ChessField.fromString("d1"), piece.getPosition());
        assertEquals(QUEEN, piece.getKind());
    }

    @Test
    public void king_move() {
        var chess = new Chess(new TestChessBoard());
        var move = SAN.decodeMove(chess, 0, "Kf2");
        var piece = move.getPiece();
        assertEquals(ChessField.fromString("e1"), piece.getPosition());
        assertEquals(KING, piece.getKind());
    }

    @Test
    public void king_side_castle() {
        var chess = new Chess(new CastleTestBoard());
        var move = SAN.decodeMove(chess, 0, "O-O");
        var piece = move.getPiece();
        assertEquals(KING, piece.getKind());
        assertEquals(ChessField.fromString("e1"), piece.getPosition());
        assertEquals(ChessField.fromString("g1"), move.getField());
    }

    @Test
    public void queen_side_castle() {
        var chess = new Chess(new CastleTestBoard());
        var move = SAN.decodeMove(chess, 0, "O-O-O");
        var piece = move.getPiece();
        assertEquals(KING, piece.getKind());
        assertEquals(ChessField.fromString("e1"), piece.getPosition());
        assertEquals(ChessField.fromString("c1"), move.getField());
    }

    @Test
    public void castling_with_zeros() {
        var chess = new Chess(new CastleTestBoard());
        assertEquals(SAN.decodeMove(chess, 0, "O-O"), SAN.decodeMove(chess, 0, "0-0"));
        assertEquals(SAN.decodeMove(chess, 0, "O-O-O"), SAN.decodeMove(chess, 0, "0-0-0"));
    }

    @Test
    public void ambiguous_file() {
        var chess = new Chess(new AmbiguousMovesTestBoard());
        try {
            SAN.decodeMove(chess, 0, "Rd7");
            fail("An exception should be thrown");
        } catch (SAN.InvalidSANMove e) {
            assertEquals("Move is ambiguous", e.getMessage());
        }
    }

    @Test
    public void ambiguous_rank() {
        var chess = new Chess(new AmbiguousMovesTestBoard());
        try {
            SAN.decodeMove(chess, 0, "Rh4");
            fail("An exception should be thrown");
        } catch (SAN.InvalidSANMove e) {
            assertEquals("Move is ambiguous", e.getMessage());
        }
    }

    @Test
    public void specified_file() {
        var chess = new Chess(new AmbiguousMovesTestBoard());
        var move = SAN.decodeMove(chess, 0, "Rad7");
        var piece = move.getPiece();
        assertEquals(ROOK, piece.getKind());
        assertEquals(ChessField.fromString("a7"), piece.getPosition());
    }

    @Test
    public void specified_rank() {
        var chess = new Chess(new AmbiguousMovesTestBoard());
        var move = SAN.decodeMove(chess, 0, "R1h5");
        var piece = move.getPiece();
        assertEquals(ROOK, piece.getKind());
        assertEquals(ChessField.fromString("h1"), piece.getPosition());
    }

    @Test
    public void ambiguous_both_file_and_rank() {
        var chess = new Chess(new AmbiguousMovesTestBoard());
        try {
            // Specified rank
            SAN.decodeMove(chess, 0, "Q1c3");
            fail("An exception should be thrown");
        } catch (SAN.InvalidSANMove e) {
            assertEquals("Move is ambiguous", e.getMessage());
        }
        try {
            // Specified file
            SAN.decodeMove(chess, 0, "Qac3");
            fail("An exception should be thrown");
        } catch (SAN.InvalidSANMove e) {
            assertEquals("Move is ambiguous", e.getMessage());
        }
    }

    @Test
    public void specified_both_rank_and_file() {
        var chess = new Chess(new AmbiguousMovesTestBoard());
        var move = SAN.decodeMove(chess, 0, "Qa1c3");
        var piece = move.getPiece();
        assertEquals(QUEEN, piece.getKind());
        assertEquals(ChessField.fromString("a1"), piece.getPosition());
    }

    @Test
    public void move_with_check() {
        var chess = new Chess(new StandardChessBoard());
        // This move isn't really  a check, but it does not matter since we only want move
        var move = SAN.decodeMove(chess, 0, "c4+");
        var piece = move.getPiece();
        assertEquals(PAWN, piece.getKind());
        assertEquals(ChessField.fromString("c2"), piece.getPosition());
    }

    @Test
    public void move_with_checkmate() {

        var chess = new Chess(new StandardChessBoard());
        // This move isn't really  a checkmate, but it does not matter since we only want move
        var move = SAN.decodeMove(chess, 0, "c4#");
        var piece = move.getPiece();
        assertEquals(PAWN, piece.getKind());
        assertEquals(ChessField.fromString("c2"), piece.getPosition());
    }
}
