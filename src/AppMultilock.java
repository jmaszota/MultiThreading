import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jacek.maszota on 17.11.2015.
 */
public class AppMultilock {

    private Random random = new Random();

    private List<Integer> list1 = new ArrayList<Integer>();
    private List<Integer> list2 = new ArrayList<Integer>();

    Object lock1 = new Object();
    Object lock2 = new Object();


    public void stageOne() {

        synchronized (lock1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list1.add(random.nextInt(100));
        }
    }


    public void stageTow() {

        synchronized (lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list2.add(random.nextInt(100));
        }
    }


    public void process() {
        for (int i = 0; i < 1000; i++) {
            stageOne();
            stageTow();
        }
    }

    public void main() {

        System.out.println("Starting...");

        long start = System.currentTimeMillis();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("Ended in: " + (end - start));
        System.out.println("L1 size:" + list1.size() + "; L2 size: " + list2.size());
    }


}
