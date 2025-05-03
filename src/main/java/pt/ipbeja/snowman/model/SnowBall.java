package pt.ipbeja.snowman.model;

public class SnowBall extends MobileElement{
    private SnowBallType type;

    public SnowBall(Position position, SnowBallType type) {
        super(position);
        this.type = type;
    }

    public SnowBallType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "SnowBall{" +
                "position=" + getPosition() +
                ", type=" + type +
                '}';
    }

}
