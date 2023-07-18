package byog.Core;

import java.util.HashSet;
import java.util.Set;

public class Area implements Face {
    private final Set<Coordinate> points = new HashSet<>();

    public Area() {}

    public Area(Face... faces) {
        for(Face face: faces) {
            points.addAll(face.toCoordinateSet());
        }
    }

    public void add(Coordinate... coordinates) {
        for (Coordinate c: coordinates) {
            points.add(c);
        }
    }

    @Override
    public Set<Coordinate> toCoordinateSet() {
        return points;
    }

    public void merge(Face face) {
        points.addAll(face.toCoordinateSet());
    }
}
