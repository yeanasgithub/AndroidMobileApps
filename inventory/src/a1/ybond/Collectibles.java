package a1.ybond;

public class Collectibles extends Item {
    private String materialType;

    public Collectibles() {
        super();
        this.materialType = "";
    }

    public Collectibles(String name, double price, int qty, String materialType) {
        super(name, price, qty);
        this.materialType = materialType;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    @Override
    public String toString() {
        return String.format("%s %7s %-20s |", super.toString(), "|", this.materialType);

    }
}

