import org.junit.Test;
import static org.junit.Assert.*;

public class LLDTest {
    @Test
    public void testAddFirst() {
        LinkedListDeque<Object> deque = new LinkedListDeque<>();
        assertTrue(deque.isEmpty());
        Object obj = new Object();
        deque.addFirst(obj);
        assertEquals(1, deque.size());
        assertEquals(obj, deque.get(0));
        assertEquals(obj, deque.removeLast());
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testAddLast() {
        LinkedListDeque<Object> deque = new LinkedListDeque<>();
        assertTrue(deque.isEmpty());
        Object obj = new Object();
        deque.addLast(obj);
        assertEquals(1, deque.size());
        assertEquals(obj, deque.get(0));
        assertEquals(obj, deque.removeLast());
        assertTrue(deque.isEmpty());
    }
}
