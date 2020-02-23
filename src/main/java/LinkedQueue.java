import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedQueue<T> implements Queue<T> {

    private final Integer PUT_LOCK_TIMEOUT = 300;

    private final Integer TAKE_LOCK_TIMEOUT = 200;

    private volatile Node<T> head;

    private volatile Node<T> tail;

    private AtomicInteger size = new AtomicInteger();

    private final Integer capacity;

    private ReentrantLock putLock = new ReentrantLock();

    private ReentrantLock takeLock = new ReentrantLock();

    class LinkedNode<T> extends Node<T> {
        public LinkedNode(T item) {
            super(item);
        }
    }

    public LinkedQueue() {
        this.capacity = Integer.MAX_VALUE;
        head = tail = new LinkedNode<>(null);
    }

    public LinkedQueue(Integer capacity) {
        if (capacity == null || capacity < 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        head = tail = new LinkedNode<>(null);
    }

    @Override
    public boolean put(T t) {
        if (t == null) {
            return false;
        }
        try {
            boolean lockSuccess = putLock.tryLock(PUT_LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
            if (!lockSuccess) {
                return false;
            }
            if (size.get() >= capacity) {
                throw new IndexOutOfBoundsException();
            }
            tail = tail.next = new LinkedNode<>(t);
            if (size.incrementAndGet() == 1) {
                head = tail;
            }
            return true;
        } catch (InterruptedException e) {
            return false;
        } finally {
            putLock.unlock();
        }
    }

    @Override
    public T take() {
        if(size.get() == 0) {
            return null;
        }
        try {
            boolean lockSuccess = takeLock.tryLock(TAKE_LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
            if (!lockSuccess) {
                return null;
            }
            Node expectHead = head.next;
            T result = head.item;
            head.item = null;
            head = (LinkedNode) expectHead;
            if (size.decrementAndGet() == 0) {
                tail = head = new LinkedNode<>(null);
            }
            return result;
        } catch (InterruptedException e) {
            return null;
        } finally {
            takeLock.unlock();
        }
    }

    @Override
    public Integer size() {
        return size.get();
    }
}
