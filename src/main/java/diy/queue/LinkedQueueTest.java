package diy.queue;

public class LinkedQueueTest {

    public static void main(String[] args) {
        Queue<String> queue = new LinkedQueue<>();

        queue.put("123");
        queue.put("234");
        queue.put("111");
        while (queue.size() > 0) {
            System.out.println(queue.take());
        }
        queue.put("123");
        queue.put("234");
        queue.put("111");
        while (queue.size() > 0) {
            System.out.println(queue.take());
        }
        queue.put("123");
        System.out.println(queue.take());
        queue.put("234");
        System.out.println(queue.take());
        queue.put("111");
        System.out.println(queue.take());
    }
}
