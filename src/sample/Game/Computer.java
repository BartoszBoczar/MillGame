package sample.Game;

import sample.GameLogic;

import java.util.ArrayList;

import static java.lang.Math.min;
import static sun.swing.MenuItemLayoutHelper.max;

public class Computer extends Player {
    public static final int MAX_DEPTH = 6;
    public static final int WIN_VALUE = 100;
    public static final int LOSE_VALUE = -100;

    private Heuristic heuristic;
    private boolean alphaBeta;
    private ComputerProcessor processor;
    private boolean processorInitialized = false;
    Board board;

    public Computer(Pawn pawnColor, GameLogic gameLogic, Heuristic heuristic, boolean alphaBeta, Board board) {
        super(pawnColor, gameLogic);
        isComputer = true;
        this.heuristic = heuristic;
        this.alphaBeta = alphaBeta;
        this.board = board;
    }

    private void initializeProcessor(Board board, SimplifiedNode[] simplifiedNodes, SimplifiedLine[] simplifiedLines) {
        processor = new ComputerProcessor(heuristic, alphaBeta, simplifiedNodes, simplifiedLines, this, board);
        processor.start();
        processorInitialized = true;
    }

    public void decideNextMove(boolean hasMill) {
        //TODO: implement
        if(!processorInitialized) {
            initializeProcessor(board, board.getSimplifiedNodes(), board.getSimplifiedLines());
        }
        startTimer();
        processor.evaluateNextMove(hasMill);

    }

    public void addMove(Move move) {
        //TODO: implement
        long currentEvaluationTime = stopTimer();
        moveHistory.add(move);
        timeHistory.add(currentEvaluationTime);
        gameLogic.addMove(move, currentEvaluationTime, totalEvaluationTime);
        processor.lastChosenDestinationIndex = -1;
    }

    public int minimax(SimplifiedNode[] currentBoard, SimplifiedLine[] lines, int currentDepth, boolean enemyTurn,
                       int pawnsToPlaceEnemy, int pawnsToPlaceSelf, boolean nowHasMill, int alpha, int beta) {

        //One more call
        howManyCalls++;

        //Finish if max depth reached
        if(currentDepth == MAX_DEPTH) {
            return evaluateBoard(currentBoard);
        }

        //Finish if game ended for current player
        if(checkIfSomeoneLost(currentBoard, pawnsToPlaceEnemy, pawnsToPlaceSelf)) {
            if(checkIfEnemyLost(currentBoard, pawnsToPlaceEnemy, pawnsToPlaceSelf)) {
                return WIN_VALUE;
            }
            return LOSE_VALUE;
        }

        //Check phase
        Player playerWithTurn = enemyTurn ? enemy : this;
        Phase phase = checkPhase(playerWithTurn, nowHasMill, enemyTurn ? pawnsToPlaceEnemy : pawnsToPlaceSelf,
                currentBoard);

        //Finish if can't move
        if(phase == Phase.MOVE) {
            if(!checkIfCanMove(playerWithTurn, currentBoard)) {
                if(playerWithTurn.getColor() == this.getColor()) {
                    return LOSE_VALUE;
                } else {
                    return WIN_VALUE;
                }
            }
        }

        ArrayList<ComputerChoice> allChoices = allPossibleChoices(playerWithTurn, phase, currentBoard);

        //Choose starting value depending on who's turn it it
        int bestSolution;
        ComputerChoice localBestChoice = null;
        if(!enemyTurn) {
            bestSolution = Integer.MIN_VALUE;
        } else {
            bestSolution = Integer.MAX_VALUE;
        }

        //Evaluate each choice
        for(ComputerChoice choice : allChoices) {

            //Apply move
            choice.getDestination().setPawnPlaced(choice.getPawnToPlace());
            if(choice.getSource() != null) {
                choice.getSource().setPawnPlaced(Pawn.EMPTY);
            }
            if(phase == Phase.PLACE) {
                if(enemyTurn) {
                    pawnsToPlaceEnemy--;
                } else {
                    pawnsToPlaceSelf--;
                }
            }
            SimplifiedLine usedMill = null;
            if(nowHasMill) {
                usedMill = useMill(playerWithTurn, lines);
            }
            //Set Last Source
            int previousLastSourceIndex = -1;
            if(phase == Phase.MOVE || phase == Phase.JUMP) {
                previousLastSourceIndex = choice.getDestination().getLastSourceIndex();
                choice.getDestination().setLastSourceIndex(choice.getSource().getIndex());
            }

            //Reset lines without mills
            ArrayList<SimplifiedLine> resetLines = new ArrayList<>();
            if(phase != Phase.REMOVE && phase != Phase.PLACE) {
                resetLines = resetLines(lines, choice.getDestination());
            }

            //Set hasMill
            boolean previousNowHasMill = nowHasMill;
            nowHasMill = hasMill(playerWithTurn, lines);

            //Evaluate children
            int evaluatedChild = minimax(currentBoard, lines, currentDepth + 1, nowHasMill ? enemyTurn : !enemyTurn,
                    pawnsToPlaceEnemy, pawnsToPlaceSelf, nowHasMill, alpha, beta);

            //Choose solution depending on who's turn it is
            if(!enemyTurn ? bestSolution < evaluatedChild : bestSolution > evaluatedChild) {
                bestSolution = evaluatedChild;
                localBestChoice = choice;
            }

            //Reverse any changes
            choice.getDestination().setPawnPlaced(choice.getOriginalDestinationPawn());
            if(choice.getSource() != null) {
                choice.getSource().setPawnPlaced(choice.getPawnToPlace());
            }
            if(phase == Phase.PLACE) {
                if(enemyTurn) {
                    pawnsToPlaceEnemy++;
                } else {
                    pawnsToPlaceSelf++;
                }
            }
            nowHasMill = previousNowHasMill;
            if(usedMill != null) {
                usedMill.setMillUsed(false);
            }

            //Reverse Last Source
            if(phase == Phase.MOVE || phase == Phase.JUMP) {
                choice.getDestination().setLastSourceIndex(previousLastSourceIndex);
            }

            //Reverse line reset
            for(int i = 0; i < resetLines.size(); i++) {
                resetLines.get(i).setMillUsed(true);
            }

            //Use aplha-beta
            if(alphaBeta) {
                if(!enemyTurn) {
                    alpha = max(alpha, evaluatedChild);
                    if(beta <= alpha) {
                        return evaluatedChild;
                    }
                } else {
                    beta = min(beta, evaluatedChild);
                    if(beta <= alpha) {
                        return evaluatedChild;
                    }
                }
            }

        }
        if(localBestChoice == null) {
            int a = 1;
        }
        processor.setBestChoice(localBestChoice);
        return bestSolution;
    }

