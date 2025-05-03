package pt.ipbeja.snowman.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GameStart extends Application {
    @Override
    public void start(Stage stage) {

        GameBoard gameBoard = new GameBoard();

        Scene scene = new Scene(gameBoard);
        stage.setScene(scene);
        stage.setTitle("A Good Snowman is Hard to Build");
        stage.show();

        // Importante para capturar eventos de teclado
        gameBoard.requestFocus();


    }

    public static void main(String[] args) {
        launch();
    }
}
