/**
 * Created by jacek.maszota on 16.11.2015.
 */
public class AppSynchronized {

    private int count = 0;

    public synchronized  void increment(){
        count++; //count = count + 1;
    }

    public static void main(String[] args) {
        AppSynchronized app = new AppSynchronized();
        app.doWork();
    }

    public void doWork(){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i < 10000; i++){
                    increment();
                }
            }
        });


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i < 10000; i++){
                    increment();
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join(); //returns when t1 finishes. To wait for thread to be finished
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Count is: " + count); //20000 expected
    }
}
