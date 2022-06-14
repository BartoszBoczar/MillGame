package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import sample.Game.*;

import java.util.ArrayList;

import static sample.Game.Board.*;

public class UIController {
    private SplitPane windowContent;
    private VBox menu;
    private Board board;
    private Scene scene;

    private Text message;
    private Button humanVsHumna;
    private Button humanVsComputer;
    private Button computerVsComputer;
    private Button testButton;

    public static final String CURRENT_TIME_MESSAGE = "Czas wykonania ostatniego ruchu: ";
    public static final String TOTAL_TIME_MESSAGE = "Całkowity czas obecnego gracza: ";
    public static final String WHITE_PLAYER_TOTAL_TIME_MESSAGE = "Całkowity czas gracza białego: ";
    public static final String BLACK_PLAYER_TOTAL_TIME_MESSAGE = "Całkowity czas gracza czarnego: ";

    private Text currentTime;
    private Text totalTime;
    private Text currentTimeMessage;
    private Text totalTimeMessage;

    private boolean playerCanClick;
    private boolean choosePawnToMove;
    private Phase currentGamePhase;

    private boolean stillSetSecondComputer = false;
    private boolean useAplhaBeta = false;
    private boolean nowWhite = false;
    private boolean createHuman = false;

    private GameLogic gameLogic;

    public UIController(GameLogic gameLogic, Board board) {
        this.gameLogic = gameLogic;
        this.board = board;
    }

    public Scene drawGame() {
        //Create window content
        windowContent = new SplitPane();

        //Initialize Board
        playerCanClick = false;
        choosePawnToMove = false;

        //Add board to content
        windowContent.getItems().add(board);

        // add buttons
        for (int index = 0; index < board.HOW_MANY_NODES; index++) {
            // add button
            int x = index / Board.X_LENGTH;
            int y = index % Board.Y_LENGTH;
            int xReal = board.realIndex(index) / Board.X_LENGTH;
            int yReal = board.realIndex(index) % Board.Y_LENGTH;
            board.add(board.getBoardOfNodes()[index], xReal, yReal, 1, 1);
//                board.nodes[index].relocate(30 + 50 * xReal, 25 + 50 * yReal);
            board.getNode(x, y).setStyle(
                    "-fx-background-radius: 5em; " +
                            "-fx-min-width: 40px; " +
                            "-fx-min-height: 40px; " +
                            "-fx-max-width: 40px; " +
                            "-fx-max-height: 40px;" +
                            "-fx-background-color: transparent;"
            );
            Circle circle = new Circle();
            board.add(circle, xReal, yReal, 1, 1);
            board.getNode(x, y).setCircle(circle);
            circle.setRadius(17);
//            circle.setStyle ("-fx-fill: rgba(0, 255, 0, 0);");
            circle.setFill(Color.GRAY);
            circle.setStroke(Color.GRAY);
            circle.setStrokeWidth(5);
            circle.toBack();
        }

        prepareMenu();

        board.setPadding(new Insets(BUTTON_PADDING));
        board.setHgap(BUTTON_H_GAP);
        board.setVgap(BUTTON_V_GAP);


        for(int i = 0; i < board.HOW_MANY_NODES; i++) {
            nodeSetOnClick(i);
        }

        //Set background
        Image image = new Image(getClass().getResource("Better Edited Board.jpg").toString());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        windowContent.setBackground(background);

        // create a scene
        scene = new Scene(windowContent, 783, 520);

        return scene;
    }

