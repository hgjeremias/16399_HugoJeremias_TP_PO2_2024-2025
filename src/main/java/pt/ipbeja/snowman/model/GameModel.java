package pt.ipbeja.snowman.model;

import pt.ipbeja.snowman.ui.GameBoard;

public class GameModel {
    private BoardModel boardModel;
    private final View view;

    public GameModel(View view) {
        this.view = view;
        this.boardModel = null; // Será definido pelo GameBoard
    }

    public void setBoardModel(BoardModel boardModel) {
        this.boardModel = boardModel;
    }

    public boolean movePlayer(Direction direction) {
        Monster player = boardModel.getPlayer();
        Position currentPos = player.getPosition();
        Position newPos = currentPos.getPositionAt(direction);

        if (!boardModel.isValidPosition(newPos)) {
            return false;
        }

        if (boardModel.isPositionEmpty(newPos)) {
            // Posição está vazia, mover jogador
            player.setPosition(newPos);
            boardModel.updateBoard();
            return true;
        }

        // Verificar se há uma bola de neve
        SnowBall snowball = boardModel.getSnowballAt(newPos);
        if (snowball != null) {
            // Tentar empurrar a bola de neve
            Position nextPos = newPos.getPositionAt(direction);

            if (boardModel.isValidPosition(nextPos) && boardModel.isPositionEmpty(nextPos)) {
                // Mover a bola de neve
                snowball.setPosition(nextPos);
                // Mover o jogador
                player.setPosition(newPos);

                // Atualizar o tabuleiro
                boardModel.updateBoard();

                // Verificar se formou um boneco de neve
                boardModel.checkForSnowman();

                return true;
            }
        }

        return false;
    }

    public boolean isGameComplete() {
        // O jogo está completo quando todas as bolas de neve foram transformadas em bonecos de neve
        return boardModel.getSnowballs().isEmpty() && !boardModel.getSnowmen().isEmpty();
    }
}

