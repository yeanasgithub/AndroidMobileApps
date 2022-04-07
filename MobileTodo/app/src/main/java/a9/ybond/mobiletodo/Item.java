package a9.ybond.mobiletodo;

public class Item {

    // 9. private final variables
    private final String description;

    public Item(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    // this is our each item
    @Override
    public String toString() {     return this.getDescription();    }


}
