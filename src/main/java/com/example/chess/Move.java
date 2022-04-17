package com.example.chess;

import javafx.util.*;

/**
 * A simple class responsible for keeping record of a given move
 * @author Dominik Matuszek
 */

public class Move{

    public enum File{
        A,B,C,D,E,F,G,H
    }
    public enum Rank{
        ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT
    }


    private final File fileNow;
    private final File fileNext;
    private final Rank rankNow;
    private final Rank rankNext;

    public Move(File fileNow, Rank rankNow, File fileNext, Rank rankNext){


        if(rankNow == null || rankNext == null || fileNow == null || fileNext == null){
            throw new NullPointerException();
        }

        this.rankNow = rankNow;
        this.rankNext = rankNext;

        this.fileNow = fileNow;
        this.fileNext = fileNext;
    }

    private static int fileToInt(File a){
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

    private static int rankToInt(Rank a){
        return switch(a){
            case ONE -> 1;
            case TWO -> 2;
            case THREE -> 3;
            case FOUR -> 4;
            case FIVE -> 5;
            case SIX -> 6;
            case SEVEN -> 7;
            case EIGHT -> 8;
        };
    }

    public static File inToFile(int a){
        return switch(a){
            case 1 -> File.A;
            case 2 -> File.B;
            case 3 -> File.C;
            case 4 -> File.D;
            case 5 -> File.E;
            case 6 -> File.F;
            case 7 -> File.G;
            case 8 -> File.H;
            default -> throw new IllegalStateException("Unexpected value: " + a);
        };
    }

    public static Rank intToRank(int a){
        return switch(a){
            case 1 -> Rank.ONE;
            case 2 -> Rank.TWO;
            case 3 -> Rank.THREE;
            case 4 -> Rank.FOUR;
            case 5 -> Rank.FIVE;
            case 6 -> Rank.SIX;
            case 7 -> Rank.SEVEN;
            case 8 -> Rank.EIGHT;
            default -> throw new IllegalStateException("Unexpected value: " + a);
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
    public Pair<Rank,File> getCurrentPositionEnum(){
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
    public Pair<Rank,File> getNextPositionEnum(){
        return new Pair<>(rankNext, fileNext);
    }

}