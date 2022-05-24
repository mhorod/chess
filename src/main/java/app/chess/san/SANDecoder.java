package app.chess.san;

import app.chess.ChessPiece;
import app.chess.moves.Castle;
import app.chess.moves.ChessMove;
import app.chess.moves.NormalMove;
import app.chess.pieces.ChessPieceKind;
import app.core.game.Field;
import app.core.game.GameView;

import static app.chess.pieces.ChessPieceKind.*;

/**
 * Utility class for decoding moves from standard algebraic notation
 */
public class SANDecoder {
    private SANDecoder() {
    }

    public static ChessMove decodeMove(GameView<ChessMove, ChessPiece> chess, int player, String move) {

        if (move == null || move.length() < 2)
            throw new SAN.InvalidSANMove("Invalid format: " + move);

        // Ignore information about check and checkmate
        if (move.charAt(move.length() - 1) == '+' || move.charAt(move.length() - 1) == '#')
            move = move.substring(0, move.length() - 1);

        // Handle castling
        if (move.equals("O-O") || move.equals("0-0"))
            return kingSideCastle(chess, player);
        else if (move.equals("O-O-O") || move.equals("0-0-0"))
            return queenSideCastle(chess, player);

        var kind = pieceKind(move);
        var targetField = targetField(move);
        var modifiers = modifiers(move);
        var piece = getMatchingPiece(chess, player, kind, targetField, modifiers);

        return new NormalMove(piece, targetField);
    }

    static Castle kingSideCastle(GameView<ChessMove, ChessPiece> chess, int player) {
        var file = 7;
        var rank = player == 0 ? 1 : 8;
        var field = new Field(rank, file);
        var king = getMatchingPiece(chess, player, KING, field, new Modifiers());
        return new Castle(king, field);
    }

    static Castle queenSideCastle(GameView<ChessMove, ChessPiece> chess, int player) {
        var file = 3;
        var rank = player == 0 ? 1 : 8;
        var field = new Field(rank, file);
        var king = getMatchingPiece(chess, player, KING, field, new Modifiers());
        return new Castle(king, field);
    }

    /**
     * Get a piece that can move to a given field and meet criteria
     * <p>
     * Throws InvalidSANMove if it cannot unambiguously determine the piece
     *
     * @param chess chess game that provides list of moves
     * @param player player that makes the move
     * @param kind kind of the piece
     * @param targetField target field
     * @param modifiers additional information
     * @return list of matching pieces
     */
    static ChessPiece getMatchingPiece(
            GameView<ChessMove, ChessPiece> chess, int player, ChessPieceKind kind, Field targetField,
            Modifiers modifiers
    ) {

        // Get list of pieces that could perform the move
        // The result should contain a single element
        var pieces = chess.getPieces(player)
                          .stream()
                          .filter(p -> p.getPlayer() == player)
                          .filter(p -> p.getKind() == kind)
                          .filter(p -> chess.getLegalMoves(player, p)
                                            .stream()
                                            .map(ChessMove::getField)
                                            .toList()
                                            .contains(targetField))
                          .filter(p -> modifiers.startFile == null || p.getPosition().file() == modifiers.startFile)
                          .filter(p -> modifiers.startRank == null || p.getPosition().rank() == modifiers.startRank)
                          .toList();

        if (pieces.isEmpty())
            throw new SAN.InvalidSANMove("No pieces match");
        else if (pieces.size() != 1)
            throw new SAN.InvalidSANMove("Move is ambiguous");
        else
            return pieces.get(0);
    }


    static ChessPieceKind pieceKind(String move) {
        if (Character.isLowerCase(move.charAt(0)))
            return PAWN;
        else
            return switch (Character.toUpperCase(move.charAt(0))) {
                case 'K' -> KING;
                case 'Q' -> QUEEN;
                case 'R' -> ROOK;
                case 'B' -> BISHOP;
                case 'N' -> KNIGHT;
                default -> throw new SAN.InvalidSANMove("Invalid piece kind");
            };
    }

    static Field targetField(String move) {
        var field = move.substring(move.length() - 2);
        return ChessField.fromString(field);
    }

    static Modifiers modifiers(String move) {
        var kind = pieceKind(move);
        var begin = kind == PAWN ? 0 : 1;
        var end = move.length() - 2;
        Modifiers result = new Modifiers();
        // Move can have optional modifiers that specify file, rank, capture
        // in case of ambiguity
        for (var c : move.substring(begin, end).toCharArray()) {
            if (ChessField.isFile(c))
                result.withStartFile(ChessField.charToFile(c));
            else if (ChessField.isRank(c))
                result.withStartRank(ChessField.charToRank(c));
            else if (c == 'x')
                result.withCapture(true);
            else
                throw new SAN.InvalidSANMove("Invalid modifier");
        }
        return result;
    }


    static class Modifiers {
        private Integer startFile;
        private Integer startRank;
        private boolean capture;

        public Modifiers withStartFile(int startFile) {
            this.startFile = startFile;
            return this;
        }

        public Modifiers withStartRank(int startRank) {
            this.startRank = startRank;
            return this;
        }

        public Modifiers withCapture(boolean capture) {
            this.capture = capture;
            return this;
        }

        @Override
        public String toString() {
            String result = "";
            if (startFile != null)
                result += ChessField.fileToChar(startFile);
            if (startRank != null)
                result += ChessField.rankToChar(startRank);
            if (capture)
                result += 'x';
            return result;
        }
    }
}
