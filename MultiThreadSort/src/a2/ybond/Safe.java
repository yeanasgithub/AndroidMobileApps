package a2.ybond;

public class Safe extends Thread{
    private static int count;

    @Override
    public void run() {
        try {
        sleep((long) (Math.random() * 100));
        getCount();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // sychronized means that one thread comes in
    // and lock the door so another tread could not come inside
    public static synchronized int getCount() {
        System.out.println(count);
        return count++;
    }
}
