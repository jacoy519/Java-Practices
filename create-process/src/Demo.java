import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by medivh on 2017/6/29.
 */
public class Demo {

    public static void main(String[] args) {

        try {

            //创建一个执行脚本的进程
            Process process = Runtime.getRuntime().exec("run some command");

            //用两个独立线程读取进程的两个输入流数据，防止进程阻塞
            Thread outputStreamThread = new Thread(new StreamHandler(process.getInputStream(), "output"));
            Thread errorStreamThread = new Thread(new StreamHandler(process.getErrorStream(), "error"));
            outputStreamThread.start();
            errorStreamThread.start();

            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //用于处理输入流的线程
    static class StreamHandler implements Runnable {

        private InputStream in = null;

        private String type = null;

        public StreamHandler(InputStream in, String type) {
            this.in = in;
            this.type = type;
        }
        @Override
        public void run() {
            try {
                //从输入流中读取数据
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line = null;
                while((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
