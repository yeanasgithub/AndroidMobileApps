package a2.ybond;

import a1.ybond.*;

import java.util.Random;
import java.util.Scanner;

public class Main {
    // refactoring to make our code cleaner
    // or Project Structure better

    public static void main(String[] args) throws InterruptedException {
	    Scanner scan = new Scanner(System.in);
        Random ran = new Random();

        System.out.print("Do you want a [S]ingle sort or a [D]ual sort or a [Q]uad sort? ");
        char selection = scan.next().charAt(0);

        System.out.print("How many items do you want to sort?");
        int count = scan.nextInt();

        Item[] items = new Item[count];
        for(int i = 0; i < count; i++) {
            int t = ran.nextInt(4);
            switch (t) {
                case 0 -> items[i] = a1.ybond.Main.genFood();
                case 1 -> items[i] = a1.ybond.Main.genTool();
                case 2 -> items[i] = a1.ybond.Main.genCollectible();
                case 3 -> items[i] = a1.ybond.Main.genPotion();
            }
        }

        switch (selection) {
            case 's', 'S' -> SingleSort(items);
            case 'd', 'D' ->
                    //System.out.println("Not yet");
                    DualSort(items);
            case 'q', 'Q' -> QuadSort(items);
        }



    }

    // To avoid try-catch exception block, we will use throws
    // This is good when your function deals with only one type of exception
    private static void QuadSort(Item[] items) throws InterruptedException {

        //private First_half[]
        // 3 mid points
        int first_midPoint = Math.round(items.length / 2f);
        int second_midPoint = Math.round(first_midPoint / 2f);
        int third_midPoint = Math.round((items.length + first_midPoint) / 2f);
        // 4 thread sorts t1 - t4
        ThreadSort t1 = new ThreadSort(items, 0, second_midPoint);
        ThreadSort t2 = new ThreadSort(items, second_midPoint, first_midPoint);
        ThreadSort t3 = new ThreadSort(items, first_midPoint, third_midPoint);
        ThreadSort t4 = new ThreadSort(items, third_midPoint, items.length);
        // three merge sorts

        long startTime = System.nanoTime();
        t1.start();
        t2.start();

        // We have to wait until t1 thread finishes before t2 gets started
        t1.join();
        t2.join();

        MergeSort m1 = new MergeSort(t1.gettItems(), t2.gettItems());
        m1.start();
        m1.join();


        t3.start();
        t4.start();

        // Again, We have to wait until t3 thread finishes before t4 gets started
        t3.join();
        t4.join();

        MergeSort m2 = new MergeSort(t3.gettItems(), t4.gettItems());
        m2.start();
        m2.join();

        // Final Merge sorting for Quadsort
        MergeSort m3 = new MergeSort(m1.getSortedItems(), m2.getSortedItems());
        m3.start();
        m3.join();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;

        for(Item i : m3.getSortedItems()){
            System.out.println(i);
        }

        System.out.print("Quad sort took: " + duration);

    }

    private static void DualSort(Item[] items) throws InterruptedException{
        // Dual sort divides the thread into two parts and merge later
        int midPoint = Math.round(items.length / 2f);
        // 2 more midpoints to make quadSort
        ThreadSort t1 = new ThreadSort(items, 0, midPoint);
        ThreadSort t2 = new ThreadSort(items, midPoint, items.length);
        // 2 more Threadsort t3 and t4 accordingly

        long startTime = System.nanoTime();
        t1.start();
        t2.start();

        // We have to wait until t1 thread finishes before t2 gets started
        t1.join();
        t2.join();

        MergeSort m1 = new MergeSort(t1.gettItems(), t2.gettItems());
        m1.start();
        m1.join();

        // 2 more mergesort m2 and m3
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;

        for(Item i : m1.getSortedItems()){
            System.out.println(i);
        }
        System.out.print("Dual sort took: " + duration);

        // for(Item i : t1.gettItems()){
        //     System.out.println(i);
        // }
        // System.out.println("-----------------------------------------------------------");
        // for(Item i : t2.gettItems()){
        //     System.out.println(i);
        // }
    }
    private static void SingleSort(Item[] items){
        // Making a ThreadSort object in this function
        // Here we get the duration of sorting
        ThreadSort single = new ThreadSort(items, 0, items.length);
        long startTime = System.nanoTime();
        single.start();
        try {
            single.join();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;
            Item[] sortedItems = single.gettItems();
            for(Item i : sortedItems){
                System.out.println(i);
            }
            System.out.println("Was sorted in: " + duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
