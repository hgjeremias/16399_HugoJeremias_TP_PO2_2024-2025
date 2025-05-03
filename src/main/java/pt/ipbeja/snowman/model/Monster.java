package pt.ipbeja.snowman.model;

public class Monster extends MobileElement{

    public Monster(Position position) {
        super(position);
    }

    @Override
    public String toString() {
        return "Monster{position=" + getPosition() + "}";
    }
}
