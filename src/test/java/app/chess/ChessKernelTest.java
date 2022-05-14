package app.chess;
import app.chess.pieces.*;
import app.core.game.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChessKernelTest {
    /**
     * Used only for tests purposes, doesn't really parse FEN
     * I mean it does, but only a part of it
     */
    private static class FENConverter{
        private static int putPieceOnBoard(char letter, Board board, int rank, int file){
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
            }
            catch(IllegalStateException e){
                file += letter - '0'; //Don't ask why I decided to go this way. It just works. Sort of.
                return file;
            }

            board.put(new Field(rank, file), kind, color);

            return file+1;
        }

        static Board parseFen(String code){
            Board result = new Board(9);

            int rank = 8;
            int file = 1;

            for(int i=0;i<code.length();i++){
                char letter = code.charAt(i);

                if(letter == ' '){
                    //We won't parse any additional details
                    break;
                }
                if(letter != '/'){
                    file = putPieceOnBoard(letter, result, rank, file);
                }
                else{
                    rank--;
                    file = 1;
                }

            }
            return result;
        }
    }

    Board board = new ChessBoard();
    Chess game = new Chess(board);

    @Test
    public void white_makes_moves_first(){
        assertEquals(20, game.getLegalMoves(0).size());
        assertEquals(0, game.getLegalMoves(1).size());
    }

    @Test
    public void there_are_400_possible_positions_after_the_first_two_moves(){
        var firstLegalMoves = game.getLegalMoves(0);

        int result = 0;

        for(var move : firstLegalMoves){
            Chess tmpgame = new Chess(new ChessBoard());
            tmpgame.makeMove(0,move);
            result += tmpgame.getLegalMoves(1).size();
        }

        assertEquals(400, result);
    }

    @Test
    public void board_is_initialized_properly_basic_test(){
        var g1 = new Chess(new ChessBoard());
        var g2 = new Chess(FENConverter.parseFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));

        assertEquals(g1.getLegalMoves(0).size() , g2.getLegalMoves(0).size());

        g2 = new Chess(FENConverter.parseFen("rnbqkbnr/pppppppp/8/8/8/7P/PPPPPPP1/RNBQKBNR b KQkq - 0 1"));

        assertNotEquals(g1.getLegalMoves(0).size(),g2.getLegalMoves(0).size());

    }
}
