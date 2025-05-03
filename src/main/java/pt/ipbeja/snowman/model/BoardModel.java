package pt.ipbeja.snowman.model;

import java.util.ArrayList;
import java.util.List;

public class BoardModel {
    private List<List<PositionContent>> board;
    private Monster player;
    private List<SnowBall> snowballs;
    private List<Snowman> snowmen;
    private int size;

    public BoardModel(List<List<PositionContent>> board) {
        this.board = board;
        this.size = board.size();
        this.snowballs = new ArrayList<>();
        this.snowmen = new ArrayList<>();

        // Inicialmente vamos colocar o jogador no centro
        this.player = new Monster(new Position(size/2, size/2));

        // Adicionar algumas bolas de neve iniciais
        snowballs.add(new SnowBall(new Position(1, 1), SnowBallType.BIG));
        snowballs.add(new SnowBall(new Position(1, 3), SnowBallType.AVERAGE));
        snowballs.add(new SnowBall(new Position(3, 3), SnowBallType.SMALL));

        // Atualizar o tabuleiro com as posições iniciais
        updateBoard();
    }

    public PositionContent getPositionContent(int row, int col) {
        if (row < 0 || row >= board.size() || col < 0 || col >= board.get(0).size()) {
            throw new IndexOutOfBoundsException("Invalid position");
        }
        return board.get(row).get(col);
    }

    public void setPositionContent(int row, int col, PositionContent content) {
        if (row < 0 || row >= board.size() || col < 0 || col >= board.get(0).size()) {
            throw new IndexOutOfBoundsException("Invalid position");
        }
        board.get(row).set(col, content);
    }

    public boolean isValidPosition(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    public Monster getPlayer() {
        return player;
    }

    public List<SnowBall> getSnowballs() {
        return new ArrayList<>(snowballs);
    }

    public List<Snowman> getSnowmen() {
        return new ArrayList<>(snowmen);
    }

    public void updateBoard() {
        // Limpar o tabuleiro
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                setPositionContent(i, j, PositionContent.SNOW);
            }
        }

        // Adicionar os elementos ao tabuleiro
        Position playerPos = player.getPosition();
        setPositionContent(playerPos.getRow(), playerPos.getCol(), PositionContent.NO_SNOW);

        for (SnowBall snowball : snowballs) {
            Position pos = snowball.getPosition();
            setPositionContent(pos.getRow(), pos.getCol(), PositionContent.BLOCK);
        }

        for (Snowman snowman : snowmen) {
            Position pos = snowman.getPosition();
            setPositionContent(pos.getRow(), pos.getCol(), PositionContent.SNOWMAN);
        }
    }

    public SnowBall getSnowballAt(Position position) {
        for (SnowBall snowball : snowballs) {
            if (snowball.getPosition().equals(position)) {
                return snowball;
            }
        }
        return null;
    }

    public boolean hasSnowballAt(Position position) {
        return getSnowballAt(position) != null;
    }

    public boolean isPositionEmpty(Position position) {
        if (!isValidPosition(position)) {
            return false;
        }

        // Verificar se há um jogador, boneco de neve ou bola de neve nesta posição
        if (player.getPosition().equals(position)) {
            return false;
        }

        for (SnowBall snowball : snowballs) {
            if (snowball.getPosition().equals(position)) {
                return false;
            }
        }

        for (Snowman snowman : snowmen) {
            if (snowman.getPosition().equals(position)) {
                return false;
            }
        }

        return true;
    }

    public void checkForSnowman() {
        // Verificar se há três bolas de neve empilhadas
        for (SnowBall base : new ArrayList<>(snowballs)) {
            if (base.getType() != SnowBallType.BIG) continue;

            Position basePos = base.getPosition();
            Position abovePos = new Position(basePos.getRow() - 1, basePos.getCol());

            SnowBall middle = getSnowballAt(abovePos);
            if (middle == null || middle.getType() != SnowBallType.AVERAGE) continue;

            Position topPos = new Position(abovePos.getRow() - 1, abovePos.getCol());
            SnowBall top = getSnowballAt(topPos);
            if (top == null || top.getType() != SnowBallType.SMALL) continue;

            // Criar um boneco de neve
            Snowman snowman = new Snowman(basePos, base, middle, top);
            snowmen.add(snowman);

            // Remover as bolas de neve
            snowballs.remove(base);
            snowballs.remove(middle);
            snowballs.remove(top);

            // Atualizar o tabuleiro
            updateBoard();
        }
    }
}

