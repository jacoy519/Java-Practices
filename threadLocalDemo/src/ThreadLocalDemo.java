
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by medivh on 2017/10/24.
 */
public class ThreadLocalDemo {

    //注意这里是声明为全局变量
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
        @Override
        public Integer initialValue() {
            return 0;
        }
    };

    public static void main(String[] args) {

        int threadCount = 3;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        //开启三个线程都同时对于value操作
        for(int i=0 ; i<threadCount ; i++) {
            final int index = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i=0 ;i<10 ; i++) {
                        int currentValue = threadLocal.get();
                        currentValue++;
                        threadLocal.set(currentValue);
                    }
                    System.out.println(" the result for thread " + index + " : " +threadLocal.get());
                }
            });
        }
    }
}
