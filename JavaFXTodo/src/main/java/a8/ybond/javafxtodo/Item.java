package a8.ybond.javafxtodo;

public class Item {
    private final String description;



    public Item(String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {  return this.getDescription();   }
}
