package byog.Core;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WorldTest {
    @Test
    public void containsTest() {
        Set<Coordinate> set = new HashSet<>();
        Coordinate point = new Coordinate(0, 0);
        assertFalse(set.contains(point));
        set.add(point);
        assertTrue(set.contains(point));
        Coordinate copyOfPoint = new Coordinate(0, 0);
        assertTrue(set.contains(copyOfPoint));
    }

    @Test
    public void westOfTest() {
        Rectangle rec1 = new Rectangle(74, 2, 76, 9);
        Rectangle rec2 = new Rectangle(62, 4, 63, 6);
        Connection[] connections = rec1.connect(rec2);
        assertEquals(3, connections.length);
    }

    @Test
    public void rectangleGetTest() {
        Rectangle rec = new Rectangle(3, 3);
        Coordinate third = new Coordinate(3, 0);
        assertEquals(third, rec.get(3));
        Coordinate ninth = new Coordinate(1, 2);
        assertEquals(ninth, rec.get(9));
    }

    @Test
    public void connectTest() {
        Rectangle from = new Rectangle(59, 13, 62, 17);
        Rectangle to = new Rectangle(58, 19, 62, 25);
        Connection[] candidates = from.connect(to);
        Connection expect = new Connection(from, to, Line.of(59, 17, 59, 19));
        assertEquals(expect, candidates[0]);
    }
}
