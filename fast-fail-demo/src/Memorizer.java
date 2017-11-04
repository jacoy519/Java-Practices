import java.security.Key;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by medivh on 2017/10/30.
 */
public class Memorizer<A,V> {

    private ExecutorService excutor = Executors.newFixedThreadPool(10);

    private Map<A, FutureTask<V>> taskMap = new ConcurrentHashMap<>();

    public V doSomething(A key) {
        BlockingQueue<Integer> queue1= new LinkedBlockingQueue<>();
        BlockingQueue<Integer> queue2= new ArrayBlockingQueue<Integer>(1);
        FutureTask<V> f = taskMap.get(key);
        if(f == null) {
            Callable<V> eval = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    //do something need long time
                    return null;
                }
            };
            FutureTask<V> ft = new FutureTask<V>(eval);
            f = taskMap.putIfAbsent(key, ft);
            if(f == null) {
                f = ft;
                excutor.submit(ft);
            }
        }
        try {
            return f.get();
        } catch (Exception e) {
            //handle exception
            return null;
        }
    }
}
