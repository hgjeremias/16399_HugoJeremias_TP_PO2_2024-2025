package pt.ipbeja.snowman.model;

public class MobileElement {
    private Position position;

    public MobileElement(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void move(Direction direction) {
        this.position = this.position.getPositionAt(direction);
    }

}
