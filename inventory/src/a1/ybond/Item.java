package a1.ybond;

public class Item {
    // member variable which is private (protected)
    // multiple access modifiers
    // package private protected in public
    // control the setting of the variable and function

    private double price;
    private int qty;
    private String name;

    // default constructor by alt + insert
    public Item() {
        this.price = 0;
        this.qty= 0;
        this.name = "Item";
    }

    public Item(String name, double price, int qty) {
        this.price = price;
        this.qty = qty;
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        // price should not be negative
        // so use will use max function
        this.price = Math.max(price, 0);
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = Math.max(qty, 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("| %-20s | $%-10.2f | %6d |", this.name, this.price, this.qty);
    }



}



