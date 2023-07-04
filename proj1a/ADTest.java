import org.junit.Test;
import static org.junit.Assert.*;

public class ADTest {
    @Test
    public void addTest() {
        ArrayDeque<Object> deque = new ArrayDeque<>();
        assertTrue(deque.isEmpty());
        Object[] objs = new Object[10];
        for (int i = 0; i < 10; i++) {
            objs[i] = new Object();
        }
        deque.addLast(objs[1]);
        deque.addLast(objs[2]);
        deque.addFirst(objs[3]);
        assertEquals(3, deque.size());
        deque.addFirst(objs[5]);
        deque.addLast(objs[6]);
        deque.addLast(objs[7]);
        assertEquals(6, deque.size());
        deque.addLast(objs[9]);
    }
}
