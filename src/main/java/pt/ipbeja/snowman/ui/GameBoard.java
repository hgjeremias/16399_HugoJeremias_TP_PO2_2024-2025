package pt.ipbeja.snowman.ui;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pt.ipbeja.snowman.model.*;

import java.util.ArrayList;
import java.util.List;

public class GameBoard extends GridPane implements View {
    private BoardModel boardModel;
    private GameModel gameModel;
    private static final int SIZE = 5;
    private static final int CELL_SIZE = 100;

    private Node[][] cells;

    public GameBoard() {
        this.gameModel = new GameModel(this);
        this.cells = new Node[SIZE][SIZE];
        createGameBoard();
        setupKeyboardControls();
    }

    private void createGameBoard() {
        List<List<PositionContent>> grid = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            List<PositionContent> row = new ArrayList<>();
            for(int j = 0; j < SIZE; j++) {
                PositionContent positionContent = PositionContent.SNOW;
                row.add(positionContent);

                Node cell = generateElement(i, j);
                add(cell, j, i);
                cells[i][j] = cell;
            }
            grid.add(row);
        }

        boardModel = new BoardModel(grid);
        gameModel.setBoardModel(boardModel);
        updateBoard();
    }

    private void setupKeyboardControls() {
        this.setFocusTraversable(true);
        this.requestFocus();

        this.setOnKeyPressed(event -> {
            Direction direction = null;
            if (event.getCode() == KeyCode.UP) {
                direction = Direction.UP;
            } else if (event.getCode() == KeyCode.DOWN) {
                direction = Direction.DOWN;
            } else if (event.getCode() == KeyCode.LEFT) {
                direction = Direction.LEFT;
            } else if (event.getCode() == KeyCode.RIGHT) {
                direction = Direction.RIGHT;
            }

            if (direction != null) {
                boolean moved = gameModel.movePlayer(direction);
                if (moved) {
                    updateBoard();

                    if (gameModel.isGameComplete()) {
                        showGameCompleteMessage();
                    }
                }
            }
        });
    }

    private void showGameCompleteMessage() {
        // Mostrar uma mensagem de vitória
        System.out.println("Parabéns! Você completou o jogo!");
    }

    public void updateBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                StackPane cell = (StackPane) cells[i][j];
                cell.getChildren().clear();

                // Fundo da célula
                Rectangle background = new Rectangle(CELL_SIZE, CELL_SIZE);
                background.setFill(Color.WHITE);
                background.setStroke(Color.BLACK);

                cell.getChildren().add(background);

                // Verificar o conteúdo da célula
                Position position = new Position(i, j);

                // Jogador
                if (boardModel.getPlayer().getPosition().equals(position)) {
                    Circle player = new Circle(CELL_SIZE / 4);
                    player.setFill(Color.RED);
                    cell.getChildren().add(player);
                }

                // Bolas de neve
                for (SnowBall snowball : boardModel.getSnowballs()) {
                    if (snowball.getPosition().equals(position)) {
                        Circle ball = new Circle();

                        switch (snowball.getType()) {
                            case SMALL:
                                ball.setRadius(CELL_SIZE / 6);
                                ball.setFill(Color.LIGHTBLUE);
                                break;
                            case AVERAGE:
                                ball.setRadius(CELL_SIZE / 4);
                                ball.setFill(Color.SKYBLUE);
                                break;
                            case BIG:
                                ball.setRadius(CELL_SIZE / 3);
                                ball.setFill(Color.BLUE);
                                break;
                        }

                        cell.getChildren().add(ball);
                    }
                }

                // Bonecos de neve
                for (Snowman snowman : boardModel.getSnowmen()) {
                    if (snowman.getPosition().equals(position)) {
                        Circle snowmanCircle = new Circle(CELL_SIZE / 3);
                        snowmanCircle.setFill(Color.LIGHTBLUE);
                        cell.getChildren().add(snowmanCircle);
                    }
                }
            }
        }
    }

    @Override
    public Node generateElement(int i, int j) {
        StackPane cell = new StackPane();

        Rectangle background = new Rectangle(CELL_SIZE, CELL_SIZE);
        background.setFill(Color.WHITE);
        background.setStroke(Color.BLACK);

        cell.getChildren().add(background);

        return cell;
    }
}
