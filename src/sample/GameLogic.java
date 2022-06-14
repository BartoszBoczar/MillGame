package sample;

import javafx.scene.Scene;
import sample.Game.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class GameLogic {
    public static final int WAITING_TINE_BETWEEN_COMPUTER_MOVES = 1000;
    public static final int MAX_ROUNDS_COUNT = 300;
    public static final long MAX_GAME_TIME = 1200000;

    private UIController uiController;
    private Board board;
    private Player white;
    private Player black;

    private Player playerWithTurn;
    private Player winner;

    private MoveProcessor moveProcessor;

    private boolean gameFinished = false;
    private int roundsCount;
    private long gameStartTime;
    private boolean gameTooSlow;

    public GameLogic() {
        //TODO: Initialize players and playerWithTurn
        board = new Board();
        uiController = new UIController(this, board);
        winner = null;
        playerWithTurn = white;
        roundsCount = 0;
        gameStartTime = 0;
    }

    public void resetGame() {
        //Reset nodes
        for(Node node : board.getNodes()) {
            node.setPawnPlaced(Pawn.EMPTY);
            node.getSimplifiedSelf().setPawnPlaced(Pawn.EMPTY);
            node.setLastSource(null);
            node.getSimplifiedSelf().setLastSourceIndex(-1);
        }

        //Reset lines
        for(Line line : board.getLines()) {
            line.setMillUsed(false);
            line.getSimplifiedSelf().setMillUsed(false);
        }

        //Reset players
        if(white != null) {
            white.gameEnd();
        }
        if(black != null) {
            black.gameEnd();
        }
        white = null;
        black = null;
        winner = null;
        gameFinished = false;
        playerWithTurn = null;
        roundsCount = 0;
        gameStartTime = 0;
        gameTooSlow = false;
    }

    public void startGame() {
        setEnemies();
        roundsCount = 0;
        gameTooSlow = false;
        gameStartTime = System.currentTimeMillis();
        moveProcessor = new MoveProcessor(this);
        moveProcessor.start();

        if(playerWithTurn.isComputer()) {
            playerWithTurn.decideNextMove(false);
        } else {
            uiController.drawChoices(possibleChocies(playerWithTurn));
            uiController.changeMessage();
            uiController.setPlayerCanClick(true);
        }
    }

    public void addMove(Move move, long currentEvaluationTime, long totalEvaluationTime) {
        uiController.updateTime(currentEvaluationTime, totalEvaluationTime);
        moveProcessor.addMove(move);
    }

    public void playRound(Move move) {
        //Increase rounds count
        roundsCount++;
        if(roundsCount  > MAX_ROUNDS_COUNT) {
            finishGame();
            return;
        }
        if(System.currentTimeMillis() - gameStartTime > MAX_GAME_TIME) {
            gameTooSlow = true;
            finishGame();
            return;
        }

        //Forbid clikcing
        uiController.setPlayerCanClick(false);

        //Reset highlights
        uiController.resetNodesHighLights();

//        System.out.println("Gracz " + playerWithTurn.getColor() + " ustawia pionka " + move.getPawnToPlace() + (move.getNodeSource() != null ? " z pozycji " + move.getNodeSource().getIndex() : "") + " na poazycję " + move.getNodeDestination().getIndex() );

        //Apply move
        if(move.getNodeSource() != null) {
            move.getNodeSource().setPawnPlaced(Pawn.EMPTY);
        }
        move.getNodeDestination().setPawnPlaced(move.getPawnToPlace());
        if(getPhase(playerWithTurn) == Phase.MOVE) {
            playerWithTurn.setLastMove(move);
            move.getNodeDestination().setLastSource(move.getNodeSource());
        }

        //Check if player has mill
        boolean hasMill = false;
        int howManyMills = 0;
        for(int i = 0; i < board.getLines().length && !hasMill; i++) {
            if(board.getLines()[i].hasMill(playerWithTurn) && !board.getLines()[i].isMillUsed()) {
//                if(playerWithTurn.isComputer()) {
//                    //Computer use mill
//                    playerWithTurn.decideNextMove();
//                    return;
//                } else {
//                    hasMill = true;
//                }
                hasMill = true;
                board.getLines()[i].setMillUsed(true);
                howManyMills++;
            }
        }

        //Reset used mills
        for(int i = 0; i < board.getLines().length && !hasMill; i++) {
            board.getLines()[i].resetIfNoMill();
        }

        //Set hasMill for the player
        if(hasMill) {
            playerWithTurn.setHasMill(true);
        } else {
            playerWithTurn.setHasMill(false);
        }

        //Change values accoring to the phase
        Phase currentPhase = getPhase(playerWithTurn);
        if(currentPhase == Phase.PLACE) {
            playerWithTurn.pawnPlaced();
        } else if(currentPhase == Phase.REMOVE) {
            getEnemy().pawnRemoved();
        }

        //Set choose pawn to move
        uiController.setChoosePawnToMove(currentPhase == Phase.MOVE || currentPhase == Phase.JUMP);

        //Change current player with turn if no mill
        if(!hasMill) {
            if(playerWithTurn.getColor() == Pawn.WHITE) {
                playerWithTurn = black;
            } else {
                playerWithTurn = white;
            }
        }
        uiController.setCurrentGamePhase(getPhase(playerWithTurn));

        //Find possible choices
        ArrayList<Node> possibleChoices = possibleChocies(playerWithTurn);
        //Check if player is not able to move
        if(possibleChoices.size() == 0 || !canMove(playerWithTurn)) {
            if(playerWithTurn.getColor() == black.getColor()) {
                winner = white;
                finishGame();
                return;
            } else {
                winner = black;
                finishGame();
                return;
            }
        }


        //Check if game over
        if(checkIfPlayerLost(black)) {
            winner = white;
            finishGame();
            return;
        } else if(checkIfPlayerLost(white)) {
            winner = black;
            finishGame();
            return;
        }

        //Change message
        uiController.changeMessage();

        //Send board if player is a computer
        if(playerWithTurn.isComputer()) {
            playerWithTurn.decideNextMove(howManyMills > 0);
            try {
                Thread.sleep(WAITING_TINE_BETWEEN_COMPUTER_MOVES);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //Draw possible choices for the player
            uiController.drawChoices(possibleChoices);
            uiController.setPlayerCanClick(true);
        }
    }

    public void finishGame() {
        if(winner != null) {
            uiController.messagePlayerWon(winner);
        } else {
            uiController.messageNoWinner();
        }
        uiController.setTotalTimes(white.getTotalEvaluationTime(), black.getTotalEvaluationTime());
        gameFinished = true;
    }

    public boolean canMove(Player player) {
        if(getPhase(player) != Phase.MOVE) {
            return true;
        }
        for(Node node : board.getNodes()) {
            if(node.getPawnPlaced() == player.getColor()) {
                for(int neighbourIndex : node.getNodesAroundIndexes()) {
                    if(board.getNodes()[neighbourIndex].getPawnPlaced() == Pawn.EMPTY) {
                        if(node.getLastSource() == null) {
                            return true;
                        }
                        else if(board.getNodes()[neighbourIndex].getIndex() != node.getLastSource().getIndex()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public Scene initializeScene() {
        return uiController.drawGame();
    }


    public ArrayList<Node> possibleChocies(Player player) {
        ArrayList<Node> results = new ArrayList<>();

        //Phase place
        if(getPhase(player) == Phase.PLACE) {
            for(Node node : board.getNodes()) {
                if(node.getPawnPlaced() == Pawn.EMPTY) {
                    results.add(node);
                }
            }
        }
        //Phase move
        else if(getPhase(player) == Phase.MOVE) {
            //Find source
            uiController.setChoosePawnToMove(true);
            for(Node node : board.getNodes()) {
                if(node.getPawnPlaced() == player.getColor()) {
                    results.add(node);
                }
            }
//            //Source chosen
//            else {
//                for(Node node : board.getNodes()) {
//                    if(board.getCurrentSourceNode().isNodeAround(node) && node.getPawnPlaced() == Pawn.EMPTY) {
//                        results.add(node);
//                    }
//                }
//            }
        }
        //Phase jump
        else if(getPhase(player) == Phase.JUMP) {
            //Find source
            uiController.setChoosePawnToMove(true);
            for(Node node : board.getNodes()) {
                if(node.getPawnPlaced() == player.getColor()) {
                    results.add(node);
                }
            }
//            //Source chosen
//            else {
//                for(Node node : board.getNodes()) {
//                    if(node.getPawnPlaced() == Pawn.EMPTY) {
//                        results.add(node);
//                    }
//                }
//            }
        }
        //Phase remove
        else {
            Pawn enemyPawn = getEnemy().getColor();
            for(Node node : board.getNodes()) {
                if(node.getPawnPlaced() == enemyPawn) {
                    results.add(node);
                }
            }
            playerWithTurn.setHasMill(false);
        }
        return results;
    }

    public ArrayList<Node> possibleNodesToRemove(Player player) {
        ArrayList<Node> results = new ArrayList<>();
        Pawn enemyColor = getEnemy().getColor();
        for(Node node : board.getNodes()) {
            if(node.getPawnPlaced() == enemyColor) {
                results.add(node);
            }
        }
        return results;
    }

    public ArrayList<Node> possubleEmptyNeighbours(Node nodeToCheck) {
        ArrayList<Node> results = new ArrayList<>();
        for(int index : nodeToCheck.getNodesAroundIndexes()) {
            Node neighbour = board.getNodes()[index];
            if(!checkIfGoesBack(nodeToCheck, neighbour) && neighbour.getPawnPlaced() == Pawn.EMPTY) {
                results.add(neighbour);
            }
        }
        return results;
    }
    public boolean checkIfGoesBack(Node nodeToCheck, Node neighbour) {
        if(nodeToCheck.getLastSource() == null) {
            return false;
        }

        return nodeToCheck.getLastSource().getIndex() == neighbour.getIndex();
    }

    public ArrayList<Node> playerNodes() {
        ArrayList<Node> results = new ArrayList<>();
        for(Node node : board.getNodes()) {
            if(node.getPawnPlaced() == playerWithTurn.getColor()) {
                results.add(node);
            }
        }
        return results;
    }

    public boolean checkIfPlayerLost(Player player) {
        if(player.getPawnsOnBoard() < 3 && player.getPawnsToPlace() == 0) {
            return true;
        }
        return false;
    }

    public Phase getPhase(Player player) {
        if(player.isHasMill()) {
            return Phase.REMOVE;
        } else if(player.getPawnsToPlace() > 0) {
            return Phase.PLACE;
        } else if(player.getPawnsOnBoard() > 3) {
            return Phase.MOVE;
        } else {
            return Phase.JUMP;
        }
    }

    public Player getPlayerWithTurn() {
        return playerWithTurn;
    }


    public void setPlayerWhite(Player player) {
        white = player;
        playerWithTurn = white;
    }

    public void setPlayerBlack(Player player) {
        black = player;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getEnemy() {
        if(playerWithTurn.getColor() == Pawn.WHITE) {
            return black;
        } else {
            return white;
        }
    }

    public void setEnemies() {
        white.setEnemy(black);
        black.setEnemy(white);
    }

    public Board getBoard() {
        return board;
    }

    public Player getWhite() {
        return white;
    }

    public int getRoundsCount() {
        return roundsCount;
    }

    public Player getBlack() {
        return black;
    }

    public boolean isGameTooSlow() {
        return gameTooSlow;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    private class MoveProcessor extends Thread {
        private GameLogic gameLogic;
        private Queue<Move> moves;

        public MoveProcessor(GameLogic gameLogic) {
            super();
            this.gameLogic = gameLogic;
            moves = new ArrayDeque<>();
        }

        public void run() {
            while(gameLogic.getWinner() == null && !gameFinished) {
                if(!moves.isEmpty()) {
                    gameLogic.playRound(moves.poll());
                } else {
                    try {
                        Thread.sleep(10);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void addMove(Move move) {
            moves.add(move);
        }
    }
}
