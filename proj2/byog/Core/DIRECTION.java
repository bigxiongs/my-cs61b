package byog.Core;

public enum DIRECTION {
    NORTH("N", "W"), SOUTH("S", "S"),
    WEST("W", "A"), EAST("E", "D"),
    NORTH_EAST("NE"), NORTH_WEST("NW"),
    SOUTH_EAST("SE"), SOUTH_WEST("SW");

    public final String abbr;
    public final String key;

    DIRECTION(String abbr, String key) {
        this.abbr = abbr;
        this.key = key;
    }

    DIRECTION(String abbr) {
        this.abbr  = abbr;
        this.key = "";
    }
}