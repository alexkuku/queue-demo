package diy.lock;

public class ShareLockTest {

    public static void main(String[] args) {
        ShareLock shareLock = new ShareLock(5);
        for (int i=0; i <= 6; i++) {
            if (shareLock.lock()) {
                System.out.println("获取链接：" + i);
            }

        }
        for (int i=0; i <= 6; i++) {

            if (shareLock.unLock()) {
                System.out.println("释放链接：" + i);
            }
        }

    }
}