    public void prepareMenu() {
        menu = new VBox(BUTTON_PADDING);
        windowContent.getItems().add(menu);

        message = new Text("Wybierz tryb gry:");
        humanVsHumna = new Button("Człowiek vs Człowiek");
        humanVsHumna.setOnAction( e ->  {
            disableTestButton();
            gameLogic.setPlayerWhite(new Human(Pawn.WHITE, gameLogic));
            gameLogic.setPlayerBlack(new Human(Pawn.BLACK, gameLogic));
            gameStarted();
        } );
        humanVsComputer = new Button("Człowiek vs Komputer");
        humanVsComputer.setOnAction( e ->  {
            disableTestButton();
            createHuman = true;
            menuChooseFirstComputerColor();
        } );
        computerVsComputer = new Button("Komputer vs Komputer");
        computerVsComputer.setOnAction( e ->  {
            disableTestButton();
            stillSetSecondComputer = true;
            menuChooseFirstComputerColor();
        } );

        menu.getChildren().add(message);
        menu.getChildren().add(humanVsHumna);
        menu.getChildren().add(humanVsComputer);
        menu.getChildren().add(computerVsComputer);

        menu.getChildren().add(new Text());
        menu.getChildren().add(new Text());
        currentTimeMessage = new Text(CURRENT_TIME_MESSAGE);
        menu.getChildren().add(currentTimeMessage);

        currentTime = new Text();
        menu.getChildren().add(currentTime);

        menu.getChildren().add(new Text());
        menu.getChildren().add(new Text());
        totalTimeMessage = new Text(TOTAL_TIME_MESSAGE);
        menu.getChildren().add(totalTimeMessage);
        totalTime = new Text();
        menu.getChildren().add(totalTime);

        testButton = new Button("Przeprowadź testy");
        menu.getChildren().add(testButton);
        testButton.setOnAction( e ->  {
            humanVsHumna.setDisable(true);
            humanVsHumna.setVisible(false);

            humanVsComputer.setDisable(true);
            humanVsComputer.setVisible(false);

            computerVsComputer.setDisable(true);
            computerVsComputer.setVisible(false);

            testButton.setDisable(true);
            testButton.setVisible(false);

            Test test = new Test(gameLogic);
            test.start();
        } );

        menu.setPadding(new Insets(BUTTON_PADDING));
    }

    private void disableTestButton() {
        testButton.setDisable(true);
        testButton.setVisible(false);
    }

    public void updateTime(long currentTimeValue, long totalTimeValue) {
        currentTime.setText(currentTimeValue + " milisekund");
        totalTime.setText(totalTimeValue + " milisekund");
    }

    public void menuChooseFirstComputerColor() {
        message.setText("Wybierz kolor " + (stillSetSecondComputer ? "pierwszego " : "") + "komputera");
        humanVsHumna.setOnAction(e ->  {
            nowWhite = true;
            if(createHuman) {
                createHuman = false;
                gameLogic.setPlayerBlack(new Human(Pawn.BLACK, gameLogic));
            }
            menuChooseComputerAlphaBeta();
        } );
        humanVsHumna.setText("Biały");
        humanVsComputer.setOnAction(e ->  {
            nowWhite = false;
            if(createHuman) {
                createHuman = false;
                gameLogic.setPlayerWhite(new Human(Pawn.WHITE, gameLogic));
            }
            menuChooseComputerAlphaBeta();
        } );
        humanVsComputer.setText("Czarny");
        computerVsComputer.setOnAction(null);
        computerVsComputer.setVisible(false);
    }

    public void menuChooseComputerAlphaBeta() {
        message.setText("Czy " + (stillSetSecondComputer ? "pierwszy " : "") + "komputer wykorzysta alpha-beta");
        humanVsHumna.setOnAction(e ->  {
            useAplhaBeta = true;
            menuChooseHeuristic();
        } );
        humanVsHumna.setText("Tak");
        humanVsComputer.setOnAction(e ->  {
            useAplhaBeta = false;
            menuChooseHeuristic();
        } );
        humanVsComputer.setText("Nie");
        computerVsComputer.setOnAction(null);
        computerVsComputer.setVisible(false);
    }

