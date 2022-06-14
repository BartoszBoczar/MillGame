package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Nine Men's Morris");
//        primaryStage.setScene(new Scene(root, 800, 600));
//
//        primaryStage.show();

        GameLogic gameLogic = new GameLogic();

        // set title for the stage
        primaryStage.setTitle("Mill Game");

        // set the scene
        primaryStage.setScene(gameLogic.initializeScene());

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
