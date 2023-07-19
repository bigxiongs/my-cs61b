package byog.Core;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Collections;

public class Line implements Face {
    private final List<Coordinate> points = new LinkedList<>();

    public Line(Coordinate... coordinates) {
        Collections.addAll(points, coordinates);
    }

    public void add(Coordinate... coordinates) {
        Collections.addAll(points, coordinates);
    }

    public static Line of(int... nums) {
        Line line = new Line();
        for (int i = 0; i < nums.length / 2; i++) {
            line.add(new Coordinate(nums[i * 2], nums[i * 2 + 1]));
        }
        return line;
    }

    @Override
    public Set<Coordinate> toCoordinateSet() {
        Set<Coordinate> set = new HashSet<>(points.size());
        Iterator<Coordinate> iterator = points.iterator();
        Coordinate start;
        if (iterator.hasNext()) {
            start = iterator.next();
            set.add(start);
        } else {
            return set;
        }
        while (iterator.hasNext()) {
            Coordinate end = iterator.next();
            while (!start.equals(end)) {
                set.add(start);
                start = start.towards(end);
            }
            set.add(start);
        }
        return set;
    }

    public int length() {
        return size();
    }

    public Area outline() {
        Area outline = new Area();
        Set<Coordinate> set = toCoordinateSet();
        for (Coordinate c: set) {
            Coordinate l = c.towards(DIRECTION.WEST);
            Coordinate r = c.towards(DIRECTION.EAST);
            Coordinate d = c.towards(DIRECTION.SOUTH);
            Coordinate u = c.towards(DIRECTION.NORTH);
            if (!set.contains(l)) {
                outline.add(l);
            }
            if (!set.contains(r)) {
                outline.add(r);
            }
            if (!set.contains(d)) {
                outline.add(d);
            }
            if (!set.contains(u)) {
                outline.add(u);
            }
        }
        return outline;
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
            return points.equals(((Line) obj).points);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(points.toArray());
    }
}
