package byog.Core;

import java.util.HashSet;
import java.util.Set;

public class Rectangle implements Face {
    private final int left;
    private final int right;
    private final int down;
    private final int up;

    public Rectangle(int x1, int y1, int x2, int y2) {
        left = x1;
        right = x2;
        down = y1;
        up = y2;
    }

    public Rectangle(int x, int y) {
        this(0, 0, x, y);
    }

    public static Rectangle[] of(int... xAndY) {
        Rectangle[] arr = new Rectangle[xAndY.length / 2];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Rectangle(xAndY[i * 2], xAndY[i * 2 + 1]);
        }
        return arr;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getDown() {
        return down;
    }

    public int getUp() {
        return up;
    }

    @Override
    public Set<Coordinate> toCoordinateSet() {
        Set<Coordinate> set = new HashSet<>();
        for (int x = left; x <= right; x++) {
            for (int y = down; y <= up; y++) {
                set.add(new Coordinate(x, y));
            }
        }
        return set;
    }

    public int width() {
        return right - left + 1;
    }

    public int height() {
        return up - down + 1;
    }

    public Rectangle moveTo(Coordinate c) {
        int dx = c.getX() - left;
        int dy = c.getY() - down;
        return new Rectangle(c.getX(), c.getY(), right + dx, up + dy);
    }

    @Override
    public int size() {
        return width() * height();
    }

    public Line outline() {
        Line outline = new Line();
        outline.add(new Coordinate(left - 1, down - 1));
        outline.add(new Coordinate(right + 1, down - 1));
        outline.add(new Coordinate(right + 1, up + 1));
        outline.add(new Coordinate(left - 1, up + 1));
        outline.add(new Coordinate(left - 1, down - 1));
        return outline;
    }

    public boolean near(Rectangle rec) {
        if (intersects(rec)) {
            return false;
        }
        Line outline = outline();
        return outline.intersects(rec);
    }

    public Connection[] connect(Rectangle rec) {
        Line[] lines;
        if (intersects(rec)) {
            lines = new Line[0];
        } else if (northOf(rec)) {
           int l = Math.max(left, rec.left);
           int r = Math.min(right, rec.right);
           lines = new Line[r - l + 1];
           for (int i = l; i <= r; i++) {
               lines[i - l] = new Line(new Coordinate(i, rec.up),
                       new Coordinate(i, down));
           }
        } else if (southOf(rec)) {
            int l = Math.max(left, rec.left);
            int r = Math.min(right, rec.right);
            lines = new Line[r - l + 1];
            for (int i = l; i <= r; i++) {
                lines[i - l] = new Line(new Coordinate(i, up),
                        new Coordinate(i, rec.down));
            }
        } else if (westOf(rec)) {
            int u = Math.min(up, rec.up);
            int d = Math.max(down, rec.down);
            lines = new Line[u - d + 1];
            for (int i = d; i <= u; i++) {
                lines[i - d] = new Line(new Coordinate(right, i),
                        new Coordinate(rec.left, i));
            }
        } else if (eastOf(rec)) {
            int u = Math.min(up, rec.up);
            int d = Math.max(down, rec.down);
            lines = new Line[u - d + 1];
            for (int i = d; i <= u; i++) {
                lines[i - d] = new Line(new Coordinate(rec.right, i),
                        new Coordinate(left, i));
            }
        } else if (northEastOf(rec)) {
            lines = new Line[]{ Line.of(left, down, left, rec.up, rec.right, rec.up),
                Line.of(left, down, rec.right, down, rec.right, rec.up)};
        } else if (northWestOf(rec)) {
            lines = new Line[]{ Line.of(right, down, right, rec.up, rec.left, rec.up),
                Line.of(right, down, rec.left, down, rec.left, rec.up)};
        } else if (southWestOf(rec)) {
            lines = new Line[]{ Line.of(right, up, rec.left, up, rec.left, rec.down),
                Line.of(right, up, right, rec.down, rec.left, rec.down)};
        } else if (southEastOf(rec)) {
            lines = new Line[]{ Line.of(left, up, rec.right, up, rec.right, rec.down),
                Line.of(left, up, left, rec.down, rec.right, rec.down)};
        } else {
            throw new RuntimeException();
        }
        Connection[] connections = new Connection[lines.length];
        for (int i = 0; i < lines.length; i++) {
            connections[i] = new Connection(this, rec, lines[i]);
        }
        return connections;
    }

    public boolean northOf(Rectangle rec) {
        return (!(right < rec.left || left > rec.right))
                && down > rec.up;
    }

    public boolean southOf(Rectangle rec) {
        return (!(right < rec.left || left > rec.right))
                && up < rec.down;
    }

    public boolean westOf(Rectangle rec) {
        return (!(up < rec.down || down > rec.up))
                && right < rec.left;
    }

    public boolean eastOf(Rectangle rec) {
        return (!(up < rec.down || down > rec.up))
                && left > rec.right;
    }

    public boolean northEastOf(Rectangle rec) {
        return left > rec.right && down > rec.up;
    }

    public boolean northWestOf(Rectangle rec) {
        return right < rec.left && down > rec.up;
    }

    public boolean southEastOf(Rectangle rec) {
        return left > rec.right && up < rec.down;
    }

    public boolean southWestOf(Rectangle rec) {
        return right < rec.left && up < rec.down;
    }

    public boolean intersects(Rectangle rectangle) {
        if (equals(rectangle)) {
            return true;
        }
        return intersects((Face) rectangle);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        } else {
            Rectangle rectangle = (Rectangle) obj;
            return left == rectangle.left && right == rectangle.right &&
                    down == rectangle.down && up == rectangle.up;
        }
    }

    @Override
    public Coordinate get(int i) {
        return new Coordinate(left + i % width(), down + i / width());
    }
}
