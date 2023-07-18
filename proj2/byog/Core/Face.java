package byog.Core;

import java.util.Set;

public interface Face {
    Set<Coordinate> toCoordinateSet();

    default boolean intersects(Face face) {
        Set<Coordinate> set1 = toCoordinateSet();
        Set<Coordinate> set2 = face.toCoordinateSet();
        for (Coordinate c: set2) {
            if (set1.contains(c)) {
                return true;
            }
        }
        return false;
    }

    default int size() {
        return toCoordinateSet().size();
    }

    default Face merge(Face... faces) {
        Set<Coordinate> set = toCoordinateSet();
        for (Face face: faces) {
            set.addAll(face.toCoordinateSet());
        }
        return new Line(set.toArray(new Coordinate[0]));
    }

    default Coordinate get(int i) {
        Set<Coordinate> set = toCoordinateSet();
        if (i > set.size()) {
            throw new RuntimeException();
        }
        return set.toArray(new Coordinate[0])[i];
    }
}
