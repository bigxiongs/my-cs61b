public class ArrayDeque<T> {
    private int size;
    private int capacity;
    private static final double RESIZE_FACTOR = 0.75;
    private static final int INITIAL_SIZE = 8;
    private T[] items;
    private int head;
    private int tail;

    public ArrayDeque() {
        capacity = INITIAL_SIZE;
        items = (T[]) new Object[capacity];
        head = capacity - 1;
        tail = capacity - 1;
        size = 0;
    }

    private int forward(int p) {
        p++;
        if (p == capacity) {
            p = 0;
        }
        return p;
    }

    private int backward(int p) {
        p--;
        if (p == -1) {
            p = capacity - 1;
        }
        return p;
    }

    private void resize(int cap) {
        T[] tmp = (T[]) new Object[cap];
        int i = head;
        head = cap - 1;
        tail = size - 1;
        for (int cnt = 0; cnt < size; cnt++) {
            i = forward(i);
            tmp[cnt] = items[i];
        }
        capacity = cap;
        items = tmp;
    }

    public void addFirst(T item) {
        if (size >= capacity * RESIZE_FACTOR) {
            resize(capacity * 2);
        }
        items[head] = item;
        head = backward(head);
        size++;
    }

    public void addLast(T item) {
        if (size >= capacity * RESIZE_FACTOR) {
            resize(capacity * 2);
        }
        tail = forward(tail);
        items[tail] = item;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (size != 0) System.out.print(get(0));
        for (int i = 1; i < size; i++) {
            System.out.print(" " + get(i));
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (size > INITIAL_SIZE && size <= (1 - RESIZE_FACTOR) * capacity) {
            resize(capacity / 2);
        }
        head = forward(head);
        size--;
        return items[head];
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (size > INITIAL_SIZE && size <= (1 - RESIZE_FACTOR) * capacity) {
            resize(capacity / 2);
        }
        tail = backward(tail);
        size--;
        return items[forward(tail)];
    }

    public T get(int index) {
        return items[(index + head + 1) % capacity];
    }
}
