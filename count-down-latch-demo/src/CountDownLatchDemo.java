import java.util.concurrent.CountDownLatch;

/**
 * Created by medivh on 2017/10/26.
 */
public class CountDownLatchDemo {

    public static void main(String[] args){
        final CountDownLatch startGate=new CountDownLatch(1);
        int threadSize=5;
        final CountDownLatch endGate=new CountDownLatch(threadSize);
        for(int i=0;i<threadSize;i++){
            Thread t=new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        //等待主线程启动
                        startGate.await();
                        System.out.println("sub thread run");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally {
                        endGate.countDown();
                    }
                }

            });
            t.start();
        }
        //启动所有子线程
        startGate.countDown();
        try {
            //等待所有子线程完成
            endGate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main thread run");

    }
}
