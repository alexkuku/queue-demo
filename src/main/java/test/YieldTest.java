package test;

public class YieldTest {

    public static void main(String[] args) {
        int i = 0;
        Long begin = System.currentTimeMillis();
//        System.out.println(begin);
        while (true) {
            Thread.yield();
            System.out.println(i++);
            if (System.currentTimeMillis() > (begin + 1000)) {
                break;
            }
        }
    }
}
