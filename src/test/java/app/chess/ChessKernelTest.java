package app.chess;

import app.chess.board.*;
import app.chess.moves.*;
import app.chess.pieces.*;
import app.core.game.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class ChessKernelTest {
    ChessBoard board = new StandardChessBoard();
    Chess game = new Chess(board);


    @Test
    public void white_makes_moves_first() {
        assertEquals(20, game.getLegalMoves(0).size());
        assertEquals(0, game.getLegalMoves(1).size());
    }

    @Test
    public void there_are_400_possible_positions_after_the_first_two_moves() {
        var firstLegalMoves = game.getLegalMoves(0);

        int result = 0;

        for (var move : firstLegalMoves) {
            Chess tmpgame = new Chess(new StandardChessBoard());
            tmpgame.makeMove(0, move);
            result += tmpgame.getLegalMoves(1).size();
        }

        assertEquals(400, result);
    }

    @Test
    public void board_is_initialized_properly_basic_test() {
        var g1 = new Chess(new StandardChessBoard());
        var g2 = new Chess(FENConverter.parseFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));

        assertEquals(g1.getLegalMoves(0).size(), g2.getLegalMoves(0).size());

        g2 = new Chess(FENConverter.parseFen("rnbqkbnr/pppppppp/8/8/8/7P/PPPPPPP1/RNBQKBNR b KQkq - 0 1"));

        assertNotEquals(g1.getLegalMoves(0).size(), g2.getLegalMoves(0).size());

    }

    @Test
    public void pinned_pieces_cannot_move() {
        var g1 = new Chess(FENConverter.parseFen("4k3/4r3/8/8/1b3n1q/4Qn2/3R1B2/4K3 w - - 0 1"));
        assertEquals(2, g1.getLegalMoves(0).size());
    }

    @Test
    public void king_cannot_castle_if_checked() {
        var g1 = new Chess(FENConverter.parseFen("4k3/4r3/8/8/1b3n1q/4Qn2/3R1B2/R3K3 w Q - 0 1"));
        assertEquals(2, g1.getLegalMoves(0).size());
    }

    @Test
    public void king_can_castle_even_if_rook_is_under_attack() {
        var g1 = new Chess(FENConverter.parseFen("4k3/4r3/8/8/1b3n1q/4N3/q2N1N2/R3K3 w Q - 0 1"));
        assertEquals(7, g1.getLegalMoves(0).size());
    }

    @Test
    public void many_bishops_on_board_dont_confuse_chess_engine() {
        var g1 = new Chess(FENConverter.parseFen(
                "kBBBBBBB/BqBBBBBB/BBBBBBBB/BBBBBBBB/BBBBBBBB/BBBBBBBB/BBBBBBBB/BBBBKBBB w - - 0 1"));
        assertEquals(3, g1.getLegalMoves(0).size());
    }

    @Test
    public void you_have_0_legal_moves_if_checkmated() {
        var g1 = new Chess(FENConverter.parseFen("kqqqqqqq/8/8/8/8/8/8/4K3 w - - 0 1"));
        assertEquals(0, g1.getLegalMoves(0).size());
    }

    @Test
    public void pinned_pawn_cant_take() {
        var g1 = new Chess(FENConverter.parseFen("kqqqqqqq/8/8/8/8/bb1p4/r3P3/4K3 w - - 0 1"));
        assertEquals(2, g1.getLegalMoves(0).size());
    }

    @Test
    public void en_passant_mate() {
        var g1 = new Chess(FENConverter.parseFen("RP4kb/RP3p2/RP2B3/RP1B4/RPB1p3/1B2P3/B1BP1PPP/KB3QQQ w - - 0 1"));
        assertEquals(1, g1.getLegalMoves(0).size());
        assertEquals(ChessState.CHECKED, g1.getState(0));

        g1.makeMove(0, g1.getLegalMoves(0).get(0));

        var legalMoves = g1.getLegalMoves(1);

        boolean moveWasMade = false;

        for (var move : legalMoves) {
            if (move.getPiece().getPosition().rank() == 4) {
                moveWasMade = true;
                g1.makeMove(1, move); //Mate by en passant
            }
        }

        if (!moveWasMade) {
            fail("Couldn't play en passant!");
        }

        assertEquals(0, g1.getLegalMoves(0).size());
        assertEquals(ChessState.MATED, g1.getState(0));

    }

    @Test
    public void castle_mate() {
        var g1 = new Chess(FENConverter.parseFen("nrrkrrnn/qqp1ppqq/2p5/2P1p3/4p3/8/2P5/R3K3 w Q - 0 1"));

        var legalMoves = g1.getLegalMoves(0);

        boolean moveWasMade = false;

        for (var move : legalMoves) {
            if (move.getClass() == Castle.class) {
                moveWasMade = true;
                g1.makeMove(0, move);
            }
        }

        if (!moveWasMade) {
            fail("Couldn't castle!");
        }

        assertEquals(0, g1.getLegalMoves(1).size()); //It's a mate
        assertEquals(ChessState.MATED, g1.getState(1));
    }

    @Test
    public void draw_is_being_properly_detected() {
        var g1 = new Chess(
                FENConverter.parseFen("krbbbnnn/rrrrrrrr/qqqqqqqq/qqqqqqqq/qqqqqqqq/q1qqqqqq/PRqqqqqq/K7 w - - 0 1"));

        assertEquals(ChessState.DRAW, g1.getState(0));
        assertEquals(0, g1.getLegalMoves(0).size());
    }

    @Test
    public void checking_if_a_move_causes_check() {
        var g1 = new Chess(FENConverter.parseFen("7k/K7/8/2P5/4PP2/3PBP2/3PPP2/8 w - - 0 1"));

        for (ChessMove move : g1.getLegalMoves(0)) {
            if (move.getPiece().getKind() == ChessPieceKind.BISHOP) {
                assertEquals(1, g1.getLegalMoves(0, move.getPiece())
                                  .size()); //Bishop has only one legal move in this position
                //We want to check if it properly recognizes this move as a check
                assertEquals(true, g1.checkIfEnemyKingIsCheckedAfterMove(move));

                //Now our reference to the bishop is pointing to the changed one, let's get the current one
                ChessPiece newBishop = g1.getAllPieces()
                                         .stream()
                                         .filter((ChessPiece a) -> a.getKind() == ChessPieceKind.BISHOP)
                                         .findFirst()
                                         .get();

                //Its internal state shouldn't be changed in any way
                assertEquals(3, newBishop.getPosition().rank());
                assertEquals(5, newBishop.getPosition().file());

            }
        }
    }

    @Test
    public void results_of_checking_for_checks_are_consistent() {
        var g1 = new Chess(FENConverter.parseFen(
                "NNNRBppk/NNRBRppp/NNRRBppp/PPPPPPQQ/QQQQPPPP/QQQQQQQQ/QQQQQQQQ/KQQQQQQQ w - - 0 1"));
        //In this position, black king is never going to be checked by ANY move that white performs.

        for (int i = 0; i < 100; i++) {
            //g1.getAllPieces().forEach(System.out::println);

            g1.getLegalMoves(0)
              .forEach((ChessMove move) -> assertEquals(false, g1.checkIfEnemyKingIsCheckedAfterMove(move)));
        }
    }

    @Test
    public void easier_consistency_test() {
        //If the above tests fails, it is likely that this one will too, and this one is easier to debug.
        //If this tests is OK and the above one fails, good luck!

        var g1 = new Chess(FENConverter.parseFen("7k/8/8/6PP/8/8/8/7K w - - 0 1"));

        for (int i = 0; i < 100; i++) {
            //System.out.println("====" + i + "===");
            //g1.getAllPieces().forEach(System.out::println);

            g1.getLegalMoves(0)
              .forEach((ChessMove move) -> assertEquals(false, g1.checkIfEnemyKingIsCheckedAfterMove(move)));
        }
    }

    @Test
    public void castling_causing_check() {
        var g1 = new Chess(FENConverter.parseFen("3k4/8/8/8/8/8/8/R3K3 w Q - 0 1"));

        for (int i = 0; i < 100; i++) {
            Castle move = null;

            try {
                move = (Castle) g1.getLegalMoves(0)
                                  .stream()
                                  .filter((ChessMove a) -> a.getClass() == Castle.class)
                                  .findFirst()
                                  .get();
            } catch (NoSuchElementException e) {
                fail("Couldn't castle!");
            }

            assertEquals(true, g1.checkIfEnemyKingIsCheckedAfterMove(move));
        }
    }


    @Test
    public void castling_causing_check_advanced() {
        var g1 = new Chess(FENConverter.parseFen("3k4/qqR1pppN/2R2PPP/2R2PPP/2R2PPP/2R2PPP/2R2PPP/R3K3 w Q - 0 1"));

        for (int i = 0; i < 100; i++) {
            Castle move = null;

            try {
                move = (Castle) g1.getLegalMoves(0)
                                  .stream()
                                  .filter((ChessMove a) -> a.getClass() == Castle.class)
                                  .findFirst()
                                  .get();
            } catch (NoSuchElementException e) {
                fail("Couldn't castle!");
            }

            assertEquals(true, g1.checkIfEnemyKingIsCheckedAfterMove(move));
        }
    }

    @Test
    public void promotion_causing_check() {
        var g1 = new Chess(FENConverter.parseFen("7k/P7/8/8/8/8/8/K7 w - - 0 1"));
        //In this scenario, white can promote its pawn
        ChessMove toTheLastRank = g1.getLegalMoves(0)
                                    .stream()
                                    .filter((ChessMove a) -> a.getPiece().getKind() == ChessPieceKind.PAWN)
                                    .findAny()
                                    .get();
        g1.makeMove(0, toTheLastRank);

        assertNotEquals(0, g1.getLegalMoves(0)
                             .size()); //It's still white that is supposed to make move, this time piece pick

        for (int i = 0; i < 100; i++) {
            var promotionToQueen = g1.getLegalMoves(0)
                                     .stream()
                                     .filter((ChessMove a) -> a.getPiece().getKind() == ChessPieceKind.QUEEN)
                                     .findAny()
                                     .get();
            assertEquals(true, g1.checkIfEnemyKingIsCheckedAfterMove(promotionToQueen));
            assertEquals(3, g1.getAllPieces().size());
        }
    }

    @Test
    public void player_changes_after_normal_move() {
        var chess = new Chess(FENConverter.parseFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQK2R w KQkq - 0 1"));
        var move = chess.getLegalMoves(0).stream().filter(e -> !(e instanceof Castle)).toList().get(0);
        chess.makeMove(0, move);
        assertEquals(0, chess.getLegalMoves(0).size());
        assertNotEquals(0, chess.getLegalMoves(1).size());
    }

    @Test
    public void player_changes_after_castling() {
        var chess = new Chess(FENConverter.parseFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQK2R w KQkq - 0 1"));
        var castle = chess.getLegalMoves(0).stream().filter(e -> e instanceof Castle).toList().get(0);
        chess.makeMove(0, castle);
        assertEquals(0, chess.getLegalMoves(0).size());
        assertNotEquals(0, chess.getLegalMoves(1).size());
    }


    @Test
    public void illegal_en_passant() {
        var chess = new Chess(FENConverter.parseFen("b7/8/8/8/k4p1R/6q1/4P3/7K w - - 0 1"));
        var onlyLegalMove = chess.getLegalMoves(0).get(0);
        assertEquals(1, chess.getLegalMoves(0).size());

        chess.makeMove(0, onlyLegalMove);

        var blackPawn = chess.getPieces(1)
                             .stream()
                             .filter((ChessPiece a) -> a.getKind() == ChessPieceKind.PAWN)
                             .findAny()
                             .get();

        assertEquals(1, chess.getLegalMoves(1, blackPawn).size()); //You CANNOT do en passant here
    }

    /**
     * Used only for tests purposes, doesn't really parse FEN <br> I mean it does, but only a part of it
     */
    private static class FENConverter {
        private static int putPieceOnBoard(char letter, ChessBoard board, int rank, int file) {
            var color = letter < 'a' ? ChessPieceColor.WHITE : ChessPieceColor.BLACK; //If it's a capital letter then it means that the player is white

            letter = Character.toLowerCase(letter);
            ChessPieceKind kind = null;
            try {
                kind = switch (letter) {
                    case 'r' -> ChessPieceKind.ROOK;
                    case 'k' -> ChessPieceKind.KING;
                    case 'q' -> ChessPieceKind.QUEEN;
                    case 'p' -> ChessPieceKind.PAWN;
                    case 'b' -> ChessPieceKind.BISHOP;
                    case 'n' -> ChessPieceKind.KNIGHT;
                    default -> throw new IllegalStateException("Unexpected value: " + letter);
                };
            } catch (IllegalStateException e) {
                file += letter - '0'; //Don't ask why I decided to go this way. It just works. Sort of.
                return file;
            }

            board.put(new Field(rank, file), kind, color);

            return file + 1;
        }

        static ChessBoard parseFen(String code) {
            ChessBoard result = new ChessBoard(9);

            int rank = 8;
            int file = 1;

            for (int i = 0; i < code.length(); i++) {
                char letter = code.charAt(i);

                if (letter == ' ') {
                    //We won't parse any additional details
                    break;
                }
                if (letter != '/') {
                    file = putPieceOnBoard(letter, result, rank, file);
                } else {
                    rank--;
                    file = 1;
                }

            }
            return result;
        }
    }

}
