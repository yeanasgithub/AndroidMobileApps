package a1.ybond;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    // added for MultiThreadSort
    // final means that we are not going to change once it is run
    private static final Random ran = new Random();
    private static final FoodItems[] foodItems = FoodItems.values();
    private static final Tools[] tools = Tools.values();
    private static final ToolUses[] toolUses = ToolUses.values();
    private static final CollectionCategory[] collectibleCate = CollectionCategory.values();
    private static final CollectionMaterialType[] collectibleMate = CollectionMaterialType.values();
    private static final PotionItems[] potions = PotionItems.values();
    private static final PotionColor[] potionColors = PotionColor.values();

    public static void main(String[] args) {

	// write your code here
    // make a scanner object
    Scanner scan = new Scanner(System.in);
        //to store, use List
    List<Item> items = new ArrayList<>();

    // store enum to an array
    // FoodItems[] foodItems = FoodItems.values();

    // Tools enum and ToolUses
    // Tools[] tools = Tools.values();
    // ToolUses[] toolUses = ToolUses.values();

    // CollectionCategory and CollectionMaterialType for Collectibles Class
    // CollectionCategory[] collectibleCate = CollectionCategory.values();
    // CollectionMaterialType[] collectibleMate = CollectionMaterialType.values();

    // PotionItems and PotionColor
    // PotionItems[] potions = PotionItems.values();
    // PotionColor[] potionColors = PotionColor.values();

    System.out.print("How many items do you want: ");


    // scan object to store the user input
    int itemCnt = Integer.parseInt(scan.nextLine());

        for(int i = 0; i < itemCnt; i++) {
        // bound value is not included, so 0 or 3
            int type = ran.nextInt(4);
            switch (type) {
                case 0 -> {
                    // genFood() will return and returned item will be added
                    items.add(genFood());
                }
                case 1 -> items.add(genTool());

                case 2 -> items.add(genCollectible());

                case 3 -> items.add(genPotion());

                default -> throw new IllegalStateException("Unexpected value: " + type);
            }

        }
        for(Item i : items) {
            System.out.println(i);
        }

    //Item a = new Item("Graphics Card", 6000.32, 1);
    //Consumable b = new Consumable("Apple", 0.25, 20, 1);
    //Food c = new Food("Pie", 3.75, 10, 8, 10);

    // We do not have to use toString, Java is smart enough to know this
    //System.out.println(a);
    //System.out.println(b);
    //System.out.println(c);
    }

    public static Food genFood() {

        int foodIndex = ran.nextInt(foodItems.length);
        String foodName = foodItems[foodIndex].toString();
        float foodPrice = ran.nextFloat(10);
        int foodQty = ran.nextInt(30);
        int foodUses = ran.nextInt(6);
        float healthGain = ran.nextFloat(20);
        //Food tmpFood =
        //System.out.println(tmpFood);
        // we are going to store instead of print it out
        return new Food(foodName, foodPrice, foodQty, foodUses, healthGain);
    }

    public static Tool genTool() {

        int toolIndex = ran.nextInt(tools.length);
        String toolName = tools[toolIndex].toString();
        float toolPrice = ran.nextFloat(200);
        int toolQty = ran.nextInt(15);
        String use = toolUses[toolIndex].toString();
        //Tool tmpTool =
        //System.out.println(tmp);
        return new Tool(toolName, toolPrice, toolQty, use);
    }

    public static Collectibles genCollectible() {

        int collectibleIndex = ran.nextInt(collectibleCate.length);
        String collectibleName = collectibleCate[collectibleIndex].toString();
        float collectiblePrice = ran.nextFloat(300);
        int collectibleQty = ran.nextInt(10);
        String materialType = collectibleMate[collectibleIndex].toString();
        return new Collectibles(collectibleName, collectiblePrice, collectibleQty, materialType);
    }

    public static Potion genPotion() {

        int potionIndex = ran.nextInt(potions.length);
        String potionName = potions[potionIndex].toString();
        float potionPrice = ran.nextFloat(1000);
        int potionQty = ran.nextInt(100);
        String potionColor = potionColors[potionIndex].toString();
        return new Potion(potionName, potionPrice, potionQty, potionColor);
    }
}
