/**
 * Created by medivh on 2017/10/18.
 */
public class DemoThread extends Thread {
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                //doSomething
            }
        } catch (Exception e) {

        }
    }
}
