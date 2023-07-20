package byog.Core;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Coordinate implements Face {
    private final int x;
    private final int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            Coordinate co = (Coordinate) o;
            return x == co.x && y == co.y;
        }
    }

    public Coordinate towards(Coordinate c) {
        if (northOf(c)) {
            return towards(DIRECTION.SOUTH);
        } else if (southOf(c)) {
            return towards(DIRECTION.NORTH);
        } else if (westOf(c)) {
            return towards(DIRECTION.EAST);
        } else if (eastOf(c)) {
            return towards(DIRECTION.WEST);
        } else {
            return this;
        }
    }

    public Coordinate towards(DIRECTION d) {
        Coordinate c;
        switch (d) {
            case NORTH: {
                c = new Coordinate(x, y + 1);
                break;
            }
            case SOUTH: {
                c = new Coordinate(x, y - 1);
                break;
            }
            case WEST: {
                c = new Coordinate(x - 1, y);
                break;
            }
            case EAST: {
                c = new Coordinate(x + 1, y);
                break;
            }
            default: {
                throw new RuntimeException();
            }
        }
        return c;
    }

    public boolean northOf(Coordinate c) {
        return x == c.x && y > c.y;
    }

    public boolean southOf(Coordinate c) {
        return x == c.x && y < c.y;
    }

    public boolean westOf(Coordinate c) {
        return x < c.x && y == c.y;
    }

    public boolean eastOf(Coordinate c) {
        return x > c.x && y == c.y;
    }

    @Override
    public boolean intersects(Face face) {
        return face.toCoordinateSet().contains(this);
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public Set<Coordinate> toCoordinateSet() {
        Set<Coordinate> set = new HashSet<>();
        set.add(this);
        return set;
    }
}

