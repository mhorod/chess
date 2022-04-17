package com.example.chess;

import java.util.ArrayList;
import java.util.List;

record LogicalField(int x, int y) {}

public class LogicalPiece {
    int x, y;
    LogicalPiece(int x, int y) {
        this.x = x;
        this.y = y;
    }
    List<LogicalField> getLegalMoves() {
        ArrayList<LogicalField> result = new ArrayList<>();
        int[] xDiffs = {0, 1, -1, 1, -1, 2, 2, -2, -2};
        int[] yDiffs = {0, 2, 2, -2, -2, 1, -1, 1, -1};
        for(int i = 0; i < xDiffs.length; i++)
            if(x + xDiffs[i] >= 0 && x + xDiffs[i] < 8 && y + yDiffs[i] >= 0 && y + yDiffs[i] < 8)
                result.add(new LogicalField(x + xDiffs[i], y + yDiffs[i]));
        return result;
    }

    void makeMove(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
