import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void arrayDequeTestRemove() {
        StringBuilder callStack = new StringBuilder();
        var sad = new StudentArrayDeque<Integer>();
        var ad = new ArrayDequeSolution<Integer>();
        for (int i = 0; i < 10; i++) {
            double randomRatio = StdRandom.uniform();
            if (randomRatio < 0.5) {
                sad.addFirst(i);
                ad.addFirst(i);
                callStack.append("addFirst(" + i + ")\n");
            } else {
                sad.addLast(i);
                ad.addLast(i);
                callStack.append("addLast(" + i + ")\n");
            }
        }
        for (int i = 0; i < 10; i++) {
            double randomRatio = StdRandom.uniform();
            if (randomRatio < 0.5) {
                callStack.append("removeFirst()\n");
                assertEquals(callStack.toString(), sad.removeFirst(), ad.removeFirst());
            } else {
                callStack.append("removeLast()\n");
                assertEquals(callStack.toString(), sad.removeLast(), ad.removeLast());
            }
        }
    }
}
