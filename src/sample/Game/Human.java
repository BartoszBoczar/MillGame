package sample.Game;

import sample.GameLogic;

public class Human extends Player {
    //TODO: Must extend player

    public Human(Pawn pawn, GameLogic gameLogic) {
        super(pawn, gameLogic);
        isComputer = false;
    }

    public void decideNextMove(boolean hasMill) {
        startTimer();
    }

    public void addMove(Move move) {
        long currentEvaluationTime = stopTimer();
        moveHistory.add(move);
        timeHistory.add(currentEvaluationTime);
        gameLogic.addMove(move, currentEvaluationTime, totalEvaluationTime);
    }

    public void gameEnd() {

    }

}
