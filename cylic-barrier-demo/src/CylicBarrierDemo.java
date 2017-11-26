import java.util.concurrent.CyclicBarrier;

/**
 * Created by medivh on 2017/11/6.
 */
public class CylicBarrierDemo {

    public static void main(String[] args) {

        int threadNunber = 5;

        //定义cyclicBarrier，阻塞数量为5
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadNunber);
        for(int i=0 ; i<threadNunber ; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("thread wait");

                        //线程被阻塞，直到有5个线程到达这里
                        cyclicBarrier.await();
                        System.out.println("thread run");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            new Thread(runnable).start();
        }
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
