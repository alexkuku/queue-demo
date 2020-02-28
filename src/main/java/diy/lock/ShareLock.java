package diy.lock;


import java.io.Serializable;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class ShareLock implements Serializable {
    private final Sync sync;
    private final int maxCount;

    public ShareLock(int maxCount) {
        sync = new Sync(maxCount);
        this.maxCount = maxCount;
    }

    public boolean lock() {
        return sync.acquiredByShared(1);
    }

    public boolean unLock() {
        return sync.tryReleaseShared(1);
    }

class Sync extends AbstractQueuedSynchronizer {


    public Sync(int count) {
        setState(count);
    }

    public boolean acquiredByShared(int i) {
        for (;;) {
            if (i <= 0) {
                return false;
            }
            int state = getState();
            if (state <= 0) {
                System.out.println("剩下的锁不够");
                return false;
            }
            int expectState = state - i;
            if (expectState < 0) {
                System.out.println("剩下的锁不够");
                return false;
            }
            if (compareAndSetState(state, expectState)) {
                return true;
            }
        }
    }


    @Override
    protected boolean tryReleaseShared(int arg) {
        for (;;) {
            if (arg <= 0) {
                return false;
            }
            int state = getState();
            int expectState = state + arg;
            if (expectState < 0 || expectState > maxCount) {
                System.out.println("锁已释放完");
                return false;
            }
            if (compareAndSetState(state, expectState)) {
                return true;
            }
        }
    }
}
}
