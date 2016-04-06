import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class TestAtomic {

    private static AtomicLong aLong = new AtomicLong(); // 原子量，每个线程都可以自由操作

    private static class MyRunnable implements Runnable {
        private String name;

        MyRunnable(String name) {
            this.name = name;
        }

        public void run() {
            System.out.println(name + ":" + aLong.incrementAndGet());
        }
    }

    public static void main(String args[]) throws IOException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        List<MyRunnable> runnableList = new ArrayList<>();
        int i = 0;
        while (i < 100) {
            runnableList.add(new MyRunnable("t" + i));
            i++;
        }
        while (i-- > 0)
            pool.execute(runnableList.get(i));
        pool.shutdown();
    }

}
