import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by medivh on 2017/10/18.
 */
public class DemoThread extends Thread {
    @Override
    public synchronized void run() {

        try {
            ReentrantReadWriteLock test = new ReentrantReadWriteLock();
            ReentrantLock lock = new ReentrantLock();
            while (!Thread.currentThread().isInterrupted()) {
                //doSomething
            }
        } catch (Exception e) {

        }
    }
}