    public void menuChooseHeuristic() {
        message.setText("Wybierz heurystykę " + (stillSetSecondComputer ? "pierwszego " : "") + "komputera");
        humanVsHumna.setOnAction(e ->  {
            if(nowWhite) {
                nowWhite = false;
                gameLogic.setPlayerWhite(new Computer(Pawn.WHITE, gameLogic, Heuristic.MOST_SELF_PAWNS, useAplhaBeta, board));
            } else {
                nowWhite = true;
                gameLogic.setPlayerBlack(new Computer(Pawn.BLACK, gameLogic, Heuristic.MOST_SELF_PAWNS, useAplhaBeta, board));
            }
            if(stillSetSecondComputer) {
                stillSetSecondComputer = false;
                menuChooseComputerAlphaBeta();
            } else {
                gameStarted();
            }
        } );
        humanVsHumna.setText("Najwięcej własnych pionków");
        humanVsComputer.setOnAction(e ->  {
            if(nowWhite) {
                nowWhite = false;
                gameLogic.setPlayerWhite(new Computer(Pawn.WHITE, gameLogic, Heuristic.LEAST_ENEMY_PAWNS, useAplhaBeta, board));
            } else {
                nowWhite = true;
                gameLogic.setPlayerBlack(new Computer(Pawn.BLACK, gameLogic, Heuristic.LEAST_ENEMY_PAWNS, useAplhaBeta, board));
            }
            if(stillSetSecondComputer) {
                stillSetSecondComputer = false;
                menuChooseComputerAlphaBeta();
            } else {
                gameStarted();
            }
        } );
        humanVsComputer.setText("Najmniej pionków przeciwnika");
        computerVsComputer.setOnAction(e ->  {
            if(nowWhite) {
                nowWhite = false;
                gameLogic.setPlayerWhite(new Computer(Pawn.WHITE, gameLogic, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, useAplhaBeta, board));
            } else {
                nowWhite = true;
                gameLogic.setPlayerBlack(new Computer(Pawn.BLACK, gameLogic, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, useAplhaBeta, board));
            }
            if(stillSetSecondComputer) {
                stillSetSecondComputer = false;
                menuChooseComputerAlphaBeta();
            } else {
                gameStarted();
            }
        } );
        computerVsComputer.setText("Połączenie powyższych");
        computerVsComputer.setVisible(true);
    }

    public void gameStarted() {
        gameLogic.startGame();

        humanVsHumna.setDisable(true);
        humanVsHumna.setVisible(false);

        humanVsComputer.setDisable(true);
        humanVsComputer.setVisible(false);

        computerVsComputer.setDisable(true);
        computerVsComputer.setVisible(false);

    }

    public Board getBoard() {
        return board;
    }

    public void resetNodesHighLights() {
        for(int i = 0; i < board.HOW_MANY_NODES; i++) {
            for(Node node : board.getNodes()) {
                node.getCircle().setStroke(Color.GRAY);
            }
        }
    }

    public void drawChoices(ArrayList<Node> possibleChoices) {
        for(Node node : possibleChoices) {
            node.getCircle().setStroke(Color.GREEN);
        }
    }

    public void nodeSetOnClick(int index) {
        final int xFinal = index / X_LENGTH;
        final int yFinal = index % Y_LENGTH;

        board.getNode(xFinal, yFinal).setOnAction( e -> nodeClicked(xFinal, yFinal) );
    }

