package byog.Core;

public enum DIRECTION {
    NORTH("N", "W"), SOUTH("S", "S"),
    WEST("W", "A"), EAST("E", "D"),
    NORTH_EAST("NE"), NORTH_WEST("NW"),
    SOUTH_EAST("SE"), SOUTH_WEST("SW");

    private final String abbr;
    private final String key;

    DIRECTION(String abbr, String key) {
        this.abbr = abbr;
        this.key = key;
    }

    DIRECTION(String abbr) {
        this.abbr  = abbr;
        this.key = "";
    }

    public String getAbbr() {
        return abbr;
    }
}
