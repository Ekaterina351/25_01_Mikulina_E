package footprints;

public enum Direction {
    NORTH,
    NORTHWEST,
    SOUTHWEST,
    SOUTH,
    NORTHEAST,
    SOUTHEAST;

    public Direction nextDirectionClockwise() {
        return switch (this) {
            case NORTH -> NORTHEAST;
            case NORTHEAST -> SOUTHEAST;
            case SOUTHEAST -> SOUTH;
            case SOUTH -> SOUTHWEST;
            case SOUTHWEST -> NORTHWEST;
            case NORTHWEST -> NORTH;
        };
    }

    public Direction nextDirectionAnticlockwise() {
        return switch (this) {
            case NORTH -> NORTHWEST;
            case NORTHWEST -> SOUTHWEST;
            case SOUTHWEST -> SOUTH;
            case SOUTH -> SOUTHEAST;
            case SOUTHEAST -> NORTHEAST;
            case NORTHEAST -> NORTH;
        };
    }
    
    public Direction getOppositeDirection() {
        return switch(this) {
            case NORTH -> SOUTH;
            case NORTHWEST -> SOUTHEAST;
            case SOUTHWEST -> NORTHEAST;
            case SOUTH -> NORTH;
            case NORTHEAST -> SOUTHWEST;
            case SOUTHEAST -> NORTHWEST;
        };
    }
}
