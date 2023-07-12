package byog.Core;

/**
 * Specific Area in the world denoted by down-left corner(inclusive) and
 * up-right corner(exclusive)
 */
public class Area {
    private final Position downLeft;
    private final Position upRight;

    public Area(Position downLeft, Position upRight) {
        this.downLeft = downLeft;
        this.upRight = upRight;
    }

    public Area(int width, int height, Position p) {
        this.downLeft = p;
        this.upRight = new Position(p.getX() + width - 1, p.getY() + height - 1);
    }

    public Area(int width, int height) {
        this(width, height, new Position(0, 0));
    }

    public Area(int width, int height, int x, int y) {
        this(width, height, new Position(x, y));
    }

    public int width() {
        return upRight.getX() - downLeft.getX() + 1;
    }

    public int height() {
        return upRight.getY() - downLeft.getY() + 1;
    }

    public int size() {
        return width() * height();
    }

    public Position downLeft() {
        return downLeft;
    }

    public Position upRight() {
        return upRight;
    }

    public boolean equals(Area a) {
        if (this == a) {
            return true;
        }
        if (a == null) {
            return false;
        }
        if (getClass() != a.getClass()) {
            return false;
        }
        return downLeft.equals(a.downLeft) && upRight.equals(a.upRight);
    }

    public boolean intersects(Area a) {
        if (downLeft.inside(a) || upRight.inside(a)) {
            return true;
        }
        return !a.downLeft.inside(this);
    }

    public Position[] toArray() {
        Position[] arr = new Position[width() * height()];
        int cnt = 0;
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < width(); j++) {
                arr[cnt++] = new Position(downLeft.getX() + i, downLeft.getY() + j);
            }
        }
        return arr;
    }
}

