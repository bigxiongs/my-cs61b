package byog.Core;

public enum DIRECTION {
    NORTH, SOUTH, WEST, EAST, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST;

    public static DIRECTION parseDirection(char c) {
        switch (c) {
            case 'a': return WEST;
            case 's': return SOUTH;
            case 'w': return NORTH;
            case 'd': return EAST;
            default: throw new IllegalArgumentException("false direction");
        }
    }
}

