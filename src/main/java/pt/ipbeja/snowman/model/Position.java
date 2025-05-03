package pt.ipbeja.snowman.model;

public class Position {
    private int col;
    private int row; //nullable

    public Position(int row, int col) {
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }
    public int getRow() {
        return row;
    }

    public Position getPositionAt(Direction direction) {
        return new Position(
                this.row + direction.getRowDelta(),
                this.col + direction.getColDelta()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }


}
