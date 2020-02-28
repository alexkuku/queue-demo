package diy.queue;

public interface Queue<T> {

    boolean put(T t);

    T take();

    Integer size();

    class Node<T> {
        T item;

        Node<T> next;

        public Node(T item) {
            this.item = item;
        }
    }
}