    //Resets lines containing node and returns array of thoes lines
    public ArrayList<SimplifiedLine> resetLines(SimplifiedLine[] lines, SimplifiedNode node) {
        ArrayList<SimplifiedLine> result = new ArrayList<>();
        for(SimplifiedLine line : lines) {
            if(line.hasNode(node)) {
                if(line.resetIfNoMill()) {
                    result.add(line);
                }
            }
        }
        return result;
    }

    public boolean checkIfEnemyLost(SimplifiedNode[] board, int pawnsToPlaceEnemy, int pawnsToPlaceSelf) {
        int enemyPawnsCount = 0;
        for(SimplifiedNode node : board) {
            if(node.getPawnPlaced() == enemy.getColor()) {
                enemyPawnsCount++;
            }
        }

        if(enemyPawnsCount < 3) {
            return true;
        }
        return false;
    }

    public boolean checkIfCanMove(Player player, SimplifiedNode[] currentBoard) {
        for(SimplifiedNode node : currentBoard) {
            if(node.getPawnPlaced() == player.getColor()) {
                for(int neighbourIndex : node.getNodesAroundIndexes()) {
                    if(currentBoard[neighbourIndex].getPawnPlaced() == Pawn.EMPTY) {
                        if(node.getLastSourceIndex() == -1) {
                            return true;
                        }
                        else if(currentBoard[neighbourIndex].getIndex() != node.getLastSourceIndex()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkIfSomeoneLost(SimplifiedNode[] board, int pawnsToPlaceEnemy, int pawnsToPlaceSelf) {
        if((pawnsToPlaceEnemy > 0) && (pawnsToPlaceSelf > 0)) {
            return false;
        }

        int selfPawnsCount = 0;
        int enemyPawnsCount = 0;

        for(SimplifiedNode node : board) {
            if(node.getPawnPlaced() == color) {
                selfPawnsCount++;
            } else if(node.getPawnPlaced() == enemy.getColor()) {
                enemyPawnsCount++;
            }
        }

        if(selfPawnsCount < 3 && enemyPawnsCount < 3) {
            return true;
        }
        return false;
    }

    public Phase checkPhase(Player player, boolean hasMill, int pawnsToPlace, SimplifiedNode[] board) {
        if(hasMill) {
            return Phase.REMOVE;
        } else if(pawnsToPlace > 0) {
            return Phase.PLACE;
        } else if(checkPawnsOnBoard(player, board) > 3) {
            return Phase.MOVE;
        } else {
            return Phase.JUMP;
        }
    }

    public int evaluateBoard(SimplifiedNode[] board) {
        int result = 0;

        //Choose Heuristic
        if(heuristic == Heuristic.MOST_SELF_PAWNS) {
            result = checkPawnsOnBoard(this, board);
        } else if(heuristic == Heuristic.LEAST_ENEMY_PAWNS) {
            result = -checkPawnsOnBoard(enemy, board);
        } else if(heuristic == Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS) {
            result = checkPawnsOnBoard(this, board) * 5 - checkPawnsOnBoard(enemy, board);
        }

        return result;
    }

    public int checkPawnsOnBoard(Player player, SimplifiedNode[] board) {
        int result = 0;
        for(SimplifiedNode node : board) {
            if(node.getPawnPlaced() == player.getColor()) {
                result++;
            }
        }
        return result;
    }

    public boolean hasMill(Player player, SimplifiedLine[] lines) {
        for(SimplifiedLine line : lines) {
            if(line.hasMill(player) && !line.isMillUsed()) {
                return true;
            }
        }
        return false;
    }

    public SimplifiedLine useMill(Player player, SimplifiedLine[] lines) {
        for(SimplifiedLine line : lines) {
            if(line.hasMill(player) && !line.isMillUsed()) {
                line.setMillUsed(true);
                return line;
            }
        }
        return null;
    }

    public ArrayList<ComputerChoice> allPossibleChoices(Player player, Phase phase, SimplifiedNode[] currentBoard) {
        ArrayList<ComputerChoice> results = new ArrayList<>();

        //Phase place
        if(phase == Phase.PLACE) {
            for(SimplifiedNode node : currentBoard) {
                if(node.getPawnPlaced() == Pawn.EMPTY) {
                    results.add(new ComputerChoice(null, node, player.getColor(), Pawn.EMPTY));
                }
            }
        }
        //Phase move
        else if(phase == Phase.MOVE) {
            for(SimplifiedNode nodeFrom : currentBoard) {
                if(nodeFrom.getPawnPlaced() == player.getColor()) {
                    for(int index : nodeFrom.getNodesAroundIndexes()) {
                        if(currentBoard[index].getPawnPlaced() == Pawn.EMPTY) {
                            if(nodeFrom.getLastSourceIndex() != currentBoard[index].getIndex()) {
                                results.add(new ComputerChoice(nodeFrom, currentBoard[index], nodeFrom.getPawnPlaced(), Pawn.EMPTY));
                            }
                        }
                    }
                }
            }
        }

        //Phase jump
        else if(phase == Phase.JUMP) {
            for(SimplifiedNode nodeFrom : currentBoard) {
                if(nodeFrom.getPawnPlaced() == player.getColor()) {
                    for(SimplifiedNode nodeTo : currentBoard) {
                        if(nodeTo.getPawnPlaced() == Pawn.EMPTY) {
                            if(nodeFrom.getLastSourceIndex() != nodeTo.getIndex()) {
                                results.add(new ComputerChoice(nodeFrom, nodeTo, nodeFrom.getPawnPlaced(), Pawn.EMPTY));
                            }
                        }
                    }
                }
            }
        }

        //Phase remove
        else {
            for(SimplifiedNode node : currentBoard) {
                if(node.getPawnPlaced() == player.enemy.getColor()) {
                    results.add(new ComputerChoice(null, node, Pawn.EMPTY, player.enemy.getColor()));
                }
            }
        }
        return results;
    }

    public void gameEnd() {
        processor.setStayInLoop(false);
    }

    private class ComputerProcessor extends Thread {
        private Heuristic heuristic;
        private boolean alphaBeta;
        private SimplifiedNode[] simplifiedNodes;
        private SimplifiedLine[] lines;
        private Board board;
        private Player owner;
        private boolean stayInLoop = true;
        private boolean nextMove = false;
        private int lastChosenDestinationIndex;
        private boolean hasMill;
        private ComputerChoice bestChoice = null;

        public ComputerProcessor(Heuristic heuristic, boolean alphaBeta, SimplifiedNode[] simplifiedNodes,
                                 SimplifiedLine[] lines, Player owner, Board board) {
            super();
            this.heuristic = heuristic;
            this.alphaBeta = alphaBeta;
            this.simplifiedNodes = simplifiedNodes;
            this.lines = lines;
            this.owner = owner;
            this.board = board;
        }

        @Override
        public void run() {
            while(stayInLoop) {
                if(nextMove) {
                    minimax(simplifiedNodes,  lines, 0, false,
                            enemy.getPawnsToPlace(), pawnsToPlace, hasMill, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    Move moveChosen;
                    if(bestChoice == null) {
                        int a = 0;
                    }
                    if(bestChoice.getSource() == null) {
                        moveChosen = new Move(null,
                                board.getNodes()[bestChoice.getDestination().getIndex()], bestChoice.getPawnToPlace());
                    } else {
                        moveChosen = new Move(board.getNodes()[bestChoice.getSource().getIndex()],
                                board.getNodes()[bestChoice.getDestination().getIndex()], bestChoice.getPawnToPlace());
                    }
                    bestChoice = null;
                    lastChosenDestinationIndex = moveChosen.getNodeDestination().getIndex();
                    owner.addMove(moveChosen);
                    nextMove = false;
                } else {
                    try {
                        Thread.sleep(5);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void evaluateNextMove(boolean hasMill) {
            this.hasMill = hasMill;
            nextMove = true;
        }

        public void finish() {
            stayInLoop = false;
        }

        public ComputerChoice getBestChoice() {
            return bestChoice;
        }

        public void setBestChoice(ComputerChoice bestChoice) {
            this.bestChoice = bestChoice;
        }

        public void setStayInLoop(boolean stayInLoop) {
            this.stayInLoop = stayInLoop;
        }
    }

}
