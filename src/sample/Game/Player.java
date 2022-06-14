package sample.Game;

import sample.GameLogic;

import java.util.ArrayList;

public abstract class Player {
    public static final short STARTING_PAWNS_COUNT = 9;

    protected Player enemy;
    protected boolean isComputer;
    protected Pawn color;
    protected int pawnsOnBoard;
    protected int pawnsToPlace;
    protected boolean hasMill;
    protected GameLogic gameLogic;

    protected Move lastMove;
    protected Move currentMove;
    protected ArrayList<Move> moveHistory;
    protected ArrayList<Long> timeHistory;

    protected long totalEvaluationTime = 0;
    protected long startTime;
    protected long stopTime;
    protected long howManyCalls;

    public Player(Pawn color, GameLogic gameLogic) {
        this.color = color;
        pawnsOnBoard = 0;
        pawnsToPlace = STARTING_PAWNS_COUNT;
        hasMill = false;
        lastMove = null;
        currentMove = null;
        moveHistory = null;
        this.gameLogic = gameLogic;
        moveHistory = new ArrayList<>();
        timeHistory = new ArrayList<>();
        howManyCalls = 0;
    }

    public Pawn getColor() {
        return color;
    }

    public int getPawnsOnBoard() {
        return pawnsOnBoard;
    }

    public int getPawnsToPlace() {
        return pawnsToPlace;
    }

    public void pawnPlaced() {
        pawnsToPlace--;
        pawnsOnBoard++;
    }

    public void pawnRemoved() {
        pawnsOnBoard--;
    }

    public boolean isHasMill() {
        return hasMill;
    }

    public void setHasMill(boolean hasMill) {
        this.hasMill = hasMill;
    }

    public boolean isComputer() {
        return isComputer;
    }

    public void setLastMove(Move lastMove) {
        this.lastMove = lastMove;
    }

   public abstract void decideNextMove(boolean hasMill);

    public abstract void addMove(Move move);

    public abstract void gameEnd();

    protected void startTimer() {
        startTime = System.currentTimeMillis();
    }

    protected long stopTimer() {
        stopTime = System.currentTimeMillis();
        long result = stopTime - startTime;
        totalEvaluationTime += result;
        return result;
    }

    public Player getEnemy() {
        return enemy;
    }

    public void setEnemy(Player enemy) {
        this.enemy = enemy;
    }

    public ArrayList<Move> getMoveHistory() {
        return moveHistory;
    }

    public ArrayList<Long> getTimeHistory() {
        return timeHistory;
    }

    public long getTotalEvaluationTime() {
        return totalEvaluationTime;
    }

    public long getCalls() {
        return howManyCalls;
    }
}
