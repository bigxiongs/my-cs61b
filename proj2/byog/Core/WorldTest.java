package byog.Core;

import org.junit.Test;
import static org.junit.Assert.*;

public class WorldTest {
    @Test
    public void intersectsTest() {
        Area a1 = new Area(2, 2);
        Area a2 = new Area(2, 2, 1, 1);
        assertTrue(a1.intersects(a2));
        a1 = new Area(1, 1);
        a2 = new Area(3, 3, -1, -1);
        assertTrue(a1.intersects(a2));
    }
    @Test
    public void calcDistanceTest() {
        Area a1 = new Area(2, 2);
        Area a2 = new Area(2, 2, 1, 3);
        assertEquals(1, Connection.calcDistance(a1, a2));
    }
}
