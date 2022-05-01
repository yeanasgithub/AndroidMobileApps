package a10.ybond.mindmaster;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Peg {

    // 42. @42:14 Peg requires position info
    private Point pos = new Point();
    int radius;
    List<Paint> paints = new ArrayList<>();
    int selectedPaint = 0;

    // constructor of this class
    public Peg(int selectedPaint, int radius, Point pos)
    {
        this.selectedPaint = selectedPaint;
        //creating paints using for loop
        for(int i = 0; i < 6; i++)
        {
            paints.add(new Paint());

        }

        // setting paint colors
        paints.get(0).setARGB(255,50,50,50); // Dark Grey -> 0
        paints.get(1).setARGB(255,255,0,0);  // Red -> 1
        paints.get(2).setARGB(255,0,0,255);  // Blue -> 2
        paints.get(3).setARGB(255,0,255,0);  // Green -> 3
        paints.get(4).setARGB(255,255,255,0); // Yellow -> 4
        paints.get(5).setARGB(255,255,165,0); // Orange -> 5

        this.radius = radius;
        this.pos = pos;

    }

    // 43. set color function
    public void setColor(int color)
    {
        selectedPaint = color;
    }

    public void draw(@NonNull Canvas canvas)
    {
        canvas.drawCircle(pos.x, pos.y, radius, paints.get(selectedPaint));
    }

    // 44. Next, our Board.java will generate all the pegs -> go to Board.java

    // 53. we need to know what area is clicked @: 1:04:03
    // and isPegClicked() is called inside Board.java
    public void isPegClicked(Point mouseInPeg)
    {
        double distance = Math.sqrt(Math.pow(pos.x - mouseInPeg.x, 2) + Math.pow(pos.y - mouseInPeg.y, 2));
        if(distance <= radius) {    pegClicked();   }
    }

    private void pegClicked()
    {
        // to ensure that the index does not go beyond the number of the colors
        // Mod is used
        selectedPaint = (selectedPaint + 1) % paints.size();
    }

    // 67. @37:43 in part 2
    // inside of Peg class, we need a constructor to get our position
    public Point getPos() {    return pos;    }





}