    public void nodeClicked(int x, int y) {

        System.out.println("Clicked " + x + " " + y);
        System.out.println("Node Index " + board.getNode(x, y).getIndex());

        //Return if mustn't click
        if(!playerCanClick || gameLogic.getPlayerWithTurn().isComputer() || gameLogic.getWinner() != null) {
            return;
        }

        //Return if node is not highlighted
        if(board.getNode(x, y).getCircle().getStroke() == Color.GRAY) {
            return;
        }

        playerCanClick = false;

        Node selected = board.getCurrentSourceNode();

        //Check if pawn has been selected
        if(selected != null) {
            if(selected.getPawnPlaced() == board.getNode(x, y).getPawnPlaced()) {
                resetNodesHighLights();
                choosePawnToMove = true;
                highlightPlayerNodes();
            } else {
                resetNodesHighLights();
                Pawn pawnToPlace = gameLogic.getPlayerWithTurn().getColor();
                gameLogic.getPlayerWithTurn().addMove(new Move(selected, board.getNode(x, y), pawnToPlace));
                choosePawnToMove = false;
            }
            board.setCurrentSourceNode(null);
        } else if(!choosePawnToMove) {
            resetNodesHighLights();
            Pawn pawnToPlace;
            if(currentGamePhase == Phase.REMOVE) {
                pawnToPlace = Pawn.EMPTY;
            } else {
                pawnToPlace = gameLogic.getPlayerWithTurn().getColor();
            }
            gameLogic.getPlayerWithTurn().addMove(new Move(selected, board.getNode(x, y), pawnToPlace));
        } else {
            resetNodesHighLights();
            selected = board.getNode(x, y);
            selected.getCircle().setStroke(Color.BLUE);
            board.setCurrentSourceNode(selected);
            choosePawnToMove = false;
            highlightNeighbours(selected);
        }

        playerCanClick = true;
    }

    public void highlightPlayerNodes() {
        drawChoices(gameLogic.playerNodes());
    }

    public void highlightNeighbours(Node node) {
        drawChoices(gameLogic.possubleEmptyNeighbours(node));
    }

    public boolean isPlayerCanClick() {
        return playerCanClick;
    }

    public void setPlayerCanClick(boolean playerCanClick) {
        this.playerCanClick = playerCanClick;
    }

    public boolean isChoosePawnToMove() {
        return choosePawnToMove;
    }

    public void setChoosePawnToMove(boolean choosePawnToMove) {
        this.choosePawnToMove = choosePawnToMove;
    }

    public void changeMessage() {
        Player currentPlayer = gameLogic.getPlayerWithTurn();
        String messageToDisplay = "Gracz ";
        if(currentPlayer.getColor() == Pawn.WHITE) {
            messageToDisplay += "Biały ";
        } else {
            messageToDisplay += "Czarny ";
        }
        if(gameLogic.getPhase(currentPlayer) == Phase.PLACE) {
            messageToDisplay += "ustawia pionka (zostało " + currentPlayer.getPawnsToPlace() + ")";
        } else if(gameLogic.getPhase(currentPlayer) == Phase.MOVE || gameLogic.getPhase(currentPlayer) == Phase.JUMP) {
            messageToDisplay += "rusza pionkiem";
        } else if(gameLogic.getPhase(currentPlayer) == Phase.REMOVE) {
            messageToDisplay += "usuwa pionka przeciwnika";
        }
        message.setText(messageToDisplay);
    }

    public void messagePlayerWon(Player player) {
        String messageToDisplay = "Gracz ";
        if(player.getColor() == Pawn.WHITE) {
            messageToDisplay += "Biały ";
        } else {
            messageToDisplay += "Czarny ";
        }
        messageToDisplay += "wygrał";
        message.setText(messageToDisplay);
    }

    public void messageNoWinner() {
        if(gameLogic.isGameTooSlow()) {
            message.setText("Gra przerwana z powodu zbyt długiego czasu gry");
        } else {
            message.setText("Gra przerwana z powodu zbyt dużej liczby rund");
        }
    }

    public void setTotalTimes(long whitePlayerTime, long blackPlayerTime) {
        currentTimeMessage.setText(WHITE_PLAYER_TOTAL_TIME_MESSAGE);
        totalTimeMessage.setText(BLACK_PLAYER_TOTAL_TIME_MESSAGE);
        currentTime.setText(whitePlayerTime + " milisekund");
        totalTime.setText(blackPlayerTime + "milisekund");
    }

    public Phase getCurrentGamePhase() {
        return currentGamePhase;
    }

    public void setCurrentGamePhase(Phase currentGamePhase) {
        this.currentGamePhase = currentGamePhase;
    }
}
