import java.util.ArrayList;

class Move {

    int xBB;
    int oBB;
    int mBB;
    int score = 0;
    boolean isMax;
    ArrayList<Move> nextMoves = new ArrayList<>();

    Move(int xBB, int oBB, boolean isMax) {
        this.xBB = xBB;
        this.oBB = oBB;
        this.mBB = oBB | xBB;
        this.isMax = isMax;
        findNextPossibleMoves();
    }

    int getNextXBB(int i) {
        if(isMax) {return xBB | BitBoard.mask(i);}
        return xBB;
    }

    int getNextOBB(int i) {
        if(!isMax) {return oBB | BitBoard.mask(i);}
        return oBB;
    }

    void findNextPossibleMoves() {
        for(int i = 8; i >= 0; i--) {
            if((mBB << i) == 0) {
                nextMoves.add(new Move(getNextXBB(i), getNextOBB(i), !isMax));
            }
        }
    }


}

class Minimax {

    static int minimax(Move mv, boolean isMax) {

        if(mv.nextMoves.isEmpty()) {
            return mv.score;
        }

        ArrayList<Integer> scores = new ArrayList<>();

        if(isMax) {
            for(Move m : mv.nextMoves) {
                scores.add(minimax(m, false));
            }
            mv.score = findLargest(scores);
            return mv.score;

        }
        else {
            for(Move m : mv.nextMoves) {
                scores.add(minimax(m, true));
            }
            mv.score = findSmallest(scores);
            return mv.score;
        }

    }

    // Util functions
    static int findLargest(ArrayList<Integer> scores) {
        int max = scores.getFirst();

        for(int i = 1; i < scores.size(); i++) {
            if(scores.get(i) > max) {
                max = scores.get(i);
            }
        }
        return max;
    }

    // Util functions
    static int findSmallest(ArrayList<Integer> scores) {
        int min = scores.getFirst();

        for (int i = 1; i < scores.size(); i++) {
            if (scores.get(i) < min) {
                min = scores.get(i);
            }
        }
        return min;
    }
}
