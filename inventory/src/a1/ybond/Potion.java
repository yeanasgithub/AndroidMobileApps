package a1.ybond;

public class Potion extends Item{
    private String potionColor;

    public Potion() {
        super();
        this.potionColor = "";
    }

    public Potion(String name, double price, int qty, String potionColor) {
        super(name, price, qty);
        this.potionColor = potionColor;
    }

    public String getPotionColor() {
       return potionColor;
    }

    public void setPotionColor(String potionColor) {
        this.potionColor = potionColor;
    }

    @Override
    public String toString() {
        return String.format("%s %7s %-20s |", super.toString(), "|", this.potionColor);
    }
}