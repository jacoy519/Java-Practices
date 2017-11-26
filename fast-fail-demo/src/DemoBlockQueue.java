import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by medivh on 2017/11/21.
 */
public class DemoBlockQueue<T> {

    private List<T> queue;

    private int maxSize;

    private Lock lock = new ReentrantLock();

    private Condition notEmpty = lock.newCondition();

    private Condition notFull = lock.newCondition();

    public DemoBlockQueue(int size) {
        queue = new ArrayList<>(size);
        maxSize = size;
    }

    public void put(T e) {
        lock.lock();
        try {
            while(queue.size() == maxSize) {
                notFull.await();
            }
            queue.add(e);
            notEmpty.signal();
        }catch (Exception ex) {

        } finally {
            lock.unlock();
        }
    }

    public T take() {
        lock.lock();
        try {
            while (queue.size() == 0) {
                notEmpty.await();
            }
            T val = queue.remove(0);
            notFull.signal();
            return val;
        }catch (Exception e) {
            return null;
        }finally {
            lock.unlock();
        }
    }
}
