package a1.ybond;

public class Tool extends Item{
    // how we use them is unique
    private String use;

    public Tool() {
        super();
        this.use = "";
    }

    public Tool(String name, double price, int qty, String use) {
        super(name, price, qty);
        this.use = use;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    @Override
    public String toString() {
        return String.format("%s %7s %-20s |", super.toString(), "|", this.use);
    }
}
