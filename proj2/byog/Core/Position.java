package byog.Core;

/**
 * Specific position in the world
 */
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * move current positon dist towards DIR.
     * @return position after movement
     */
    public Position move(DIRECTION DIR, int dist) {
        Position pos;
        switch (DIR) {
            case NORTH: {
                pos = new Position(x, y + dist);
                break;
            }
            case SOUTH: {
                pos = new Position(x, y - dist);
                break;
            }
            case EAST: {
                pos = new Position(x - dist, y);
                break;
            }
            case WEST: {
                pos = new Position(x + dist, y);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + DIR);
        }
        return pos;
    }

    public Position move(DIRECTION DIR) {
        return move(DIR, 1);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Position p) {
        if (this == p) {
            return true;
        }
        if (p == null) {
            return false;
        }
        if (getClass() != p.getClass()) {
            return false;
        }
        return x == p.getX() && y == p.getY();
    }

    public boolean inside(Area a) {
        return x >= a.downLeft().x && x <= a.downLeft().x + a.width() &&
                y >= a.downLeft().y && y <= a.downLeft().y + a.height();
    }
}

