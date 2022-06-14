package sample;

import sample.Game.*;

import java.io.*;
import java.util.ArrayList;

public class Test extends Thread {
    public static final String path = "D:\\IntelliJ Projects\\MillGame\\src\\tests\\";

    private GameLogic gameLogic;

    public Test(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    @Override
    public void run() {
        test(gameLogic);
    }

    public void test(GameLogic gameLogic) {
        //Both most self and no a-b
        GameLogic game = gameLogic;
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS, false, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS, false, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most_self_no-alpha-beta_Black_most_self_no-alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //Both most self and first a-b, second no a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS, true, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS, false, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most_self_alpha-beta_Black_most_self_no-alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //Both most self and a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS, true, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS, true, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most_self_alpha-beta_Black_most_self_alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//
//
//
//        //Both least enemy and no a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_least_enemy_no-alpha-beta_Black_least_enemy_no-alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //Both least enemy and first a-b, second no a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_least_enemy_alpha-beta_Black_least_enemy_no-alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //Both least enemy and a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_least_enemy_alpha-beta_Black_least_enemy_alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//
//
//        //Both most-least and no a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most-least_no-alpha-beta_Black_least_enemy_no-alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);

//        //Both most-least and first a-b, second no a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most-least_alpha-beta_Black_least_enemy_no-alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);

        //Both most-least and a-b
        game.resetGame();
        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, true, game.getBoard()));
        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, true, game.getBoard()));
        game.startGame();
        waitForTheGameToFinish(game);
        saveToFile("White_most-least_alpha-beta_Black_most-least_alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//
//
//        //First most self, second least enemy and no a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS, false, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most_self_no-alpha-beta_Black_least_enemy_no-alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //First most self, second least enemy and first a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS, true, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most_self_alpha-beta_Black_least_enemy_no-alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //First most self, second least enemy and second a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS, false, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most_self_no-alpha-beta_Black_least_enemy_alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //First most self, second least enemy and both a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS, true, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most_self_alpha-beta_Black_least_enemy_alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//
//
//        //First most self, second most-least enemy and no a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS, false, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most_self_no-alpha-beta_Black_most-least_no-alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //First most self, second most-least and first a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS, true, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most_self_alpha-beta_Black_most-least_no-alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //First most self, second most-least and second a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS, false, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most_self_no-alpha-beta_Black_most-least_alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //First most self, second most-least and both a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS, true, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most_self_alpha-beta_Black_most-least_alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//
//
//
//
//        //First least enemy, second most self enemy and no a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS, false, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_least_enemy_no-alpha-beta_Black_most_self_no-alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //First least enemy, second most self and first a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS, false, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_least_enemy_alpha-beta_Black_most_self_no-alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //First least enemy, second most self and second a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS, true, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_least_enemy_no-alpha-beta_Black_most_self_alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //First least enemy, second most self and both a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS, true, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_least_enemy_alpha-beta_Black_most_self_alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//
//
//
//
//
//        //First least enemy, second most-least enemy and no a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_least_enemy_no-alpha-beta_Black_most-least_no-alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //First least enemy, second most-least and first a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_least_enemy_alpha-beta_Black_most-least_no-alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //First least enemy, second most-least and second a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_least_enemy_no-alpha-beta_Black_most-least_alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //First least enemy, second most-least and both a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_least_enemy_alpha-beta_Black_most-least_alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//
//
//
//        //First most-least, second most self enemy and no a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS, false, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most-least_no-alpha-beta_Black_most_self_no-alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //First most-least, second most self and first a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS, false, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most-least_alpha-beta_Black_most_self_no-alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //First most-least, second most self and second a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS, true, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most-least_no-alpha-beta_Black_most_self_alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //First most-least, second most self and both a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.MOST_SELF_PAWNS, true, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most-least_alpha-beta_Black_most_self_alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//
//
//
//
//        //First most-least, second least enemy enemy and no a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most-least_no-alpha-beta_Black_least_enemy_no-alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //First most-least, second least enemy and first a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most-least_alpha-beta_Black_least_enemy_no-alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //First most-least, second least enemy and second a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, false, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most-least_no-alpha-beta_Black_least_enemy_alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);
//
//        //First most-least, second least enemy and both a-b
//        game.resetGame();
//        game.setPlayerWhite(new Computer(Pawn.WHITE, game, Heuristic.MOST_SELF_PAWNS_AND_LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.setPlayerBlack(new Computer(Pawn.BLACK, game, Heuristic.LEAST_ENEMY_PAWNS, true, game.getBoard()));
//        game.startGame();
//        waitForTheGameToFinish(game);
//        saveToFile("White_most-least_alpha-beta_Black_least_enemy_alpha-beta_Tree_depth_" + Computer.MAX_DEPTH + ".txt", game);

        System.out.println("Test skończony");
    }

    public static void saveToFile(String fileName, GameLogic game) {
        System.out.println("Rozpoczęto zapisywanie " + fileName);
        File file = new File(path + fileName);
        ArrayList<Player> players = new ArrayList<>();
        players.add(game.getWhite());
        players.add(game.getBlack());
        try {
            PrintStream printStream = new PrintStream(file);
            if(game.getWinner() != null) {
                printStream.println("Wygrał: " + playerColor(game.getWinner()));
            } else {
                printStream.println("Gra przerwana z powodu zbyt wielu rund");
            }
            printStream.println("Liczba rund: " + game.getRoundsCount());
            for(Player player : players) {
                //Write player
                printStream.println(playerColor(player));

                //Write time
                printStream.println("Całkowity czas: " + player.getTotalEvaluationTime() + " milisekund");

                //Wirte how many calls
                printStream.println("Łączna liczba wywołań funkcji min-max: " + player.getCalls());

                //Write each move and time
                for(int i = 0; i < player.getMoveHistory().size(); i++) {
                    Move move = player.getMoveHistory().get(i);
                    long time;
                    if(player.getTimeHistory().size() > i) {
                         time = player.getTimeHistory().get(i);
                    } else {
                        time = -1L;
                    }
                    //Place
                    if(move.getNodeSource() == null && move.getPawnToPlace() == player.getColor()) {
                        printStream.println("Umieszczenie swojego pionka na pole " + move.getNodeDestination().getIndex() + ". Czas szukania rozwiązania: " + time + " milisekund");
                    }
                    //Remove
                    else if(move.getNodeSource() == null) {
                        printStream.println("Usunięcia pionka przeciwnika na polu " + move.getNodeDestination().getIndex() + ". Czas szukania rozwiązania: " + time + " milisekund");
                    }
                    //Move
                    else {
                        printStream.println("Przesunięcie swojego pionka z pola " + move.getNodeSource().getIndex() + " na pole " + move.getNodeDestination().getIndex() + ". Czas szukania rozwiązania: " + time + " milisekund");
                    }
                }
                printStream.println();
            }
            printStream.close();
            System.out.println("Zapisano " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String playerColor(Player player) {
        return player.getColor() == Pawn.WHITE ? "Gracz biały" : "Gracz czarny";
    }

    public void waitForTheGameToFinish(GameLogic game) {
        try {
            while(!game.isGameFinished()) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
