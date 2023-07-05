public class LinkedListDeque<T> {
    private class ListNode<T> {
        private T data;
        private ListNode<T> next;
        private ListNode<T> prev;

        public ListNode(T d, ListNode<T> n, ListNode p) {
            data = d;
            next = n;
            prev = p;
        }

        public ListNode(T d) {
            data = d;
        }

        public ListNode() {
        }

        public T getData() {
            return data;
        }

        public ListNode<T> getNext() {
            return next;
        }

        public ListNode<T> getPrev() {
            return prev;
        }

        public void setData(T newData) {
            data = newData;
        }

        public void setNext(ListNode<T> newNext) {
            next = newNext;
        }

        public void setPrev(ListNode<T> newPrev) {
            prev = newPrev;
        }

        public void insertNext(ListNode<T> node) {
            node.next = next;
            node.prev = this;
            next.prev = node;
            next = node;
        }

        public T removeNext() {
            ListNode<T> p = next;
            next = p.next;
            p.next.prev = this;
            return p.data;
        }
    }

    private ListNode<T> head;
    private ListNode<T> tail;
    private int size;

    public LinkedListDeque() {
        head = new ListNode<>();
        tail = new ListNode<>();
        head.next = tail;
        tail.prev = head;
    }

    public void addFirst(T item) {
        head.insertNext(new ListNode<T>(item));
        size++;
    }

    public void addLast(T item) {
        tail.prev.insertNext(new ListNode<T>(item));
        size++;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (size() == 0) {
            System.out.println();
        } else {
            ListNode<T> p = head.getNext();
            System.out.print(p.getData());
            while (p.getNext() != tail) {
                p = p.getNext();
                System.out.print(" " + p.getData());
            }
            System.out.println();
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size--;
        return head.removeNext();
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size--;
        return tail.prev.prev.removeNext();
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        ListNode<T> p = head;
        for (int i = 0; i < index; i++) {
            p = p.getNext();
        }
        return p.getNext().getData();
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }
        return getRecursive(head.getNext(), index);
    }

    private T getRecursive(ListNode<T> root, int index) {
        if (index == 0) {
            return root.getData();
        }
        return getRecursive(root.getNext(), index - 1);
    }
}
