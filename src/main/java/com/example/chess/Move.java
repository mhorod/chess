package com.example.chess;

import javafx.util.*;

/**
 * A simple class responsible for keeping record of a given move
 * @author Dominik Matuszek
 */

public class Move{

    public enum file{
        A,B,C,D,E,F,G,H
    }
    public enum rank{
        ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE
    }


    private final file fileNow;
    private final file fileNext;
    private final rank rankNow;
    private final rank rankNext;

    public Move(file fileNow, rank rankNow, file fileNext, rank rankNext){


        if(rankNow == null || rankNext == null || fileNow == null || fileNext == null){
            throw new NullPointerException();
        }

        this.rankNow = rankNow;
        this.rankNext = rankNext;

        this.fileNow = fileNow;
        this.fileNext = fileNext;
    }

    private int fileToInt(file a){
        return switch(a){
            case A -> 1;
            case B -> 2;
            case C -> 3;
            case D -> 4;
            case E -> 5;
            case F -> 6;
            case G -> 7;
            case H -> 8;
        };
    }

    private int rankToInt(rank a){
        return switch(a){
            case ONE -> 1;
            case TWO -> 2;
            case THREE -> 3;
            case FOUR -> 4;
            case FIVE -> 5;
            case SIX -> 6;
            case SEVEN -> 7;
            case EIGHT -> 8;
            case NINE -> 9;
        };
    }

    /**
     *
     * @return Position that given piece should be currently located on (represented by a pair of Integers, 1-8)
     */
    public Pair<Integer,Integer> getCurrentPosition(){
        return new Pair<>(fileToInt(fileNow), rankToInt(rankNow));
    }

    /**
     *
     * @return Position that given piece should be currently located on (represented by a pair of enums used for construction)
     */
    public Pair<rank,file> getCurrentPositionEnum(){
        return new Pair<>(rankNow, fileNow);
    }

    /**
     *
     * @return Position that given piece should be located on after performing a move (represented by a pair of Integers, 1-8)
     */
    public Pair<Integer,Integer> getNextPosition(){
        return new Pair<>(fileToInt(fileNext), rankToInt(rankNext));
    }


    /**
     *
     * @return Position that given piece should be located on after performing a move (represented by a pair of enums used for construction)
     */
    public Pair<rank,file> getNextPositionEnum(){
        return new Pair<>(rankNext, fileNext);
    }

}