package a1.ybond;

public class Food extends Consumable{
    float healthGain;

    public Food() {
        super();
        this.healthGain = 0;
    }

    public Food(String name, double price, int qty, int usesLeft, float healthGain) {
        super(name, price, qty, usesLeft);
        this.healthGain = healthGain;
    }

    public float getHealthGain() {
        return healthGain;
    }

    public void setHealthGain(float healthGain) {
        this.healthGain = healthGain;
    }

    @Override
    public String toString() {
        // super.toString() always to the parent class
        return String.format("%s %20.2f |", super.toString(), this.healthGain);
    }
}
