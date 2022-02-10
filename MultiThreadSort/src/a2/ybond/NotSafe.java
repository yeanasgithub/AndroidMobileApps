package a2.ybond;

public class NotSafe extends Thread{
    public static int count;
    @Override
    public void run() {
        try {
            sleep((long) (Math.random() * 100));
            getCount();
        } catch (InterruptedException e ) {
            e.printStackTrace();
        }
    }
    public static int getCount() {
        System.out.println(count);
        return count++;
    }



}
