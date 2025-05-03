package pt.ipbeja.snowman.model;

public enum SnowBallType {
    BIG(6),
    AVERAGE(5),
    SMALL(4),
    BIG_AVERAGE(3),
    BIG_SMAL(2),
    AVERAGE_SMALL(1);

    private final int size;

    SnowBallType(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public boolean isSmallerThan(SnowBallType other) {
        return this.size < other.size;
    }

}
