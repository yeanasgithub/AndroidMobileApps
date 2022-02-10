package a2.ybond;

import java.util.Random;
import a1.ybond.Item;

public class ThreadSort extends Thread{

    private Item[] tItems;

    public ThreadSort(Item[] items, int lowBounds, int upperBounds){
        this.tItems = new Item[upperBounds - lowBounds];

        // Copying Item's array and storing to tItems' array
        System.arraycopy(items, lowBounds, this.tItems, 0, upperBounds - lowBounds);

    }

    @Override

    public void run() {
        System.out.println("Thread Started");
        int n = this.tItems.length;
        Item tmp;
        for(int i = 0; i < n; i++){
            for(int j = 1; j < n; j++){
                // check if it needs to be swapped
                if(this.tItems[j-1].getPrice() > this.tItems[j].getPrice()) {
                    tmp = this.tItems[j-1];
                    this.tItems[j-1] = this.tItems[j];
                    this.tItems[j] = tmp;
                }
            }

        }
        System.out.println("Thread Complete");
    }

    public Item[] gettItems() {
        return tItems;
    }
}
