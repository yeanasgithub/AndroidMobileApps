package a1.ybond;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
    // make a scanner object
    Scanner scan = new Scanner(System.in);
    Random ran = new Random();

    //to store, use List
    List<Item> items = new ArrayList<>();

    // store enum to an array
    FoodItems[] foodItems = FoodItems.values();

    // Tools enum and ToolUses
    Tools[] tools = Tools.values();
    ToolUses[] toolUses = ToolUses.values();
    System.out.print("How many items do you want: ");

    // scan object to store the user input
    int itemCnt = Integer.parseInt(scan.nextLine());

        for(int i = 0; i < itemCnt; i++) {
        // bound value is not included, so 0 or 1
            int type = ran.nextInt(2);
            switch (type) {
                case 0 -> {
                    int foodIndex = ran.nextInt(foodItems.length);
                    String foodName = foodItems[foodIndex].toString();
                    float foodPrice = ran.nextFloat(10);
                    int foodQty = ran.nextInt(30);
                    int foodUses = ran.nextInt(6);
                    float healthGain = ran.nextFloat(20);
                    Food tmpFood = new Food(foodName, foodPrice, foodQty, foodUses, healthGain);
                    //System.out.println(tmpFood);
                    // we are going to store instead of print it out
                    items.add(tmpFood);
                }
                case 1 -> {
                    int toolIndex = ran.nextInt(tools.length);
                    String toolName = tools[toolIndex].toString();
                    float toolPrice = ran.nextFloat(200);
                    int toolQty = ran.nextInt(15);
                    String use = toolUses[toolIndex].toString();
                    Tool tempTool = new Tool(toolName, toolPrice, toolQty, use);
                    //System.out.println(tmp);
                    items.add(tempTool);
                }
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
}
