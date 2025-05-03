package pt.ipbeja.snowman.model;

public class Snowman {
    private final Position position;
    private final SnowBall base;
    private final SnowBall middle;
    private final SnowBall top;

    public Snowman(Position position, SnowBall base, SnowBall middle, SnowBall top) {
        this.position = position;
        this.base = base;
        this.middle = middle;
        this.top = top;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isValid() {
        return base.getType() == SnowBallType.BIG &&
               middle.getType() == SnowBallType.AVERAGE &&
               top.getType() == SnowBallType.SMALL;
    }
}