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

        System.out.print("Do you want a [S]ingle sort or a [D]udal sort? ");
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
        }



    }

    // To avoid try-catch exception block, we will use throws
    // This is good when your function deals with only one type of exception
    private static void DualSort(Item[] items) throws InterruptedException{
        // Dual sort divides the thread into two parts and merge later
        int midPoint = Math.round(items.length / 2f);
        ThreadSort t1 = new ThreadSort(items, 0, midPoint);
        ThreadSort t2 = new ThreadSort(items, midPoint, items.length);
        long startTime = System.nanoTime();
        t1.start();
        t2.start();

        // We have to wait until t1 thread finishes before t2 gets started
        t1.join();
        t2.join();

        MergeSort m1 = new MergeSort(t1.gettItems(), t2.gettItems());
        m1.start();
        m1.join();
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
