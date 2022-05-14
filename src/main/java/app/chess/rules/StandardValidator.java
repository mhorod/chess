package app.chess.rules;

import app.chess.*;
import app.chess.moves.*;

import java.util.*;

public class StandardValidator implements Validator {
    private final RuleFactory factory = new StandardRuleFactory();
    @Override
    public List<ChessMove> getLegalMoves(ChessPiece piece, ChessPiece[][] board, List<Rule> rules) {

        List<ChessMove> legalMoves = new ArrayList<>();

        for(var potentialMove : RulesPieceConverter.convert(piece).getPotentialMoves()){
            boolean badMove = false;

            for(var rule : rules){
                if(!rule.validate(potentialMove,board)){
                    badMove = true;
                    break;
                }
            }

            if(!badMove){
                legalMoves.add(potentialMove);
            }
        }

        return legalMoves;
    }

    @Override
    public List<ChessMove> getLegalMoves(ChessPiece piece, ChessPiece[][] board) {
        return getLegalMoves(piece, board, getDefaultRules());
    }

    @Override
    public List<Rule> getDefaultRules() {
        return factory.getRules();
    }
}
