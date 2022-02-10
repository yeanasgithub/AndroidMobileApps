package a2.ybond;

import a1.ybond.Item;

public class MergeSort extends Thread{
    private Item[] item_first_half;
    private Item[] item_second_half;
    private Item[] sortedItems;

    public MergeSort(Item[] i1, Item[] i2) {
        this.item_first_half = i1;
        this.item_second_half = i2;
        this.sortedItems = new Item[i1.length + i2.length];
    }


    @Override
    public void run() {
        System.out.println("Merge started");
        int i = 0; // Current index of item_first_half
        int j = 0; // Current index of item_second_half
        int k = 0; // Current index of sortedItems

        while(i < item_first_half.length && j < item_second_half.length){
            if(this.item_first_half[i].getPrice() < this.item_second_half[j].getPrice()){
                this.sortedItems[k++] = this.item_first_half[i++];

            } else{
                this.sortedItems[k++] = this.item_second_half[j++];
            }
        }

        // if item_first_half is longer than item_second_half
        // dump the rest of the item to the end of sortedItems
        while(i < this.item_first_half.length) {
            this.sortedItems[k++] = this.item_first_half[i++];
        }

        // if item_second_half is longer than item_first_half
        // dump the rest of the item to the end of sortedItems
        while(j < this.item_second_half.length){
            this.sortedItems[k++] = this.item_second_half[j++];
        }

        System.out.println("Merge Complete");

    }

    // We only need the end result, sortedItems
    public Item[] getSortedItems() {
        return sortedItems;
    }
}
