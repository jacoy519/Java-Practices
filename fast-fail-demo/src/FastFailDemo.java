import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by medivh on 2017/10/11.
 */
public class FastFailDemo {

    private static List<Integer> list =  new ArrayList<>();

    public static void main(String[] args) {

        for(int i=0 ;i<100 ;i++) {
            list.add(i);
        }

        FutureTask test=null;

        //设置两个线程同时对list进行操作
        //线程一用迭代器访问list
        Runnable runnableOne = new Runnable() {
            @Override
            public void run() {
                for(Integer val : list) {
                    try {
                        Thread.sleep(30);
                    }catch (Exception e) {

                    }
                    System.out.println("visit:" + val);
                }
            }
        };

        //线程二随机删除一些list中的数据
        Runnable runnableTwo = new Runnable() {
            @Override
            public void run() {
                int i=0;
                while(i<100) {
                    try {
                        Thread.sleep(30);
                    }catch (Exception e) {

                    }
                    if(i%3==0) {
                        list.remove(i);
                    }
                    i++;
                }
            }
        };

        //启动线程
        new Thread(runnableOne).start();
        new Thread(runnableTwo).start();
    }
}
